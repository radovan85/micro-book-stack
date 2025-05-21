package com.radovan.play.converter;

import com.radovan.play.dto.BookImageDto;
import com.radovan.play.entity.BookImageEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class ImageConverter {

    @Inject
    private ModelMapper mapper;

    public BookImageDto entityToDto(BookImageEntity imageEntity) {
        return mapper.map(imageEntity, BookImageDto.class);
    }

    public BookImageEntity dtoToEntity(BookImageDto imageDto) {
        return mapper.map(imageDto, BookImageEntity.class);
    }

}
