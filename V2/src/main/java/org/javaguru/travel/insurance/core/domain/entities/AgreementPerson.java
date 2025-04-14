package org.javaguru.travel.insurance.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "agreement_persons")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementPerson {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agreement_id", nullable = false)
    private Agreement agreement;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "medical_risk_limit_level")
    private String medicalRiskLimitLevel;
}
