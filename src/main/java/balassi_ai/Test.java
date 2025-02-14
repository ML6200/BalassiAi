package balassi_ai;


import balassi_ai.core.*;
import balassi_ai.models.GroqModels;

import java.util.concurrent.CompletableFuture;

public class Test
{
    public static final String apiKey = "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"; // Replace with actual key
    private static final String systemMessage = "Te a Balassagyarmati Balassi Bálint Gimnázium mesterséges intelligencia alapú robotja vagy. \n" +
            "A te neved Pepi.\n" +
            "A feladatod, hogy segíts a diákoknak a tanulásban és egyéb iskolához kötődő dolgokban.\n" +
            "Ha nem kérdezik, hogy hívnak, vagy nem kérdezik a neved, akkor csak a kérdésre válaszolj! " +
            "Ha be kell mutatkoznod, akkor azt röviden tedd meg!\n" +
            "Érthetően válaszolj a kérdésekre.\n" +
            "Ha trágár kifejezésekkel kérdeznek akkor figyelmeztetsd őt, hogy illedelmesen beszéljen\n" +
            "(Mogyorósi Attlila: Az iskola igazgatója. Ő biológiát tanít.)" +
            "\n";

    public static void main(String[] args)
    {
        asyncExample();
    }

    static void threadExample()
    {
        OpenAiApiService apiService = new OpenAiApiServiceBuilder()
                .withApiKey(apiKey)
                .withUrl("http://localhost:1234/v1/chat/completions").build();

        //new OpenAiApiService(apiKey, "http://localhost:1234/v1/chat/completions");

        //OpenAiApiService apiService = new GroqApiService(apiKey)
        ChatMemory memory = new ChatMemory();

        memory.addUserMessage("ki vagy?");

        new Thread(() ->
        {
            ChatRequest request = new ChatRequestBuilder()
                    .setModel("mistral-nemo-instruct-2407")
                    .addSystemMessage(systemMessage)
                    .setMemory(memory)
                    .build();

            ChatResponse response = apiService.sendMessage(request);

            String res = response.getContent();

            System.out.println(res);
        }).start();
    }

    static void asyncExample()
    {
        OpenAiApiServiceBuilder builder = new GroqApiServiceBuilder(apiKey);
        ChatMemory memory = new ChatMemory();

        memory.addSystemMessage(systemMessage);
        memory.addUserMessage("szia!");

        CompletableFuture<OpenAiApiService> asyncServiceFuture = CompletableFuture.supplyAsync(builder::build);
        asyncServiceFuture.thenAccept(asyncService ->
        {
            // Use the service asynchronously
            ChatRequest asyncRequest = new ChatRequestBuilder()
                    .setModel(GroqModels.LLAMA3_3_70B_VERSATILE)
                    .setMemory(memory)
                    .build();
            // Populate asyncRequest
            ChatResponse asyncResponse = asyncService.sendMessage(asyncRequest);
            // Handle asyncResponse
            System.out.println(asyncResponse.getContent());
        }).exceptionally(ex -> {
            System.err.println("Error during asynchronous service creation: " + ex.getMessage());
            return null;
        });
    }
}
