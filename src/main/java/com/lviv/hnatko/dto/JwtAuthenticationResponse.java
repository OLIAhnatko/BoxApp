package com.lviv.hnatko.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private int user_id;

    public JwtAuthenticationResponse(String accessToken, int id) {
        this.accessToken = accessToken;
        this.user_id = id;
    }
}
