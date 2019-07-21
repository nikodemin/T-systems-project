package com.t_systems.webstore.model;

public enum Category
{
    BURGER("Бургеры"),
    DRINK("Напитки"),
    HOT("Горячее"),
    PIZZA("Пицца"),
    SET("Наборы"),
    SUSHI("Суши"),
    SWEET("Десерты"),
    WOK("Воки");

    private final String text;

    Category(String str)
    {
        this.text=str;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
