package io.zucchiniui.backend.auth.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.dropwizard.jackson.Discoverable;
import io.zucchiniui.backend.auth.domain.UserRepository;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface UserRepositoryFactory extends Discoverable {

    UserRepository create();

}
