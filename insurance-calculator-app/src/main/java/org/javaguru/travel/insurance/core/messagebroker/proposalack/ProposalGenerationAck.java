package org.javaguru.travel.insurance.core.messagebroker.proposalack;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProposalGenerationAck {

    private UUID agreementUuid;
    private String proposalFilePath;

}
