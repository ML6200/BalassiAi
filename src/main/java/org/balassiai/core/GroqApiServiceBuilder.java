package org.balassiai.core;

public class GroqApiServiceBuilder extends OpenAiApiServiceBuilder
{
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public GroqApiServiceBuilder(String apiKey)
    {
        // Use the builder to set the fixed URL and optionally the API key.
        this.withApiKey(apiKey);
        this.withUrl(API_URL);
    }
}
