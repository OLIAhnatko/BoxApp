package com.lviv.hnatko.mapper;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public abstract class AbstractMapper<ENTITY, DTO> {

    @Autowired
    ModelMapper modelMapper;

    private final Class<ENTITY> entityClass;
    private final Class<DTO> dtoClass;

    AbstractMapper(Class<ENTITY> entityClass, Class<DTO> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    public DTO toDto(ENTITY entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    public ENTITY toEntity(DTO dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, entityClass);
    }

    public DTO toPreviewDto(ENTITY entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    final Converter<ENTITY, DTO> toDtoConverter() {
        return mappingContext -> {
            ENTITY source = mappingContext.getSource();
            DTO destination = mappingContext.getDestination();
            mapSpecificDtoFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    final Converter<DTO, ENTITY> toEntityConverter() {
        return mappingContext -> {
            DTO source = mappingContext.getSource();
            ENTITY destination = mappingContext.getDestination();
            mapSpecificEntityFields(source, destination);
            return mappingContext.getDestination();
        };
    }

    void mapSpecificDtoFields(ENTITY source, DTO destination) {
    }

    void mapSpecificEntityFields(DTO source, ENTITY destination) {
    }

}
