<h1 style="color: red;">This repository is still under development.</h1>

# Groq API Service

Groq API Service is a Java library designed to simplify interactions with the Groq API as well as the OpenAI API for chat-based applications. It abstracts the complexities of sending chat requests and managing responses, enabling you to focus on building your application logic.

## Table of Contents

- [Features](#features)
- [Usage](#usage)
- [Example](#example)
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

## Example

This example shows how to use the `GroqApiServiceBuilder` for sending a chat request using a specific model and then displaying the response:

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
