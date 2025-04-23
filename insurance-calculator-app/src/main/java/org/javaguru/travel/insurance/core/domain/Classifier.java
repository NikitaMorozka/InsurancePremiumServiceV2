package org.javaguru.travel.insurance.core.domain;
import jakarta.persistence.*;//Импортируются аннотации JPA, которые нужны для связи класса с базой данных.
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classifiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Classifier {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;


}


