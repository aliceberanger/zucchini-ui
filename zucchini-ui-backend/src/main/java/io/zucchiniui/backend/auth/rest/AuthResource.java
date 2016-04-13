package io.zucchiniui.backend.auth.rest;

import com.auth0.jwt.JWTSigner;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource<P extends Principal> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthResource.class);

    private final JWTSigner jwtSigner;

    private final JWTSigner.Options signingOptions;

    private final Authenticator<AuthRequest, P> authenticator;

    private final Function<P, Map<String, Object>> principalConverter;

    public AuthResource(
        final JWTSigner jwtSigner,
        final JWTSigner.Options signingOptions,
        final Authenticator<AuthRequest, P> authenticator,
        final Function<P, Map<String, Object>> principalConverter
    ) {
        this.jwtSigner = jwtSigner;
        this.signingOptions = signingOptions;
        this.authenticator = authenticator;
        this.principalConverter = principalConverter;
    }

    @POST
    @Path("authenticate")
    public AuthResponse authenticate(@Valid @NotNull final AuthRequest authRequest) {
        final Optional<P> principal;
        try {
            principal = authenticator.authenticate(authRequest);
        } catch (final AuthenticationException e) {
            LOGGER.warn("Error authenticating credentials", e);
            throw new InternalServerErrorException();
        }

        if (!principal.isPresent()) {
            throw new BadRequestException("Invalid credentials");
        }

        final String token = jwtSigner.sign(principalConverter.apply(principal.get()), signingOptions);
        return new AuthResponse(token);
    }

}
