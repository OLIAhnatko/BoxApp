package com.lviv.hnatko.mapper;

import com.lviv.hnatko.dto.UserResponse;
import com.lviv.hnatko.entity.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserResponseMapper extends  AbstractMapper<AppUser, UserResponse>  {

    private final ModelMapper mapper;

    UserResponseMapper(ModelMapper mapper) {
        super(AppUser.class, UserResponse.class);
        this.mapper = mapper;
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(AppUser.class, UserResponse.class)
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(UserResponse.class, AppUser.class)
                .setPostConverter(toEntityConverter());
    }

}
