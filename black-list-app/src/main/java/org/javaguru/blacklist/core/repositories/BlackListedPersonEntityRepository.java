package org.javaguru.blacklist.core.repositories;

import org.javaguru.blacklist.core.domain.BlackListedPersonEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlackListedPersonEntityRepository extends JpaRepository<BlackListedPersonEntity, Long> {
    @Cacheable(cacheNames = {"blackListedPersonByParams"}, key = "{#firstName, #lastName, #personCode}", unless = "#result.isEmpty()")
    @Query("SELECT pe from BlackListedPersonEntity pe " +
            "where pe.firstName = :firstName " +
            "      and pe.lastName = :lastName " +
            "      and pe.personCode = :personCode")
    Optional<BlackListedPersonEntity> findBy(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("personCode") String personCode
    );

    @Cacheable(cacheNames = {"blackListedPersonEntity"}, unless = "#result.isEmpty()")
    @Query("SELECT pe FROM BlackListedPersonEntity pe")
    List<BlackListedPersonEntity> findAllBlackListedPersons();

    @CacheEvict(cacheNames = {"blackListedPersonEntity", "blackListedPersonByParams"}, allEntries = true)
    void deleteById(Long id);
}
