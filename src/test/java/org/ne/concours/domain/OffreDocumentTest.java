package org.ne.concours.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.ne.concours.web.rest.TestUtil;

public class OffreDocumentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffreDocument.class);
        OffreDocument offreDocument1 = new OffreDocument();
        offreDocument1.setId(1L);
        OffreDocument offreDocument2 = new OffreDocument();
        offreDocument2.setId(offreDocument1.getId());
        assertThat(offreDocument1).isEqualTo(offreDocument2);
        offreDocument2.setId(2L);
        assertThat(offreDocument1).isNotEqualTo(offreDocument2);
        offreDocument1.setId(null);
        assertThat(offreDocument1).isNotEqualTo(offreDocument2);
    }
}
