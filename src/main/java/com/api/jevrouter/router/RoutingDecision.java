package com.api.jevrouter.router;

import java.util.Map;

/**
 * Jev's routing result: the chosen tier, its Bedrock model, Jev's confidence and the probability of every tier.
 */
public record RoutingDecision(ModelTier modelTier,String model, double confidence, Map<String, Double> probabilities) {
}
