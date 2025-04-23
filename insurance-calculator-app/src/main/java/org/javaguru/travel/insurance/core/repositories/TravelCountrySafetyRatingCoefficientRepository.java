package org.javaguru.travel.insurance.core.repositories;


import org.javaguru.travel.insurance.core.domain.cancellation.TravelCountrySafetyRatingCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelCountrySafetyRatingCoefficientRepository extends JpaRepository<TravelCountrySafetyRatingCoefficient, Long> {

    @Cacheable(cacheNames = {"travelCountrySafetyRatingCoefficientCache"}, unless="#result == null" )
    Optional<TravelCountrySafetyRatingCoefficient> findByCountryIc(String title);
}
