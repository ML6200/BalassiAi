package balassi_ai;

public class EmptyMessageException extends Exception
{
    public EmptyMessageException()
    {
        super("No message context given!");
    }
}
