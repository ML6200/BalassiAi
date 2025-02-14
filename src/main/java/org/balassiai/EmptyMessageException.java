package org.balassiai;

public class EmptyMessageException extends Exception
{
    public EmptyMessageException()
    {
        super("No message context given!");
    }
}
