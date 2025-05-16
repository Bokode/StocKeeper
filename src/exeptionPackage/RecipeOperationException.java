package exeptionPackage;

public class RecipeOperationException extends AppException
{
    public RecipeOperationException(String message)
    {
        super(message);
    }

    public RecipeOperationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
