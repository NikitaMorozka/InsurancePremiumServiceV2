package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medical.TMAgeCoefficient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TMAgeCoefficientRepository extends JpaRepository<TMAgeCoefficient, Long> {

    @Cacheable(cacheNames = {"tmAgeCoefficientCache"}, unless="#result == null" )
    @Query("SELECT ac from TMAgeCoefficient ac " +
            "where ac.ageFrom <= :age and ac.ageTo >= :age")
    Optional<TMAgeCoefficient> findByAgeCoefficient(
            @Param("age") Integer age
    );
}
