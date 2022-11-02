package org.ne.concours.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.ne.concours.web.rest.TestUtil;

public class DocumentAFournirTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentAFournir.class);
        DocumentAFournir documentAFournir1 = new DocumentAFournir();
        documentAFournir1.setId(1L);
        DocumentAFournir documentAFournir2 = new DocumentAFournir();
        documentAFournir2.setId(documentAFournir1.getId());
        assertThat(documentAFournir1).isEqualTo(documentAFournir2);
        documentAFournir2.setId(2L);
        assertThat(documentAFournir1).isNotEqualTo(documentAFournir2);
        documentAFournir1.setId(null);
        assertThat(documentAFournir1).isNotEqualTo(documentAFournir2);
    }
}
