package io.zucchiniui.backend.auth.rest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class AuthRequest {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String password;

    @JsonCreator
    public AuthRequest(@JsonProperty("name") final String name, @JsonProperty("password") final String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
