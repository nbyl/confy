package de.codecentric.nbyl.confy.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TalkTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Talk.class)
                .withPrefabValues(Talk.class, new Talk(1l), new Talk(2l))
                .verify();
    }

    @Test
    public void toStringIsCorrect() {
        assertThat(new Talk().toString(), is("Talk[id=null, title='null', description='null', startTime=null, heldAt=null, speakers=[]]"));
    }

}
