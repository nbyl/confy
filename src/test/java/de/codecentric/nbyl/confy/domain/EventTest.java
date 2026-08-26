package de.codecentric.nbyl.confy.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventTest {

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Event.class)
                .verify();
    }

    @Test
    void toStringIsCorrect() {
        assertEquals("Event[id=null, name='null', location='null', startDate=null, endDate=null]",
                new Event().toString());
    }
}
