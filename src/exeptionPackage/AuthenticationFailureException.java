package exeptionPackage;

public class AuthenticationFailureException extends AppException
{
    public AuthenticationFailureException(String message)
    {
        super(message);
    }

    public AuthenticationFailureException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

