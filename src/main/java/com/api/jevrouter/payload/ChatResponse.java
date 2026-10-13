package com.api.jevrouter.payload;

import com.api.jevrouter.router.RoutingDecision;

public record ChatResponse(String answer, RoutingDecision routingDecision) {
}
