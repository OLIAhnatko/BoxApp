package com.lviv.hnatko.entity.enumeration;

import lombok.Getter;

public enum OrderStatus {

    NOT_CREATED("You are in progress of creating an order."),
    CREATED("Your order is created! Please wait for call to approve it."),
    APPROVED("Your order is approved!"),
    DECLINED("Your order is declined, we are sorry!"),
    SENT("Your order is sent, wait for the goods to arrive."),
    DELIVERED("Your order is delivered."),
    EXPIRED("We were unable to contact you, the order automatically expired"),
    CANCELED("Your order is canceled.");

    @Getter
    private final String details;

    OrderStatus(String details) {
        this.details = details;
    }
}
