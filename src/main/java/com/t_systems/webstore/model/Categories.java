package com.t_systems.webstore.model;

public enum Categories
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

    Categories(String str)
    {
        this.text=str;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
