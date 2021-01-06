package com.radixdlt.atommodel.tokens;

import com.google.common.hash.HashCode;
import com.radixdlt.crypto.HashUtils;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class FixedSupplyTokenDefinitionParticleTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(FixedSupplyTokenDefinitionParticle.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withIgnoredFields("version")
                .withPrefabValues(HashCode.class, HashUtils.random256(), HashUtils.random256())
                .verify();
    }
}
