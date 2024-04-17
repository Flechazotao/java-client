package com.teach.javafx.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Token {
    private String token;
    private UserInfo userInfo;

    public Token(String token, String uid) {
        this.token = token;
        this.userInfo = new UserInfo(uid);
    }
}
