package org.javaguru.travel.insurance.core.repositories.entities;

import org.javaguru.travel.insurance.core.domain.entities.Agreement;
import org.javaguru.travel.insurance.core.domain.entities.SelectedRisks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectRisksRepository extends JpaRepository<SelectedRisks, Long> {
    List<SelectedRisks> findByAgreement(Agreement agreement);
}
