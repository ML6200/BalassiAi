package org.balassiai.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OpenAiApiServiceBuilder
{
    private static final Logger logger = LoggerFactory.getLogger(OpenAiApiServiceBuilder.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String url;
    private String apiKey;
    private Executor executor = Executors.newSingleThreadExecutor();

    public OpenAiApiServiceBuilder withUrl(String url)
    {
        this.url = url;
        return this;
    }

    public OpenAiApiServiceBuilder withApiKey(String apiKey)
    {
        this.apiKey = apiKey;
        return this;
    }

    public OpenAiApiServiceBuilder withExecutor(Executor executor)
    {
        this.executor = executor;
        return this;
    }

    public OpenAiApiService build()
    {
        validateFields();
        return new OpenAiApiServiceImpl(url, apiKey);
    }

    public CompletableFuture<OpenAiApiService> buildAsync()
    {
        return CompletableFuture.supplyAsync(() ->
        {
            validateFields();
            return new OpenAiApiServiceImpl(url, apiKey);
        }, executor);
    }

    private void validateFields()
    {
        if (url == null || url.trim().isEmpty())
        {
            throw new IllegalStateException("URL must be provided.");
        }
        // Additional validation can be added here if needed
    }

    private static class OpenAiApiServiceImpl implements OpenAiApiService
    {
        private final String url;
        private final String apiKey;

        OpenAiApiServiceImpl(String url, String apiKey)
        {
            this.url = url;
            this.apiKey = apiKey;
        }

        @Override
        public String getUrl()
        {
            return url;
        }

        @Override
        public ChatResponse sendMessage(ChatRequest request)
        {
            try
            {
                // Serialize the request using Jackson
                String jsonPayload = objectMapper.writeValueAsString(request);

                HttpURLConnection connection = getHttpURLConnection(jsonPayload);
                int code = connection.getResponseCode();

                if (code != HttpURLConnection.HTTP_OK)
                {
                    String errorResponse;
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8)))
                    {
                        errorResponse = br.lines().collect(Collectors.joining());
                    }
                    String message = "HTTP error code: " + code + " Response: " + errorResponse;
                    logger.error(message);
                    return createErrorResponse(message);
                }

                String apiResponse;
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
                {
                    apiResponse = br.lines().collect(Collectors.joining());
                }
                return new ChatResponse(apiResponse);

            } catch (IOException e)
            {
                logger.error("I/O error during API request", e);
                return createErrorResponse("I/O error during API request: " + e.getMessage());
            } catch (Exception e)
            {
                logger.error("Unexpected error during API request", e);
                return createErrorResponse("Unexpected error during API request: " + e.getMessage());
            }
        }

        private HttpURLConnection getHttpURLConnection(String jsonPayload) throws IOException
        {
            URL endpoint = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36");

            connection.setRequestProperty("Content-Type", "application/json");

            if (apiKey != null && !apiKey.isEmpty())
            {
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            }

            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream())
            {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            return connection;
        }


        private ChatResponse createErrorResponse(String errorMessage)
        {
            try
            {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage(errorMessage);
                ErrorWrapper errorWrapper = new ErrorWrapper(errorResponse);
                String errorJson = objectMapper.writeValueAsString(errorWrapper);
                return new ChatResponse(errorJson);
            } catch (Exception e)
            {
                throw new RuntimeException("Error serializing error response", e);
            }
        }
    }
}
