package org.javaguru.travel.insurance.core.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "agreements_xml_export")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class AgreementXmlExport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_uuid",  nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID agreementUuid;

    @Column(name = "already_exported", columnDefinition = "TINYINT(1)")
    private Boolean alreadyExported;

}
