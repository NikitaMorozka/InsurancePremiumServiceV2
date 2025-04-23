package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    Optional<Agreement> findByUuid(UUID uuid);

    @Query(value = "SELECT agr.uuid " +
            "FROM agreements agr " +
            "WHERE agr.uuid NOT IN (SELECT agreement_uuid FROM agreements_xml_export)", nativeQuery = true)
    List<byte[]> getNotExportedAgreementUuids();
}
