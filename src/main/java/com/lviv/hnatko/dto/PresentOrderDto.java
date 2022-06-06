package com.lviv.hnatko.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import com.lviv.hnatko.entity.enumeration.OrderStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class PresentOrderDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String name;

    private String photoUrl;

    private String description;

    private Boolean isAvailable;

    private BigDecimal priceInUah;

    private OrderStatus status;

    private UserResponse buyer;
}
