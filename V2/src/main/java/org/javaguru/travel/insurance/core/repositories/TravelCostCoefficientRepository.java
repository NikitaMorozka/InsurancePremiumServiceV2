package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.cancellation.TravelCostCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface TravelCostCoefficientRepository extends JpaRepository<TravelCostCoefficient, Long> {
    @Query("SELECT tcc from TravelCostCoefficient tcc " +
            "where tcc.travelCostFrom <= :cost and tcc.travelCostTo >= :cost")
    Optional<TravelCostCoefficient> findByCostCoefficient(
            @Param("cost") BigDecimal cost
    );
}
