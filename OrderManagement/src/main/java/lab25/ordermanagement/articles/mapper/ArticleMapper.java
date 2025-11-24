package lab25.ordermanagement.articles.mapper;

import lab25.ordermanagement.articles.dto.ArticleDTO;
import lab25.ordermanagement.articles.dto.ArticleUpdateDTO;
import lab25.ordermanagement.articles.models.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring" ,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL
)
public interface ArticleMapper {

    ArticleDTO toDTO(ArticleEntity entity);

    ArticleEntity updateFromDTO(ArticleUpdateDTO dto, @MappingTarget ArticleEntity entity);
}
