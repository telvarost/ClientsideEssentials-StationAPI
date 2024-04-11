package com.github.telvarost.clientsideessentials;

public enum GammaRangeEnum {
    SMALL("1.6F - 2.8F"),
    MEDIUM("1.0F - 3.4F"),
    LARGE("0.0F - 5.0F");

    final String stringValue;

    GammaRangeEnum() {
        this.stringValue = "1.0F - 3.4F";
    }

    GammaRangeEnum(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}