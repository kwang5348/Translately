package com.kwang.dto;

import java.time.LocalDateTime;

public class UserData {
    private int userid;
    private String email;
    private String name;
    private String password;
    private int remaintime;
    private LocalDateTime createdate;
    private String token;



    public String toString() {
        return "email : " + email + "\nname : " + name + "\npassword : " + password + "\nremaintime : " + remaintime
                + "\ncreatedate : " + createdate + "\ntoken : " + token;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return this.email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRemaintime() {
        return remaintime;
    }

    public void setRemaintime(int remaintime) {
        this.remaintime = remaintime;
    }

    public LocalDateTime getCreatedate() {
        return createdate;
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