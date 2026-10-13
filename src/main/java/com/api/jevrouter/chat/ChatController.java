package com.api.jevrouter.chat;

import com.api.jevrouter.payload.ChatRequest;
import com.api.jevrouter.payload.ChatResponse;
import com.api.jevrouter.router.ModelRouter;
import com.api.jevrouter.router.RoutingDecision;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final ModelRouter modelRouter;

    public ChatController(ChatClient.Builder chatClientBuilder, ModelRouter modelRouter) {
        this.chatClient = chatClientBuilder.build();
        this.modelRouter = modelRouter;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        RoutingDecision routingDecision = modelRouter.route(chatRequest.prompt());

        String answer = chatClient.prompt()
                .user(chatRequest.prompt())
                .options(ChatOptions.builder().model(routingDecision.model()))
                .call()
                .content();

        return new ChatResponse(answer, routingDecision);
    }
}
