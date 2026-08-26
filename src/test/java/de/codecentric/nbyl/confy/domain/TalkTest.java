package de.codecentric.nbyl.confy.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TalkTest {

    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Talk.class)
                .withPrefabValues(Talk.class, new Talk("Talk 1"), new Talk("Talk 2"))
                .verify();
    }

    @Test
    void toStringIsCorrect() {
        assertEquals("Talk[id=null, title='null', description='null', startTime=null, heldAt=null, speakers=[]]",
                new Talk().toString());
    }
}
