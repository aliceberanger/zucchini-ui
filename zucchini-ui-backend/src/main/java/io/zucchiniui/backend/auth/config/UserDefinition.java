package io.zucchiniui.backend.auth.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public final class UserDefinition {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String password;

    @NotEmpty
    private final String displayName;

    public UserDefinition(
        @JsonProperty("name") final String name,
        @JsonProperty("password") final String password,
        @JsonProperty("displayName") final String displayName
    ) {
        this.name = name;
        this.password = password;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

}
