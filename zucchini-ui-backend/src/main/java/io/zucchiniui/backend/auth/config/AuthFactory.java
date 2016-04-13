package io.zucchiniui.backend.auth.config;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import io.dropwizard.util.Duration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AuthFactory {

    @NotEmpty
    private String secret;

    @NotNull
    private Algorithm algorithm = Algorithm.HS512;

    @NotNull
    private Duration expiry;

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public void setAlgorithm(final Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setExpiry(final Duration expiry) {
        this.expiry = expiry;
    }

    public JWTSigner createJWTSigner() {
        return new JWTSigner(secret);
    }

    public JWTSigner.Options createJWTSignerOptions() {
        return new JWTSigner.Options()
            .setIssuedAt(true)
            .setExpirySeconds((int) expiry.toSeconds())
            .setAlgorithm(algorithm);
    }

    public JWTVerifier createJWTVerifier() {
        return new JWTVerifier(secret);
    }

}
