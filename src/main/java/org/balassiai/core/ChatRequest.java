package org.balassiai.core;

import java.util.List;

/**
 * Interface representing a chat request.
 *
 * This interface defines the structure of a chat request, including methods
 * to get the model, messages, and temperature for the request.
 */
public interface ChatRequest
{
    /**
     * Gets the model used for the chat request.
     *
     * @return the model as a string
     */
    String getModel();

    /**
     * Gets the list of messages in the chat request.
     *
     * @return the list of messages
     */
    List<Message> getMessages();

    /**
     * Gets the temperature setting for the chat request.
     *
     * @return the temperature as a float
     */
    float getTemperature();
}