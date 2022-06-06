package com.lviv.hnatko.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PresentBoxDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String photoUrl;
    private String name;
    private String description;
    private Boolean isAvailable;
    private BigDecimal priceInUah;

}
