package exceptionPackage;

public class WrongInputException extends AppException {
    public WrongInputException(String message)
    {
        super(message);
    }

    public WrongInputException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
