package com.vijay.travel.service.impl;

import com.vijay.travel.exception.DestinationAbsentException;
import com.vijay.travel.exception.ItineraryAbsentException;
import com.vijay.travel.model.Destination;
import com.vijay.travel.model.Itinerary;
import com.vijay.travel.repository.DestinationRepository;
import com.vijay.travel.repository.ItineraryRepository;
import com.vijay.travel.repository.UserRepository;
import com.vijay.travel.service.ItineraryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItineraryServiceImpl extends AbstractUserDependentEntityServiceImpl<Itinerary, UUID> implements ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final DestinationRepository destinationRepository;

    @Autowired
    public ItineraryServiceImpl(ItineraryRepository itineraryRepository, DestinationRepository destinationRepository, UserRepository userRepository) {

        super(userRepository);

        this.itineraryRepository = itineraryRepository;
        this.destinationRepository = destinationRepository;
    }

    @Override
    protected String extractUsernameFromEntity(Itinerary itinerary) {

        return itinerary.getDestination().getUser().getUsername();
    }

    @Override
    public Optional<Itinerary> getItineraryById(UUID uuid, Optional<String> matchUsername) {

        Assert.notNull(uuid, "UUID cannot be null");
        Assert.notNull(matchUsername, "Optional username cannot be null");

        return super.getEntityById(uuid, matchUsername, itineraryRepository);
    }


    @Override
    public Itinerary createItinerary(UUID destinationId, Itinerary itinerary, Optional<String> matchUsername) {

        Assert.notNull(destinationId, "Destination uuid cannot be null");
        Assert.notNull(itinerary, "Itinerary cannot be null");
        Assert.notNull(matchUsername, "Optional username cannot be null");

        String destinationAbsentErrMsg = "Destination with UUID: " + destinationId + " does not exist";
        Destination fetchedDestination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new DestinationAbsentException(destinationAbsentErrMsg));

        if (matchUsername.isPresent()) {

            String provided = matchUsername.get();
            String actual = fetchedDestination.getUser().getUsername();

            if (!provided.equals(actual)) {
                throw new DestinationAbsentException(destinationAbsentErrMsg);
            }
        }

        itinerary
                .setUuid(null)
                .setDestination(fetchedDestination);

        return itineraryRepository.save(itinerary);
    }

    @Override
    public Itinerary editItinerary(UUID uuid, Itinerary itinerary, Optional<String> matchUsername) {

        throw new RuntimeException("Method not implemented");
    }

	@Override
	public Page<Itinerary> getItinerariesByUsername(String username, int page, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Itinerary deleteItinerary(UUID uuid, Optional<String> matchUsername) {
		// TODO Auto-generated method stub
		return null;
	}

}
