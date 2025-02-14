package org.balassiai.core;

public class GroqApiService implements OpenAiApiService
{
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final OpenAiApiService service;

    public GroqApiService(String apiKey)
    {
        // Use the builder to set the fixed URL and optionally the API key.
        this.service = new OpenAiApiServiceBuilder()
                .withUrl(API_URL)
                .withApiKey(apiKey)
                .build();
    }

    @Override
    public ChatResponse sendMessage(ChatRequest request)
    {
        return service.sendMessage(request);
    }

    @Override
    public String getUrl()
    {
        return service.getUrl();
    }
}
