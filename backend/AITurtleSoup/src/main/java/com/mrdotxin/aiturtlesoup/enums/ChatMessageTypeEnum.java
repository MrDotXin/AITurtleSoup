package com.mrdotxin.aiturtlesoup.enums;

public enum ChatMessageTypeEnum {

    TEXT("文本", "text");

    private final String text;
    private final String value;

    ChatMessageTypeEnum(String text, String value) {
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
