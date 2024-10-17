public class CustomException extends Exception
{
    private String name;

    public CustomException(String name)
    {
        super(name);
        this.name = name;
    }

    public String what()
    {
        return name;
    }
}
