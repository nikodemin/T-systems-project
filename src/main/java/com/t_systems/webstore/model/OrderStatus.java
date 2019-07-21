package com.t_systems.webstore.model;

public enum OrderStatus
{
    UNPAID("Не оплачен"),
    PAID("Оплачен"),
    DELIVERED("Доставлен");
    private String text;

    OrderStatus(String str)
    {
        this.text=str;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
