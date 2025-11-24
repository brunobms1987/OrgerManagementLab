package lab25.ordermanagement.common.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class BaseSeeder implements Seeder {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final JdbcTemplate jdbcTemplate;

    protected BaseSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected boolean dataExists(String tableName) {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName, Long.class);
        return count != null && count > 0;
    }

    protected Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }

    protected Timestamp futureTimestamp(int days) {
        return Timestamp.from(Instant.now().plus(days, ChronoUnit.DAYS));
    }

    @Override
    public void seed() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            if (shouldSkipSeeding()) {
                return;
            }
            executeSeed();
            logger.info("{} seeder completed successfully", getName());
        } catch (Exception e) {
            logger.error("Error in {} seeder: {}", getName(), e.getMessage(), e);
            throw e; // Just rethrow the original exception
        } finally {
            stopWatch.stop();
            logger.debug("{} seeder took {} ms", getName(), stopWatch.getTotalTimeMillis());
        }
    }

    protected boolean shouldSkipSeeding() {
        return false;
    }

    protected abstract void executeSeed();
}
