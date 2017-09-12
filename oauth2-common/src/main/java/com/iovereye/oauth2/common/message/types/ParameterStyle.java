package com.iovereye.oauth2.common.message.types;

public enum ParameterStyle {
    BODY("body"),
    QUERY("query"),
    HEADER("header");

    private String parameterStyle;

    ParameterStyle(String parameterStyle) {
        this.parameterStyle = parameterStyle;
    }

    @Override
    public String toString() {
        return parameterStyle;
    }
}
