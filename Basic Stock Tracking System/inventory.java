import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class inventory
{
    private LinkedList<ArrayList<device>> inventorylist;

    public inventory()
    {
        inventorylist = new LinkedList<>();
    }
    /**
     * 
     * finds the place of Item to be added 
     * if there is not any ArrayList suitable, creates a new one
     * 
     * O(n)
     * 
     * @param Item
     */
    public void addItem(device Item)
    {
        boolean flag = false;
        for(int i=0;i<inventorylist.size();++i)
        {
            if(inventorylist.get(i).size()>0)
            {
                if(inventorylist.get(i).get(0).getCategory().equals(Item.getCategory()))
                {
                    flag = true;
                    inventorylist.get(i).add(Item);
                }
            }
        }
        if(flag == false)
        {
            ArrayList<device> newCatList = new ArrayList<>();
            newCatList.add(Item);
            inventorylist.add(newCatList);
        }
    }
    /**
     * 
     * Removes the entered name if exist,
     * if there is not any product it throws exception
     * 
     * O(n^2)
     * 
     * @param Item_name
     * @throws CustomException
     */
    public void removeItem(String Item_name) throws CustomException
    {
        for(int i=0;i<inventorylist.size();++i)
        {
            for(int j=0;j<inventorylist.get(i).size();++j)
            {
                if(inventorylist.get(i).get(j).getName().equals(Item_name))
                {
                    inventorylist.get(i).remove(inventorylist.get(i).get(j));
                    if(inventorylist.get(i).size() == 0)
                    {
                        inventorylist.remove(i);
                    }
                    return;
                }
            }
        }
        throw new CustomException("Item_not_found");
    }
    /** 
     * 
     * Updates the selected product if not exist throws exception
     * if exists it changes the price and quantity with the entered values,
     * checks whether entered values are valid with the functions convert
     * 
     * to keep the current value enter blank
     * 
     * O(n^3)
     * 
     * @param Item_name
     * @throws CustomException
     */
    public void updateItem(String Item_name) throws CustomException
    {
        String input;
        double price;
        int quantity;
        Scanner scan = new Scanner(System.in);

        for(int i=0;i<inventorylist.size();++i)
        {
            for(int j=0;j<inventorylist.get(i).size();++j)
            {
                if(inventorylist.get(i).get(j).getName().equals(Item_name))
                {
                    System.out.printf(" Enter new price (leave blank to keep current price): "); input = scan.nextLine();
                    if(!input.isEmpty())
                    {
                        price = convert_price(input);
                        inventorylist.get(i).get(j).setPrice(price);
                    }
                    System.out.printf(" Enter new quantity (leave blank to keep current quantity):"); input = scan.nextLine();
                    if(!input.isEmpty())
                    {
                        quantity = convert_quantity(input);
                        inventorylist.get(i).get(j).setQuantity(quantity);
                    }
                    System.out.printf("%s details updated: Price - %.2f$, Quantity - %d\n",inventorylist.get(i).get(j).getName(),inventorylist.get(i).get(j).getPrice(),inventorylist.get(i).get(j).getQuantity());
                    return;
                }
            }
        }
        throw new CustomException("Item_not_found");
    }

    /**
     * prints all the elements in the inventory
     * 
     * O(n^2)
     * 
     */
    public void displayAll()
    {
        int no=1;
        System.out.println("Device List:");
        for(ArrayList<device> categorylist : inventorylist)
        {
            for(device items : categorylist)
            {
                System.out.printf("%d. %s",no++,items.toString());
            }
        }
    }

    /**
     * 
     * search through all of the linkedlist and arraylist to find the cheapest product and returns
     * 
     * O(n^2)
     * 
     * @return device
     * @throws CustomException
     */
    public device findCheapest() throws CustomException
    {
        if(inventorylist.size() < 1 || inventorylist.get(0).size() < 1)
        {
            throw new CustomException("EmptyArray");
        }
        device total_min,temp_min;
        temp_min = total_min = inventorylist.get(0).get(0);
        for(int i = 0;i<inventorylist.size();++i)
        {
            if(inventorylist.get(i).size() > 0)
            {
                temp_min = inventorylist.get(i).get(0);
                for(int j = 1;j<inventorylist.get(i).size();++j)
                {
                    if(temp_min.getPrice() > inventorylist.get(i).get(j).getPrice())
                    {
                        temp_min = inventorylist.get(i).get(j);
                    }
                }
                if(total_min.getPrice() > temp_min.getPrice())
                {
                    total_min = temp_min;
                }
            }
        }
        return total_min;
    }

    /**
     * 
     * creates a deep copy of the inventory and calls findCheapest method prints the cheapest and removes the printed Item
     * 
     * O(n^3)
     * 
     * @throws CustomException
     */
    public void sortByPrice() throws CustomException
    {
        if(inventorylist.size() < 1 || inventorylist.get(0).size() < 1)
        {
            throw new CustomException("EmptyArray");
        }
        int length = 0;
        inventory sortedINV = new inventory();
        for(int i = 0;i<inventorylist.size();++i)
        {
            for(int j = 0;j<inventorylist.get(i).size();++j)
            {
                sortedINV.addItem(inventorylist.get(i).get(j));
                length++;
            }
        }
        device temp;
        for(int i=0;i<length;++i)
        {
            temp = sortedINV.findCheapest();
            System.out.printf("%d  %s",i+1,temp.toString());
            sortedINV.removeItem(temp.getName());
        }
    }


    /**
     * 
     * returns the total value of the inventory
     * 
     * O(n^2)
     * 
     * @return double
     */
    public double totalValue()
    {
        double total=0;
        for(ArrayList<device> categorylist : inventorylist)
        {
            for(device items : categorylist)
            {
                total += items.getPrice() * items.getQuantity();
            }
        }
        return total;
    }

    /**
     * 
     * if entered ItemName doesnt exist in inventory throws exception
     * takes string input which has to be Add or Remove else throws exception
     * if entered input is Remove checks whether current stock - entered stock >=0 else throws exception
     * 
     * 
     * O(n^3)
     * 
     * @param Item_name
     * @throws CustomException
     */
    public void restocking(String Item_name) throws CustomException
    {
        Scanner scan = new Scanner(System.in);
        String input; int quantity;
        for(int i=0;i<inventorylist.size();++i)
        {
            for(int j=0;j<inventorylist.get(i).size();++j)
            {
                if(inventorylist.get(i).get(j).getName().equals(Item_name))
                {
                    System.out.printf("Do you want to add or remove stock? (Add/Remove): "); input = scan.nextLine();
                    if(input.isEmpty() || !(input.equals("Add") || (input.equals("Remove"))))
                    {
                        throw new CustomException("MissingInput");
                    }
                    if(input.equals("Add"))
                    {
                        System.out.printf("Enter the quantity to add: "); input = scan.nextLine();
                        if(input.isEmpty())
                        {
                            throw new CustomException("MissingInput");
                        }
                        quantity = convert_quantity(input);
                        inventorylist.get(i).get(j).setQuantity(quantity+inventorylist.get(i).get(j).getQuantity());
                        System.out.printf("%s restocked. New quantity: %d\n",inventorylist.get(i).get(j).getName(),inventorylist.get(i).get(j).getQuantity());
                    }
                    else
                    {
                        System.out.printf("Enter the quantity to remove: "); input = scan.nextLine();
                        if(input.isEmpty())
                        {
                            throw new CustomException("MissingInput");
                        }
                        quantity = convert_quantity(input);
                        if(inventorylist.get(i).get(j).getQuantity() - quantity < 0)
                        {
                            throw new CustomException("InvalidInput");
                        }
                        inventorylist.get(i).get(j).setQuantity(inventorylist.get(i).get(j).getQuantity()-quantity);
                        System.out.printf("%s stock reduced. New quantity: %d\n",inventorylist.get(i).get(j).getName(),inventorylist.get(i).get(j).getQuantity());
                    }
                    return;
                }
            }
        }
        throw new CustomException("Item_not_found");
    }
    
    /**
     * 
     * prints all the items in inventory and summary of it
     * 
     * O(n^2)
     * 
     */
    public void exportReport() throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter("report.txt");
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d'‘' MMMM yyyy");
        String formattedDate = currentDate.format(formatter);
        writer.println("Electronics Shop Inventory Report\nGenerated on: " + formattedDate);
        writer.printf("\n\n---------------------------------------\n|  No.  |  Category  |  Name             |  Price  |  Quantity  |\n---------------------------------------\n");
        int no=0;
        for(ArrayList<device> categorylist : inventorylist)
        {
            for(device item : categorylist)
            {
                writer.printf("| %d  | %-12s | %-18s | $%.2f  |  %-5s |\n",++no,item.getCategory(),item.getName(),item.getPrice(),item.getQuantity());
            }
        }
        writer.printf("---------------------------------------\n\n\nSummary:\n- Total Number of Devices: %d\n- Total Inventory Value: $%.2f\n\nEnd of Report\n\n",no,totalValue());
        writer.close();
    }

    /**
     *  takes inputs which needs for creating a device
     * 
     * checks whether inputs are valid or not
     * such as Category Name, same product name, missing inputs etc
     * 
     * 
     * O(n)
     * 
     * @return device
     * @throws CustomException
     */
    public device addItemINPUT() throws CustomException
    {
        device Item; String category,input,name;
        int quantity; double price;
        Scanner scan = new Scanner(System.in);
        System.out.printf("Enter category name: "); category = scan.nextLine();
        if(!(category.equals("Smart Phone") || category.equals("Headphones") || category.equals("Laptop") || category.equals("Smart Watch") || category.equals("TV")))
        {
            throw new CustomException("InvalidCategory");
        }
        System.out.printf("Enter device name: "); name = scan.nextLine();
        if(isExist(name))
        {
            throw new CustomException("SameProductName");
        }
        System.out.printf("Enter price: "); input = scan.nextLine();
        if(input.isEmpty() || input.equals("\n"))
        {
            throw new CustomException("MissingInput");
        }
        price = convert_price(input);
        System.out.printf("Enter quantity: "); input = scan.nextLine();
        if(input.isEmpty() || input.equals("\n"))
        {
            throw new CustomException("MissingInput");
        }
        quantity = convert_quantity(input);
        
        if(category.equals("Smart Phone"))
        {
            Item = new smartphone(name,price,quantity);
        }
        else if(category.equals("TV"))
        {
            Item = new tv(name,price,quantity);
        }
        else if(category.equals("Headphones"))
        {
            Item = new headphones(name,price,quantity);
        }
        else if(category.equals("Laptop"))
        {
            Item = new laptop(name,price,quantity);
        }
        else //Smart Watch
        {
            Item = new smartwatch(name,price,quantity);
        }
        return Item;
    }

    /**
     * 
     * takes String input which has to be a some sort of double
     * if not throws exception
     * 
     * 
     * O(n)
     * 
     * @param input
     * @return double
     */
    public static double convert_price(String input) throws CustomException
    {
        String temp = new String();
        for(int i=0;i<input.length();++i)
        {
            if(input.charAt(i) == '.' || (input.charAt(i) >= '0' && input.charAt(i)<= '9'))
            {
                temp += input.charAt(i);
            }
        }
        if(temp.length() < 1) throw new CustomException("MissingInput");
        return Double.parseDouble(temp);
    }

    /**
     * 
     * takes String input which has to be a some sort of int
     * if not throws exception
     * 
     * O(n)
     * 
     * @param input
     * @return int
     */
    public static int convert_quantity(String input) throws CustomException
    {
        String temp = new String();
        for(int i=0;i<input.length();++i)
        {
            if(input.charAt(i) >= '0' && input.charAt(i)<= '9')
            {
                temp += input.charAt(i);
            }
        }
        if(temp.length() < 1) throw new CustomException("MissingInput");
        return Integer.parseInt(temp);
    }

    /**
     * 
     * checks whether Item's name already exist in inventory
     * 
     * O(n^2)
     * 
     * @param Item
     * @return boolean
     */
    public boolean isExist(String Item)
    {
        for(ArrayList<device> categorylist : inventorylist)
        {
            for(device items : categorylist)
            {
                if(items.getName().equals(Item))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
