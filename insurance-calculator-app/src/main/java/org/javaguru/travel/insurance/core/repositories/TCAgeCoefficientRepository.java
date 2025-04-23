package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.cancellation.TCAgeCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TCAgeCoefficientRepository extends JpaRepository<TCAgeCoefficient, Long> {

    @Cacheable(cacheNames = {"tcAgeCoefficientCache"}, unless="#result == null" )
    @Query("SELECT ac from TCAgeCoefficient ac " +
            "where ac.ageFrom <= :age " +
            "and ac.ageTo >= :age")
    Optional<TCAgeCoefficient> findByAgeCoefficient(@Param("age") Integer age);
}
