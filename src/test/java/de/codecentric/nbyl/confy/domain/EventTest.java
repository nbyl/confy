package de.codecentric.nbyl.confy.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EventTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Event.class)
                .verify();
    }

    @Test
    public void toStringIsCorrect() {
        assertThat(new Event().toString(), is("Event[id=null, name='null', location='null', startDate=null, endDate=null]"));
    }
}
