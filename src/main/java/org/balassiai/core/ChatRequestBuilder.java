package org.balassiai.core;

import com.szkkr.pepperai.backend.balassiai.EmptyMessageException;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRequestBuilder
{
    private String model;
    private List<Message> messages;
    private float temperature = 1f;

    public ChatRequestBuilder()
    {
        this.messages = new ArrayList<>();
    }

    public ChatRequestBuilder(@JsonProperty("model") String model,
                              @JsonProperty("messages") List<Message> messages)
    {
        this.model = model;
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    public ChatRequestBuilder(@JsonProperty("model") String model,
                              @JsonProperty("messages") List<Message> messages,
                              @JsonProperty("temperature") float temperature)
    {
        this.model = model;
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    public ChatRequestBuilder setModel(String model)
    {
        this.model = model;
        return this;
    }

    public ChatRequestBuilder addMessage(Message message)
    {
        this.messages.add(message);
        return this;
    }

    public ChatRequestBuilder addUserMessage(String message)
    {
        return addMessage(new Message("user", message));
    }

    //Does not work properly
    public ChatRequestBuilder addSystemMessage(String message)
    {
        return addMessage(new Message("system", message));
    }

    public ChatRequestBuilder setMemory(ChatMemory memory)
    {
        if (memory == null || memory.getMessages().isEmpty())
        {
            try
            {
                throw new EmptyMessageException();
            } catch (EmptyMessageException e)
            {
                throw new RuntimeException(e);
            }
        }

        if (!(this.messages.isEmpty()))
        {
            this.messages = memory.getMessages();
            return this;
        }

        this.messages.addAll(memory.getMessages());

        return this;
    }

    public ChatRequest build()
    {
        return new ChatRequestImpl(model, messages, getTemperature());
    }

    public float getTemperature()
    {
        return temperature;
    }

    public ChatRequestBuilder setTemperature(float temperature)
    {
        this.temperature = temperature;
        return this;
    }

    // Private immutable implementation of ChatRequest.
    private static class ChatRequestImpl implements ChatRequest
    {
        private final String model;
        private final List<Message> messages;
        private final float temperature;

        public ChatRequestImpl(String model, List<Message> messages, float temperature)
        {
            this.model = model;
            // Create a defensive copy for immutability.
            this.messages = messages != null ? new ArrayList<>(messages) : new ArrayList<>();
            this.temperature = temperature;
        }

        @Override
        public String getModel()
        {
            return model;
        }

        @Override
        public List<Message> getMessages()
        {
            return Collections.unmodifiableList(messages);
        }

        @Override
        public float getTemperature()
        {
            return this.temperature;
        }
    }
}
