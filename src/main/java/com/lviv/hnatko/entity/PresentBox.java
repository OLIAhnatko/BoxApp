package com.lviv.hnatko.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
public class PresentBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull
    @Size(min = 2, max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;


    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @NotNull
    @Column(name = "price_in_uah", precision = 21, scale = 2, nullable = false)
    private BigDecimal priceInUah;

}
