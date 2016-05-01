package io.zucchiniui.backend.auth.config;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.collect.ImmutableList;
import io.zucchiniui.backend.auth.domain.UserRepository;
import io.zucchiniui.backend.auth.domainimpl.StaticUserRepositoryImpl;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonTypeName("static")
public class StaticUserRepositoryFactory implements UserRepositoryFactory {

    @NotNull
    @Valid
    private ImmutableList<UserDefinition> users = ImmutableList.of();

    public void setUsers(final ImmutableList<UserDefinition> users) {
        this.users = users;
    }

    public ImmutableList<UserDefinition> getUsers() {
        return users;
    }

    @Override
    public UserRepository create() {
        return new StaticUserRepositoryImpl(users);
    }

}
