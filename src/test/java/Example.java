import org.balassiai.core.*;
import org.balassiai.models.GroqModels;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;

public class Example
{
    public static final String apiKey = "API_KEY"; // Replace with actual key
    private static final String systemMessage = "Te a Balassagyarmati Balassi Bálint Gimnázium mesterséges intelligencia alapú robotja vagy. \n" +
            "A te neved Pepi.\n" +
            "A feladatod, hogy segíts a diákoknak a tanulásban és egyéb iskolához kötődő dolgokban.\n" +
            "Ha nem kérdezik, hogy hívnak, vagy nem kérdezik a neved, akkor csak a kérdésre válaszolj! " +
            "Ha be kell mutatkoznod, akkor azt röviden tedd meg!\n" +
            "Fontos, hogy röviden és érthetően válaszolj a kérdésekre.\n" +
            "Ha trágár kifejezésekkel kérdeznek akkor figyelmeztetsd őt, hogy illedelmesen beszéljen\n" +
            "(Mogyorósi Attlila: Az iskola igazgatója. Ő biológiát tanít.)" +
            "\n";

    public static void mainer(String[] args)
    {
        //asyncExample();
        //threadExample();
        threadWithGroqExample();
    }

    static void threadExample()
    {
        OpenAiApiService apiService = new OpenAiApiServiceBuilder()
                .withApiKey(apiKey)
                .withUrl("http://localhost:1234/v1/chat/completions").build();

        //new OpenAiApiService(apiKey, "http://localhost:1234/v1/chat/completions");

        //OpenAiApiService apiService = new GroqApiService(apiKey)
        ChatMemory memory = new ChatMemory();

        memory.addUserMessage("who are you?");

        new Thread(() ->
        {
            ChatRequest request = new ChatRequestBuilder()
                    .setModel("deepseek-r1-distill-qwen-7b")
                    .addSystemMessage(systemMessage)
                    .setMemory(memory)
                    .build();

            ChatResponse response = apiService.sendMessage(request);

            String res = response.withoutThink().getContent();

            System.out.println(res);
            JOptionPane.showMessageDialog(null, res);
        }).start();
    }

    static void asyncExample()
    {
        OpenAiApiServiceBuilder builder = new GroqApiServiceBuilder(apiKey);
        ChatMemory memory = new ChatMemory();

        memory.addSystemMessage(systemMessage);
        memory.addUserMessage("szia!");

        CompletableFuture<OpenAiApiService> asyncServiceFuture = builder.buildAsync();
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

    static void threadWithGroqExample()
    {
        OpenAiApiServiceBuilder builder = new GroqApiServiceBuilder(apiKey);
        ChatMemory memory = new ChatMemory();

        memory.addSystemMessage(systemMessage);
        memory.addUserMessage("szia!");

       new Thread(() ->
       {
           // Use the service asynchronously
           ChatRequest asyncRequest = new ChatRequestBuilder()
                   .setTemperature(1f)
                   .setModel(GroqModels.GEMMA2_9B_IT)
                   .setMemory(memory)
                   .build();

           ChatResponse asyncResponse = builder.build().sendMessage(asyncRequest);

           System.out.println(asyncResponse.withoutThink().getContent());
           JOptionPane.showMessageDialog(null, asyncResponse.withoutThink().getContent());
       }).start();
    }
}
