# Groq API Service

Groq API Service is a Java library designed to simplify interactions with the Groq API as well as the OpenAI API for chat-based applications. It abstracts the complexities of sending chat requests and managing responses, enabling you to focus on building your application logic.

## Table of Contents

- [Features](#features)
- [Usage](#usage)
- [Examples](#examples)
    - [LmStudio (localhost) Example](#lmstudio-localhost-example)
    - [Groq Example](#groq-example)
- [Contributing](#contributing)
- [License](#license)
- [Repository](#repository)

## Features

- **GroqApiService**: A service class for interacting with the Groq API.
- **GroqApiServiceBuilder**: A builder class for creating instances of `GroqApiService`.
- **Message**: A model class representing a chat message.
- **OpenAiApiService**: An interface for interacting with the OpenAI API.
- **OpenAiApiServiceBuilder**: A builder class for creating instances of `OpenAiApiService`.
- **ChatMemory**: A class to manage and persist chat session memory.

## Usage

This library is crafted to seamlessly integrate chat functionality into your Java application. It manages the low-level details of API requests and responses while maintaining session context through chat memory.

## Examples

### LmStudio (localhost) Example

This example demonstrates how to use the `OpenAiApiService` with basic functionality to send a chat request and display the response:

```java
OpenAiApiService apiService = new OpenAiApiServiceBuilder()
        .withUrl("http://localhost:1234/v1/chat/completions")
        .build();

ChatMemory memory = new ChatMemory();
memory.addUserMessage("who are you?");

ChatRequest request = new ChatRequestBuilder()
        .setModel("deepseek-r1-distill-qwen-7b")
        .addSystemMessage("systemMessage")
        .setMemory(memory)
        .build();

ChatResponse response = apiService.sendMessage(request);
String result = response.withoutThink().getContent();

System.out.println(result);
