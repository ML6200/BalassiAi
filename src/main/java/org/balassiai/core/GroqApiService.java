package org.balassiai.core;

/**
 * Service class for interacting with the Groq API.
 * <p>
 * This class implements the OpenAiApiService interface and provides methods
 * to send messages and get the API URL.
 */
public class GroqApiService implements OpenAiApiService
{
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private final OpenAiApiService service;

    /**
     * Constructor to initialize the GroqApiService with an API key.
     *
     * @param apiKey the API key to use for authentication
     */
    public GroqApiService(String apiKey)
    {
        // Use the builder to set the fixed URL and optionally the API key.
        this.service = new OpenAiApiServiceBuilder()
                .withUrl(API_URL)
                .withApiKey(apiKey)
                .build();
    }

    /**
     * Sends a chat request to the Groq API.
     *
     * @param request the chat request to send
     * @return the chat response from the API
     */
    @Override
    public ChatResponse sendMessage(ChatRequest request)
    {
        return service.sendMessage(request);
    }

    /**
     * Gets the URL of the Groq API.
     *
     * @return the API URL as a string
     */
    @Override
    public String getUrl()
    {
        return service.getUrl();
    }
}