package com.bsuir.lagunovskaya.clinic.communication;

import com.bsuir.lagunovskaya.clinic.communication.entity.User;

public class UserServerResponse extends ServerResponse {

    private static final long serialVersionUID = 498950774815643206L;

    private User user;

    public UserServerResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
