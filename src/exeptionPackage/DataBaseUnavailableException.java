package exeptionPackage;

public class DataBaseUnavailableException extends AppException
{
    public DataBaseUnavailableException(String message)
    {
        super(message);
    }

    public DataBaseUnavailableException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
