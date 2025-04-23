package org.javaguru.travel.insurance.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "person_risks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRisks {
    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_risks_id")
    private AgreementPerson agreementPerson;

    @Column(name = "risk_Ic")
    private String riskIc;

    @Column(name = "premium", precision = 10, scale = 2, nullable = false)
    private BigDecimal premium;

}
