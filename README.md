# Groq API Service

This project provides a Java service for interacting with the Groq API. It includes classes and interfaces to send chat requests and handle responses.

## Features

- **GroqApiService**: Service class for interacting with the Groq API.
- **GroqApiServiceBuilder**: Builder class for creating instances of `GroqApiService`.
- **Message**: Class representing a message in the chat system.
- **OpenAiApiService**: Interface for interacting with the OpenAI API.
- **OpenAiApiServiceBuilder**: Builder class for creating instances of `OpenAiApiService`.
- **ChatMemory**: Class representing the memory of a chat session.


## Examples

### LmStudio(localhost) Example

This example demonstrates how to use the OpenAiApiService with basic functionality to send a chat request and display the response

```java
OpenAiApiService builder = new OpenAiApiServiceBuilder()
        .withUrl("http://localhost:1234/v1/chat/completions")
        .build();

ChatMemory memory = new ChatMemory();
memory.addUserMessage("who are you?");

ChatRequest request = new ChatRequestBuilder()
        .setModel("deepseek-r1-distill-qwen-7b")
        .addSystemMessage(systemMessage)
        .setMemory(memory)
        .build();

ChatResponse response = apiService.sendMessage(request);
String result = response.withoutThink().getContent();

System.out.println(result);
```


### Groq Example
This example demonstrates how to use the OpenAiApiService with basic functionality: a specific model to send a chat request and display the response in a dialog.

```java

OpenAiApiService apiService = new GroqApiServiceBuilder("API_KEY")
        .build();

ChatMemory memory = new ChatMemory();
memory.addSystemMessage("You are a helpful assistant!");
memory.addUserMessage("Hi!");

ChatRequest request = new ChatRequestBuilder()
        .setTemperature(1f)
        .setModel(GroqModels.GEMMA2_9B_IT)
        .setMemory(memory)
        .build();

ChatResponse response = apiService.sendMessage(request);

System.out.println(response.withoutThink().getContent());
```

