package balassi_ai.core;

public interface OpenAiApiService
{
    ChatResponse sendMessage(ChatRequest request);

    String getUrl();
}
