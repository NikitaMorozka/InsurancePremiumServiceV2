package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.AgreementXmlExport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgreementXmlExportRepository extends JpaRepository<AgreementXmlExport, Long> {
    Optional<AgreementXmlExport> findByAgreementUuid(UUID uuid);
}
