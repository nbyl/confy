package de.codecentric.nbyl.confy.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpeakerTest {

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Speaker.class)
                .withPrefabValues(Talk.class, new Talk("Talk 1"), new Talk("Talk 2"))
                .verify();
    }

    @Test
    void toStringIsCorrect() {
        assertEquals("Speaker[id=null, surname='null', firstName='null', talks=[]]",
                new Speaker().toString());
    }
}
