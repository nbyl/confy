package de.codecentric.nbyl.confy.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SpeakerTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Speaker.class)
                .withPrefabValues(Talk.class, new Talk("Talk 1"), new Talk("Talk 2"))
                .verify();
    }

    @Test
    public void toStringIsCorrect() {
        assertThat(new Speaker().toString(), is("Speaker[id=null, surname='null', firstName='null', talks=[]]"));
    }
}
