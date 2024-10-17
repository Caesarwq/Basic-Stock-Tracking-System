public class laptop implements device
{
    private String name;
    private String category; 
    private double price;
    private int quantity;

    public laptop(String name,double price,int quantity)
    {
        category = "Laptop";
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * returns the category of the object
     * 
     * @return String
     */
    public String getCategory()
    {
        return category;
    }

    /**
     * returns the name of the object
     * 
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * sets the name 
     * 
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * returns the price of the object
     * 
     * @return double
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * sets the price
     * 
     * @param price
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * returns the quantity of the object
     * 
     * @return int
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * sets the quantity
     * 
     * @param quantity
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /**
     * 
     * 
     * @override toString method
     */
    public String toString()
    {
        return "Category: " + getCategory() + ", Name: "+ getName() + ", Price: " + getPrice() + ", Quantity: " + getQuantity() + "\n";
    }
}
