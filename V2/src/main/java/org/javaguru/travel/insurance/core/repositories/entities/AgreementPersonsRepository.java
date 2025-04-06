package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.Agreement;
import org.javaguru.travel.insurance.core.domain.entities.AgreementPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementPersonsRepository extends JpaRepository<AgreementPerson, Long> {
    List<AgreementPerson> findByAgreement(Agreement agreement);
}
