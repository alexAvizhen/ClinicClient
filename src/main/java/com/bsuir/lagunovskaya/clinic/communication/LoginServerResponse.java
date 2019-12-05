package com.bsuir.lagunovskaya.clinic.communication;

import com.bsuir.lagunovskaya.clinic.communication.entity.User;

public class LoginServerResponse extends ServerResponse {

    private static final long serialVersionUID = -3010446995174504379L;

    private String loginStatus;

    private User user;

    public LoginServerResponse(String loginStatus, User user) {
        this.loginStatus = loginStatus;
        this.user = user;
    }

    public LoginServerResponse(String loginStatus) {
        this.loginStatus = loginStatus;
        this.user = null;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public User getUser() {
        return user;
    }
}
