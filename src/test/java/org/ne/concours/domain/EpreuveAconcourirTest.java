package org.ne.concours.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.ne.concours.web.rest.TestUtil;

public class EpreuveAconcourirTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EpreuveAconcourir.class);
        EpreuveAconcourir epreuveAconcourir1 = new EpreuveAconcourir();
        epreuveAconcourir1.setId(1L);
        EpreuveAconcourir epreuveAconcourir2 = new EpreuveAconcourir();
        epreuveAconcourir2.setId(epreuveAconcourir1.getId());
        assertThat(epreuveAconcourir1).isEqualTo(epreuveAconcourir2);
        epreuveAconcourir2.setId(2L);
        assertThat(epreuveAconcourir1).isNotEqualTo(epreuveAconcourir2);
        epreuveAconcourir1.setId(null);
        assertThat(epreuveAconcourir1).isNotEqualTo(epreuveAconcourir2);
    }
}
