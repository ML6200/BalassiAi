package org.balassiai.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatResponse
{
    private Content content;

    public ChatResponse(String jsonResponse)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseObj = mapper.readTree(jsonResponse);

            if (responseObj.has("error"))
            {
                JsonNode errorObj = responseObj.get("error");
                if (errorObj.has("message"))
                {
                    this.content = new Content("API Error: " + errorObj.get("message").asText());
                } else
                {
                    this.content = new Content("API Error: " + errorObj.toString());
                }
            } else if (responseObj.has("choices"))
            {
                JsonNode choices = responseObj.get("choices");
                if (choices.isArray() && choices.size() > 0)
                {
                    JsonNode messageNode = choices.get(0).get("message");
                    if (messageNode != null && messageNode.has("content"))
                    {
                        this.content = new Content(messageNode.get("content").asText());
                    } else
                    {
                        this.content = new Content("No content in message.");
                    }
                } else
                {
                    this.content = new Content("No response from API.");
                }
            } else
            {
                this.content = new Content("Unexpected API response structure.");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            this.content = new Content("Error parsing API response: " + e.getMessage());
        }
    }

    public String getContent()
    {
        return this.content.getContent();
    }


    public Content withoutThink()
    {
        if (this.content == null)
        {
            return null;
        }

        // Regex to match <think> tags and their content
        String regex = "<think>.*?</think>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(this.content.getContent());

        // Replace all occurrences of <think> tags and their content with an empty string
        String replaced = matcher.replaceAll("").trim();

        return new Content(replaced);
    }
}
