package com.mrdotxin.aiturtlesoup.enums;

public enum RoomStatusEnum {

    WAITING("等待中", "waiting"),

    STARTED("已开始", "started"),

    ENDED("已结束", "ended");

    private final String text;
    private final String value;

    RoomStatusEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
