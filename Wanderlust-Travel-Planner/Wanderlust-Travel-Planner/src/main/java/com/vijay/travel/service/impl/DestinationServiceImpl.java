package com.vijay.travel.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.vijay.travel.model.Destination;
import com.vijay.travel.repository.DestinationRepository;
import com.vijay.travel.repository.UserRepository;
import com.vijay.travel.service.DestinationService;

@Service
public class DestinationServiceImpl extends AbstractUserDependentEntityServiceImpl<Destination, UUID> implements DestinationService {

    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository, UserRepository userRepository) {

        super(userRepository);
        this.destinationRepository = destinationRepository;
    }

    @Override
    protected String extractUsernameFromEntity(Destination destination) {

        return destination.getUser().getUsername();
    }

    @Override
    public Optional<Destination> getDestinationById(UUID uuid, Optional<String> matchUsername) {

        Assert.notNull(uuid, "UUID cannot be null");
        Assert.notNull(matchUsername, "Optional username cannot be null");

        return super.getEntityById(uuid, matchUsername, destinationRepository);
    }



    @Override
    public Destination updateDestination(UUID uuid, Destination destination, Optional<String> matchUsername) {

        throw new RuntimeException("Method not implemented");
    }

	@Override
	public Destination deleteDestination(UUID uuid, Optional<String> matchUsername) {
		return null;
	}

}
