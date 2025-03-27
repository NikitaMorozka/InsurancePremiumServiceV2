package org.javaguru.travel.insurance.core.domain;
import jakarta.persistence.*;//Импортируются аннотации JPA, которые нужны для связи класса с базой данных.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //Говорит, что этот класс — сущность, которая будет храниться в базе данных. Без этой аннотации JPA не поймёт,
        // что это не просто обычный класс.
@Table(name = "classifiers") // Указывает имя таблицы в базе
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


//Доменная модель — это Java-класс, который описывает данные, с которыми работает ваше приложение. В данном случае это
// класс Classifier, который представляет таблицу classifiers в базе данных. Он говорит: "Вот как выглядят мои данные,
// и вот как они хранятся в базе".

public class Classifier {
    @Id//указывает, что это первичный ключ
    @Column(name = "id") // связываем имя с наименованием в таблице
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерирует
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;


}


//Взаимодействие всех частей
//Доменная модель (Classifier):
//Описывает структуру данных и маппинг на таблицу classifiers.
//JPA использует эту информацию для создания таблицы и работы с данными.

//Репозиторий (ClassifierRepository):
//Предоставляет методы для доступа к данным (save, findByTitle).
//Spring Data JPA автоматически преобразует вызовы методов в SQL-запросы и выполняет их через Hibernate (реализацию JPA).

//Тесты (ClassifierRepositoryTest):
//Проверяют, что репозиторий работает правильно, вызывая его методы и проверяя результаты.
//@DataJpaTest обеспечивает тестовую среду, где H2 база создаётся на основе Classifier, а данные из data.sql загружаются для проверки