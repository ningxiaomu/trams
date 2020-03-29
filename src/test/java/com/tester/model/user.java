package com.tester.model;

import java.io.Serializable;


public class user implements Serializable{

    private Integer id;
    private String username;
    //private Date birthday;
    private String password;
    private  String params;

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    private String ContentType;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", ContentType='" + ContentType + '\'' +
                ", params='" + params + '\'' +
                ", expected='" + expected + '\'' +
                ", method='" + method + '\'' +
                '}';
    }

    private String expected;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String method;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public Date getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Date birthday) {
//        this.birthday = birthday;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getParams(){
        return params;
    }
    public void setParams(String params){
        this.params=params;
    }

}
