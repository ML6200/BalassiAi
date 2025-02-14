package org.balassiai.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.balassiai.exceptions.EmptyMessageException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder class for creating ChatRequest instances.
 *
 * @author Mark Lorincz
 */
public class ChatRequestBuilder
{
    private String model;
    private List<Message> messages;
    private float temperature = 1f;

    /**
     * Default constructor initializing an empty message list.
     */
    public ChatRequestBuilder()
    {
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor initializing the builder with a model and messages.
     *
     * @param model the model to be used
     * @param messages the list of messages
     */
    public ChatRequestBuilder(@JsonProperty("model") String model,
                              @JsonProperty("messages") List<Message> messages)
    {
        this.model = model;
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    /**
     * Constructor initializing the builder with a model, messages, and temperature.
     *
     * @param model the model to be used
     * @param messages the list of messages
     * @param temperature the temperature setting
     */
    public ChatRequestBuilder(@JsonProperty("model") String model,
                              @JsonProperty("messages") List<Message> messages,
                              @JsonProperty("temperature") float temperature)
    {
        this.model = model;
        this.messages = messages != null ? messages : new ArrayList<>();
    }

    /**
     * Sets the model for the chat request.
     *
     * @param model the model to be set
     * @return the current instance of ChatRequestBuilder
     */
    public ChatRequestBuilder setModel(String model)
    {
        this.model = model;
        return this;
    }

    /**
     * Adds a message to the chat request.
     *
     * @param message the message to be added
     * @return the current instance of ChatRequestBuilder
     */
    public ChatRequestBuilder addMessage(Message message)
    {
        this.messages.add(message);
        return this;
    }

    /**
     * Adds a user message to the chat request.
     *
     * @param message the user message to be added
     * @return the current instance of ChatRequestBuilder
     */
    public ChatRequestBuilder addUserMessage(String message)
    {
        return addMessage(new Message("user", message));
    }

    /**
     * Adds a system message to the chat request.
     *
     * @param message the system message to be added
     * @return the current instance of ChatRequestBuilder
     */
    public ChatRequestBuilder addSystemMessage(String message)
    {
        return addMessage(new Message("system", message));
    }

    /**
     * Sets the memory for the chat request.
     *
     * @param memory the chat memory to be set
     * @return the current instance of ChatRequestBuilder
     * @throws RuntimeException if the memory is null or empty
     */
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

    /**
     * Builds and returns a ChatRequest instance.
     *
     * @return a new ChatRequest instance
     */
    public ChatRequest build()
    {
        return new ChatRequestImpl(model, messages, getTemperature());
    }

    /**
     * Gets the temperature setting.
     *
     * @return the temperature setting
     */
    public float getTemperature()
    {
        return temperature;
    }

    /**
     * Sets the temperature for the chat request.
     *
     * @param temperature the temperature to be set
     * @return the current instance of ChatRequestBuilder
     */
    public ChatRequestBuilder setTemperature(float temperature)
    {
        this.temperature = temperature;
        return this;
    }

    /**
     * Private immutable implementation of ChatRequest.
     */
    private static class ChatRequestImpl implements ChatRequest
    {
        private final String model;
        private final List<Message> messages;
        private final float temperature;

        /**
         * Constructor initializing the ChatRequestImpl with model, messages, and temperature.
         *
         * @param model the model to be used
         * @param messages the list of messages
         * @param temperature the temperature setting
         */
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