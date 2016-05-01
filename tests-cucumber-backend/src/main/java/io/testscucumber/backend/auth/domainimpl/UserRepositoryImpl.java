package io.testscucumber.backend.auth.domainimpl;

import io.testscucumber.backend.auth.domain.User;
import io.testscucumber.backend.auth.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    // FIXME Check username and password against a real repository

    @Override
    public Optional<User> findByNameAndPassword(final String name, final String password) {
        return Optional.of(new User(name, name));
    }

    @Override
    public User getById(final String id) {
        return new User(id, id);
    }

    @Override
    public void save(final User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(final User entity) {
        throw new UnsupportedOperationException();
    }

}
