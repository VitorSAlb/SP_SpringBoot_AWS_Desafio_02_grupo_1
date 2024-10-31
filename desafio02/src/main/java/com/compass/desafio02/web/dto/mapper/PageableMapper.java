package com.compass.desafio02.web.dto.mapper;

import com.compass.desafio02.web.dto.PageableDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PageableMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <E, D> PageableDto toDto(Page<E> page, Class<D> dtoClass) {
        PageableDto dto = new PageableDto();
        dto.setContent(page.getContent().stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList()));
        dto.setNumber(page.getNumber());
        dto.setSize(page.getSize());
        dto.setNumberOfElements(page.getNumberOfElements());
        dto.setTotalPages(page.getTotalPages());
        dto.setTotalElements((int) page.getTotalElements());
        dto.setFirst(page.isFirst());
        dto.setLast(page.isLast());
        return dto;
    }
}
