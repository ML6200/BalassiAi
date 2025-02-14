package balassi_ai.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message
{
    private final String role;
    private final String content;

    public Message(@JsonProperty("role") String role, @JsonProperty("content") String content)
    {
        this.role = role;
        this.content = content;
    }

    public String getRole()
    {
        return role;
    }

    public String getContent()
    {
        return content;
    }
}
