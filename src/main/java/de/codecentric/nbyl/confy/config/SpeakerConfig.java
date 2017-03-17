package de.codecentric.nbyl.confy.config;

import de.codecentric.nbyl.confy.domain.speakers.Speaker;
import de.codecentric.nbyl.confy.domain.speakers.SpeakerCommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpeakerConfig {

    @Autowired
    private EventStore eventStore;

    @Bean
    public AggregateFactory<Speaker> speakerAggregateFactory() {
        return new GenericAggregateFactory(Speaker.class);
    }

    @Bean
    public Repository<Speaker> speakerRepository() {
        return new EventSourcingRepository(speakerAggregateFactory(), eventStore);
    }

    @Bean
    public SpeakerCommandHandler speakerCommandHandler() {
        return new SpeakerCommandHandler(speakerRepository());
    }
}
