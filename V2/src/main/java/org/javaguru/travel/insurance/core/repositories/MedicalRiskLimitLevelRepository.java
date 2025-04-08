package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.medical.MedicalRiskLimitLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRiskLimitLevelRepository extends JpaRepository<MedicalRiskLimitLevel, Long> {
    Optional<MedicalRiskLimitLevel> findByMedicalRiskLimitLevelIc(String medicalRiskLevel);
}

