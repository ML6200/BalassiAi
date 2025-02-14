package org.balassiai.core;

import org.balassiai.EmptyMessageException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the memory of a chat session.
 * <p>
 * This class manages a list of messages that are part of a chat session.
 * It provides methods to get, set, and add messages to the chat memory.
 */
public class ChatMemory
{
    private List<Message> messages = new ArrayList<>();

    /**
     * Gets the list of messages in the chat memory.
     *
     * @return the list of messages
     */
    public List<Message> getMessages()
    {
        return messages;
    }

    /**
     * Sets the list of messages in the chat memory.
     *
     * @param messages the list of messages to set
     */
    public void setMessages(List<Message> messages)
    {
        this.messages = messages;
    }

    /**
     * Adds a message to the chat memory.
     * <p>
     * This method adds a message to the chat memory. If the message content is empty,
     * it throws an EmptyMessageException.
     *
     * @param message the message to add
     * @throws RuntimeException if the message content is empty
     */
    public void addMessage(Message message)
    {
        if (message.getContent().isEmpty())
        {
            try
            {
                throw new EmptyMessageException();
            } catch (EmptyMessageException e)
            {
                throw new RuntimeException(e);
            }
        }
        messages.add(message);
    }

    /**
     * Adds a user message to the chat memory.
     * <p>
     * This method creates a new user message and adds it to the chat memory.
     *
     * @param message the user message to add
     */
    public void addUserMessage(String message)
    {
        addMessage(new Message("user", message));
    }

    /**
     * Adds a system message to the chat memory.
     * <p>
     * This method creates a new system message and adds it to the chat memory.
     *
     * @param message the system message to add
     */
    public void addSystemMessage(String message)
    {
        addMessage(new Message("system", message));
    }
}