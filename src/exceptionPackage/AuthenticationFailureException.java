package exceptionPackage;

public class AuthenticationFailureException extends AppException
{
    public AuthenticationFailureException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

