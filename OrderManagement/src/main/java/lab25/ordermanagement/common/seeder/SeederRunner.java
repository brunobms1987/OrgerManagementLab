package lab25.ordermanagement.common.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Comparator;
import java.util.List;

@Component
@Order(1)
public class SeederRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(SeederRunner.class);
    private final List<Seeder> seeders;

    public SeederRunner(List<Seeder> seeders) {
        this.seeders = seeders;
    }

    @Override
    public void run(String... args) {
        if (shouldSkipSeeding(args)) {
            logger.info("Database seeding skipped");
            return;
        }

        if (seeders.isEmpty()) {
            logger.warn("No seeders found to execute");
            return;
        }

        StopWatch totalStopWatch = new StopWatch();
        totalStopWatch.start();

        logger.info("Starting database seeding with {} seeders...", seeders.size());

        seeders.stream()
                .sorted(Comparator.comparingInt(Seeder::getOrder))
                .forEach(this::executeSeeder);

        totalStopWatch.stop();
        logger.info("Database seeding completed in {} ms!", totalStopWatch.getTotalTimeMillis());
    }

    private void executeSeeder(Seeder seeder) {
        logger.info("Running {} seeder (order: {})...", seeder.getName(), seeder.getOrder());
        seeder.seed(); // Let exceptions propagate naturally
    }

    private boolean shouldSkipSeeding(String... args) {
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--skip-seeding") ||
                    arg.equalsIgnoreCase("-Dskip-seeding=true")) {
                return true;
            }
        }
        return false;
    }
}
