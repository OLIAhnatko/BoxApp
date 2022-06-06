package com.lviv.hnatko.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lviv.hnatko.entity.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PresentOrder {

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

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NOT_CREATED;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser buyer;


    public PresentOrder (String name, String photoUrl, String description, Boolean isAvailable,BigDecimal priceInUah, AppUser buyer) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.description = description;
        this.isAvailable = isAvailable;
        this.priceInUah = priceInUah;
        this.buyer = buyer;
    }
}
