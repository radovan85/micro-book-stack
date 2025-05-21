package com.radovan.play.converter;

import com.radovan.play.dto.BookDto;
import com.radovan.play.entity.BookEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

import java.text.DecimalFormat;

@Singleton
public class BookConverter {

    private final DecimalFormat decfor = new DecimalFormat("0.00");

    @Inject
    private ModelMapper mapper;

    public BookDto entityToDto(BookEntity bookEntity){
        BookDto returnValue = mapper.map(bookEntity, BookDto.class);
        returnValue.setPrice(Double.valueOf(decfor.format(returnValue.getPrice())));
        return returnValue;
    }

    public BookEntity dtoToEntity(BookDto bookDto){
        BookEntity returnValue = mapper.map(bookDto,BookEntity.class);
        returnValue.setPrice(Double.valueOf(decfor.format(returnValue.getPrice())));
        return returnValue;
    }
}
