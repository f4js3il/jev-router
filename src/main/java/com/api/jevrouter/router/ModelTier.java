package com.api.jevrouter.router;

import java.util.Locale;

/**
 * Claude model tiers, cheapest first. The Bedrock model ID for each tier comes from {@code router.tiers}.
 */
public enum ModelTier {

    HAIKU("Greetings, small talk, simple facts, basic formatting and short rewrites"),
    SONNET("Summaries, explanations, everyday coding and short multi-step tasks"),
    OPUS("Multi-step reasoning, code spanning several files, in-depth analysis"),
    FABLE("Research, system architecture, hard debugging, proofs and the most demanding problems");

    private final String description;

    ModelTier(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    /** Option label sent to Jev, e.g. {@code "haiku"}. */
    public String label() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static ModelTier fromLabel(String label) {
        return valueOf(label.toUpperCase(Locale.ROOT));
    }
}
