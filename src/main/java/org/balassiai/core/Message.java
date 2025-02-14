package org.balassiai.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a message in the chat system.
 * <p>
 * This class encapsulates the role and content of a message.
 */
public class Message
{
    private final String role;
    private final String content;

    /**
     * Constructor to initialize a Message with a role and content.
     *
     * @param role    the role of the message sender (e.g., user, system)
     * @param content the content of the message
     */
    public Message(@JsonProperty("role") String role, @JsonProperty("content") String content)
    {
        this.role = role;
        this.content = content;
    }

    /**
     * Gets the role of the message sender.
     *
     * @return the role as a string
     */
    public String getRole()
    {
        return role;
    }

    /**
     * Gets the content of the message.
     *
     * @return the content as a string
     */
    public String getContent()
    {
        return content;
    }
}