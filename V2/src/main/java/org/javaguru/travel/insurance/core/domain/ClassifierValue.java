package org.javaguru.travel.insurance.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "classifier_values")
@Setter
@Getter

@NoArgsConstructor
@AllArgsConstructor

public class ClassifierValue {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // генерируем числа по порядку
    private Long id;

    @ManyToOne // Связь "многие-к-одному" с Classifier
    @JoinColumn(name = "classifier_id", nullable = false)
    Classifier classifier;

    @Column(name = "ic", nullable = false)
    private String ic;

    @Column(name = "description",nullable = false)
    private String description;

}
