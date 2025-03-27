package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.Classifier;
import org.springframework.data.jpa.repository.JpaRepository;//: Импортируется интерфейс JpaRepository, который предоставляет базовые методы CRUD
import org.springframework.stereotype.Repository;

import java.util.Optional;


//Репозиторий — это интерфейс, который предоставляет методы для работы с данными в базе (сохранение, поиск, удаление).
// В Spring Data JPA вам не нужно писать реализацию — Spring делает это за вас автоматически.
@Repository
public interface ClassifierRepository extends JpaRepository<Classifier, Long> {
    Optional<Classifier> findByTitle(String title);
}
