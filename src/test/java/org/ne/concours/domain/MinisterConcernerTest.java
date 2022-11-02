package org.ne.concours.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.ne.concours.web.rest.TestUtil;

public class MinisterConcernerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MinisterConcerner.class);
        MinisterConcerner ministerConcerner1 = new MinisterConcerner();
        ministerConcerner1.setId(1L);
        MinisterConcerner ministerConcerner2 = new MinisterConcerner();
        ministerConcerner2.setId(ministerConcerner1.getId());
        assertThat(ministerConcerner1).isEqualTo(ministerConcerner2);
        ministerConcerner2.setId(2L);
        assertThat(ministerConcerner1).isNotEqualTo(ministerConcerner2);
        ministerConcerner1.setId(null);
        assertThat(ministerConcerner1).isNotEqualTo(ministerConcerner2);
    }
}
