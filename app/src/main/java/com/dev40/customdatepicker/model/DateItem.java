package com.dev40.customdatepicker.model;

public class DateItem {
    public String title;
    public boolean isSelected;
    public DATE_ITEM_TYPE type;

    public enum DATE_ITEM_TYPE {
        MONTH,
        DAY,
        YEAR
    }
}
