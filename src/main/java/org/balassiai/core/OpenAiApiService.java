package org.balassiai.core;

public interface OpenAiApiService
{
    ChatResponse sendMessage(ChatRequest request);

    String getUrl();
}
