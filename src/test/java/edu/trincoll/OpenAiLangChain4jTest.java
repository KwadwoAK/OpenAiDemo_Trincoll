package edu.trincoll;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OpenAiLangChain4jTest {
    private final String apiKey = System.getenv("OPENAI_API_KEY");

    private final ChatLanguageModel chatModel = OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                //.modelName("o1-preview")
                .build();

    @Test
    void chatWithMessages() {
        ChatResponse response = chatModel.chat(ChatRequest.builder()
                .messages(List.of(
                        new UserMessage("""
                    What holiday is on May 4th
                    ?""")))
                .build());
        System.out.println(response.aiMessage().text());
        System.out.println(response.tokenUsage());
    }

    @Test
   void chatWithString() {
        String answer = chatModel.generate("""
                name the fastest vehicle in the world currently?
                """);
        System.out.println(answer);
    }

    @Test
    void visionChat() {
        String imageUrl = "https://commondatastorage.googleapis.com/codeskulptor-demos/riceracer_assets/img/car_3.png";
        Response<AiMessage> response = chatModel.generate(
                UserMessage.from(
                        ImageContent.from(imageUrl),
                        TextContent.from("What do you see?")
                )
        );
        System.out.println(response.content().text());
    }

}
