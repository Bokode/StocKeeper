package exceptionPackage;

public class AlreadyExistException extends AppException
{
    public AlreadyExistException(String message)
    {
        super(message);
    }
    public AlreadyExistException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
