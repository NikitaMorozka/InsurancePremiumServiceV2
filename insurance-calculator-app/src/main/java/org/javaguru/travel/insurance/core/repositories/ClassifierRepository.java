package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.Classifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//Репозиторий — это интерфейс, который предоставляет методы для работы с данными в базе (сохранение, поиск, удаление).
// В Spring Data JPA вам не нужно писать реализацию — Spring делает это за вас автоматически.
@Repository
public interface ClassifierRepository extends JpaRepository<Classifier, Long> {

    @Cacheable(cacheNames = {"classifierCache"}, unless="#result == null" )
    Optional<Classifier> findByTitle(String title);
}
