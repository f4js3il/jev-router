package com.api.jevrouter.router;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.typesafe.TypeSafeClient;
import org.springaicommunity.typesafe.question.Choice;
import org.springaicommunity.typesafe.response.ChoiceAnswer;
import org.springaicommunity.typesafe.response.SystemOneResponse;
import org.springframework.stereotype.Service;

/**
 * Asks Jev which {@link ModelTier} is the cheapest one that can still answer a prompt well.
 */
@Service
public class ModelRouter {

    private static final Logger log = LoggerFactory.getLogger(ModelRouter.class);

    private static final String QUESTION = "tier";

    private final TypeSafeClient typeSafeClient;
    private final RouterProperties routerProperties;
    private final Choice choice;

    public ModelRouter(TypeSafeClient typeSafeClient, RouterProperties routerProperties) {
        this.typeSafeClient = typeSafeClient;
        this.routerProperties = routerProperties;

        Choice.Builder choiceBuilder = Choice.builder()
                .instructions("Which is the cheapest model tier that can still answer this prompt well? "
                        + "Prefer the cheaper tier unless the prompt clearly needs more capability.");
        for (ModelTier modelTier : ModelTier.values()) {
            choiceBuilder.option(modelTier.label(), modelTier.description());
        }
        this.choice = choiceBuilder.build();
    }

    public RoutingDecision route(String prompt) {
        SystemOneResponse systemOneResponse = typeSafeClient.systemOne(prompt, Map.of(QUESTION, choice));
        ChoiceAnswer choiceAnswer = systemOneResponse.choice(QUESTION);

        ModelTier modelTier = ModelTier.fromLabel(choiceAnswer.value());
        String model = routerProperties.modelFor(modelTier);
        log.info("Routed prompt to {} ({}) with confidence {}", modelTier.label(), model, choiceAnswer.confidence());

        return new RoutingDecision(modelTier, model, choiceAnswer.confidence(), choiceAnswer.probabilities());
    }
}
