package org.balassiai.core;

import java.util.List;

public interface ChatRequest
{
    String getModel();

    List<Message> getMessages();

    float getTemperature();
}
