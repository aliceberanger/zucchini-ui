package io.zucchiniui.backend.auth.rest;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Priority(Priorities.AUTHENTICATION)
public final class JWTAuthFilter<P extends Principal> extends AuthFilter<Map<String, Object>, P> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthFilter.class);

    private static final Splitter AUTH_HEADER_SPLITTER = Splitter.on(' ').limit(2).trimResults();

    private final JWTVerifier jwtVerifier;

    private JWTAuthFilter(final JWTVerifier jwtVerifier) {
        this.jwtVerifier = Objects.requireNonNull(jwtVerifier, "jwtVerifier must be supplied");
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        final Optional<P> principal = getTokenFromRequest(requestContext).map(this::parseToken).flatMap(this::authenticate);

        if (principal.isPresent()) {

            requestContext.setSecurityContext(new SecurityContext() {

                @Override
                public Principal getUserPrincipal() {
                    return principal.get();
                }

                @Override
                public boolean isUserInRole(final String role) {
                    return authorizer.authorize(principal.get(), role);
                }

                @Override
                public boolean isSecure() {
                    return requestContext.getSecurityContext().isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return SecurityContext.BASIC_AUTH;
                }

            });

        } else {
            throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
        }
    }

    private Optional<String> getTokenFromRequest(final ContainerRequestContext requestContext) {
        final String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (Strings.isNullOrEmpty(authHeader)) {
            return Optional.empty();
        }

        final List<String> parts = AUTH_HEADER_SPLITTER.splitToList(authHeader);
        if (parts.size() != 2) {
            LOGGER.warn("Invalid auth header received. Parts are: {}", parts);
            return Optional.empty();
        }

        final String authMethod = parts.get(0);
        if (!prefix.equalsIgnoreCase(authMethod)) {
            return Optional.empty();
        }

        return Optional.of(parts.get(1));
    }

    private Map<String, Object> parseToken(final String token) {
        try {
            return jwtVerifier.verify(token);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | JWTVerifyException | IOException e) {
            LOGGER.warn("Received an invalid JWT token", e);
            throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
        }
    }

    private Optional<P> authenticate(final Map<String, Object> tokenContent) {
        try {
            return authenticator.authenticate(tokenContent);
        } catch (final AuthenticationException e) {
            LOGGER.warn("Error authenticating current credentials", e);
        }
        return Optional.empty();
    }

    public static final class Builder<P extends Principal> extends AuthFilterBuilder<Map<String, Object>, P, JWTAuthFilter<P>> {

        private JWTVerifier jwtVerifier;

        public Builder() {
            setPrefix("Bearer");
        }

        public Builder<P> setJWTVerifier(final JWTVerifier jwtVerifier) {
            this.jwtVerifier = jwtVerifier;
            return this;
        }

        @Override
        protected JWTAuthFilter<P> newInstance() {
            return new JWTAuthFilter<>(jwtVerifier);
        }

    }

}
