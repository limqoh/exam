package com.react.exam.vo;

public class DbSelectVo {
    private String key;
    private String data;

    public DbSelectVo() {

    }

    public DbSelectVo(String key, String data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public String getData() {
        return data;
    }

}
