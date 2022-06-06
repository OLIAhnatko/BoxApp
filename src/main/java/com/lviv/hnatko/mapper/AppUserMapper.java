package com.lviv.hnatko.mapper;

import com.lviv.hnatko.dto.AppUserDto;
import com.lviv.hnatko.entity.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AppUserMapper extends AbstractMapper<AppUser, AppUserDto> {

    private final ModelMapper mapper;
    private final PresentOrderMapper presentOrderMapper;

    AppUserMapper(ModelMapper mapper, PresentOrderMapper presentOrderMapper) {
        super(AppUser.class, AppUserDto.class);
        this.mapper = mapper;
        this.presentOrderMapper = presentOrderMapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(AppUser.class, AppUserDto.class)
                .addMappings(m -> {
                    m.skip(AppUserDto::setOrders);
                })
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(AppUserDto.class, AppUser.class)
                .setPostConverter(toEntityConverter());
    }
    @Override
    void mapSpecificDtoFields(AppUser source, AppUserDto destination) {
        boolean nullAsDefault = Objects.isNull(source);
        destination.setOrders(nullAsDefault ? null : new HashSet<>(source.getOrders()
                .stream().map(presentOrderMapper::toDto).collect(Collectors.toSet())));

    }
}
