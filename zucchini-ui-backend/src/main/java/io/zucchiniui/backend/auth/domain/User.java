package io.zucchiniui.backend.auth.domain;

import com.google.common.base.MoreObjects;
import io.zucchiniui.backend.support.ddd.BaseEntity;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User extends BaseEntity<String> implements Principal {

    private final String name;

    private final String displayName;

    public User(final String name, final String displayName) {
        this.name = Objects.requireNonNull(name);
        this.displayName = Objects.requireNonNull(displayName);
    }

    public static User fromJWTClaims(final Map<String, Object> claims) {
        return new User((String) claims.get("sub"), (String) claims.get("displayName"));
    }

    public boolean hasRole(final String role) {
        // FIXME Use real roles
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Map<String, Object> toJWTClaims() {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("sub", name);
        claims.put("displayName", displayName);
        return claims;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("name", name)
            .add("displayName", displayName)
            .toString();
    }

    @Override
    protected String getEntityId() {
        return name;
    }

}
