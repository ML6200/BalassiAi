package balassi_ai.core;

import java.util.List;

public interface ChatRequest
{
    String getModel();

    List<Message> getMessages();
}
