package org.doc.generator.core.api.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalGenerationAcknowledgment {

    private UUID agreementUuid;
    private String proposalFilePath;

}
