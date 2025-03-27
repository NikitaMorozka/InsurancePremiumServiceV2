package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgeCoefficientRepository extends JpaRepository<AgeCoefficient, Long> {

    @Query("SELECT ac from AgeCoefficient ac " +
            "where ac.ageFrom <= :age and ac.ageTo >= :age")
    Optional<AgeCoefficient> findByAgeCoefficient(
            @Param("age") Integer age
    );
}
