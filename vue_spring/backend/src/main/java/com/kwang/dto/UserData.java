package com.kwang.dto;

import java.time.LocalDateTime;

public class UserData {
    private String email;
    private String name;
    private String password;
    private int remaintime;
    private LocalDateTime createDate;
    private String token;

    public String getEmail() {
        return this.email;
    }

    public int getRemaintime() {
        return remaintime;
    }

    public void setRemaintime(int remaintime) {
        this.remaintime = remaintime;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}