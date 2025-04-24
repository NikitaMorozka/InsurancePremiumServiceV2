package org.doc.generator.core.messagebroker;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_PROPOSAL_GENERATION = "q.proposal-generation";
    public static final String QUEUE_PROPOSAL_GENERATION_DLQ = "q.proposal-generation-dlq";

    @Bean
    public Queue createProposalPdfGenerationQueue() {
        return new Queue(QUEUE_PROPOSAL_GENERATION);
    }

}