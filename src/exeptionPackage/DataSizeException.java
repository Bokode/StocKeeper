package exeptionPackage;

public class DataSizeException extends AppException
{
    public DataSizeException(String message)
    {
        super(message);
    }
    public DataSizeException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
