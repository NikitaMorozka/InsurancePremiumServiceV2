package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medical.CountryDefaultDayRate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CountryDefaultDayRateRepository extends JpaRepository<CountryDefaultDayRate, Long> {

    @Cacheable(cacheNames = {"countryDefaultDayRateCache"}, unless="#result == null" )
    @Query("SELECT cddr from CountryDefaultDayRate cddr " +
            "where cddr.countryIc = :countryIc")
    Optional<CountryDefaultDayRate> findDefaultDayRateByCountryIc(
            @Param("countryIc") String countryIc
    );
}
