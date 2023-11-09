package com.vijay.travel.service.impl;

import com.vijay.travel.exception.UserAbsentException;
import com.vijay.travel.repository.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Supplier;

abstract class AbstractUserDependentEntityServiceImpl<E, ID> {

    protected final UserRepository userRepository;

    public AbstractUserDependentEntityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    protected Optional<E> getEntityById(ID id, Optional<String> matchUsername, JpaRepository<E, ID> entityRepository) {

        Optional<E> optionalEntity = entityRepository.findById(id);

        if (matchUsername.isPresent() && optionalEntity.isPresent()) {

            String provided = matchUsername.get();
            String actual = extractUsernameFromEntity(optionalEntity.get());

            if (provided.equals(actual)) {
                return optionalEntity;
            } else {
                return Optional.empty();
            }
        }

        return optionalEntity;
    }

    protected E deleteEntity(ID id, Optional<String> matchUsername, JpaRepository<E, ID> entityRepository, Supplier<? extends RuntimeException> exceptionSupplier) throws UserAbsentException {

        E fetchedEntity = entityRepository.findById(id)
                .orElseThrow(exceptionSupplier);

        if (matchUsername.isPresent()) {

            String provided = matchUsername.get();
            String actual = extractUsernameFromEntity(fetchedEntity);

            if (!userRepository.existsById(provided)) {
                throw new UserAbsentException("User with username: " + provided + " does not exist");
            }
            if (!provided.equals(actual)) {
                throw exceptionSupplier.get();
            }
        }

        entityRepository.deleteById(id);
        return fetchedEntity;
    }

    protected abstract String extractUsernameFromEntity(E entity);
}
