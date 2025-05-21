package com.radovan.play.converter;

import com.radovan.play.dto.BookGenreDto;
import com.radovan.play.entity.BookGenreEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class BookGenreConverter {

    @Inject
    private ModelMapper mapper;

    public BookGenreDto entityToDto(BookGenreEntity genreEntity){
        return mapper.map(genreEntity, BookGenreDto.class);
    }

    public BookGenreEntity dtoToEntity(BookGenreDto genreDto){
        return mapper.map(genreDto, BookGenreEntity.class);
    }
}
