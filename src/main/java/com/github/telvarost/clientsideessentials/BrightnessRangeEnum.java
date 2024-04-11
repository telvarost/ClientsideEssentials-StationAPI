package com.github.telvarost.clientsideessentials;

public enum BrightnessRangeEnum {
    SMALL("Small"),
    LARGE("Large"),
    EXTRA_LARGE("Extra Large");

    final String stringValue;

    BrightnessRangeEnum() {
        this.stringValue = "Small";
    }

    BrightnessRangeEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}