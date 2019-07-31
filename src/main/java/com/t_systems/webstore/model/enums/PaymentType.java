package com.t_systems.webstore.model.enums;

public enum PaymentType {
    CASH("Cash"),
    CARD("Card");

    private String text;

    PaymentType(String str){
        text = str;
    }

    @Override
    public String toString() {
        return text;
    }
}
