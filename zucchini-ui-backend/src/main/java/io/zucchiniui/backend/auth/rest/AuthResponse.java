package io.zucchiniui.backend.auth.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

    private final String token;

    @JsonCreator
    public AuthResponse(@JsonProperty("token") final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
