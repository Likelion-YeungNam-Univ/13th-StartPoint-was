package startpointwas.domain.practical.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.client.ChatClient;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(org.springframework.ai.openai.OpenAiChatModel model) {
        return ChatClient.builder(model).build();
    }
}