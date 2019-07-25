package com.t_systems.webstore.model.enums;

public enum Category {
    BURGER("Burgers"),
    DRINK("Drinks"),
    HOT("Hot"),
    PIZZA("Pizza"),
    SET("Combos"),
    SUSHI("Sushi"),
    SWEET("Desserts"),
    WOK("Woks");

    private final String text;

    Category(String str) {
        this.text = str;
    }

    @Override
    public String toString() {
        return text;
    }
}
