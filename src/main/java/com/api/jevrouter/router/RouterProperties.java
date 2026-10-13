package com.api.jevrouter.router;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Maps each {@link ModelTier} to a Bedrock model ID, bound from {@code router.tiers}.
 */
@ConfigurationProperties("router")
public record RouterProperties(Map<ModelTier, String> tiers) {

    public RouterProperties {
        tiers = tiers == null ? Map.of() : Map.copyOf(tiers);
        Set<ModelTier> missingModelTiers = EnumSet.allOf(ModelTier.class);
        missingModelTiers.removeAll(tiers.keySet());
        if (!missingModelTiers.isEmpty()) {
            throw new IllegalStateException("router.tiers is missing a model for: " + missingModelTiers);
        }
    }

    public String modelFor(ModelTier modelTier) {
        return tiers.get(modelTier);
    }
}
