package com.iovereye.oauth2.common.message.types;

public enum ResponseType {

    CODE("code"),
    TOKEN("token");

    private String code;

    ResponseType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
