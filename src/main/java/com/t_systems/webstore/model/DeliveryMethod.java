package com.t_systems.webstore.model;

public enum DeliveryMethod
{
    COURIER("Курьер"),
    PICKUP("Самовывоз");

    private String text;

    DeliveryMethod(String str)
    {
        text = str;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
