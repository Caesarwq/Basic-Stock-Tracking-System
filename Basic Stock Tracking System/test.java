import java.io.FileNotFoundException;
import java.util.Scanner;

public class test
{
    public static void main(String args[])
    {
        String choice;
        Scanner scan = new Scanner(System.in);
        inventory Inventory = new inventory();
        do
        {
            System.out.printf("Please select an option:\n");
            System.out.printf("1. Add a new device\n");
            System.out.printf("2. Remove a device\n");
            System.out.printf("3. Update device details\n");
            System.out.printf("4. List all devices\n");
            System.out.printf("5. Find the cheapest device\n");
            System.out.printf("6. Sort devices by price\n");
            System.out.printf("7. Calculate total inventory value\n");
            System.out.printf("8. Restock a device\n");
            System.out.printf("9. Export inventory report\n");
            System.out.printf("0. Exit\n");

            choice = scan.nextLine();
            try
            {
                if(choice.equals("1"))
                {
                    device newItem = Inventory.addItemINPUT();
                    Inventory.addItem(newItem);
                    System.out.printf("%s, %s, %.2f$, %d amount added...\n",newItem.getCategory(),newItem.getName(),newItem.getPrice(),newItem.getQuantity());
                }
                else if(choice.equals("2"))
                {
                    System.out.printf("Enter the name of the device to be removed: ");
                    Inventory.removeItem(scan.nextLine());
                }
                else if(choice.equals("3"))
                {
                    System.out.printf("Enter the name of the device to be updated: ");
                    Inventory.updateItem(scan.nextLine());
                }
                else if(choice.equals("4"))
                {
                    Inventory.displayAll();
                }
                else if(choice.equals("5"))
                {
                    System.out.printf("The cheapest devive is:\n%s",Inventory.findCheapest().toString());
                }
                else if(choice.equals("6"))
                {
                    Inventory.sortByPrice();
                }
                else if(choice.equals("7"))
                {
                    System.out.printf("Total inventory value: $%.2f\n",Inventory.totalValue());
                }
                else if(choice.equals("8"))
                {
                    System.out.printf(" Enter the name of the device to restock: ");
                    Inventory.restocking(scan.nextLine());
                }
                else if(choice.equals("9"))
                {
                    Inventory.exportReport();
                }
                else if(choice.equals("0")) {break;}
                else
                {
                    System.out.printf("Wrong Selection! Try again\n");
                }
            }
            catch(CustomException e)
            {
                if(e.what().equals("InvalidCategory"))
                {
                    System.out.printf("Invalid Category name!\n");
                }
                else if(e.what().equals("MissingInput"))
                {
                    System.out.printf("Missing information!\n");
                }
                else if(e.what().equals("Item_not_found"))
                {
                    System.out.printf("Item not found!\n");
                }
                else if(e.what().equals("EmptyArray"))
                {
                    System.out.printf("Inventory is empty!\n");
                }
                else if(e.what().equals("InvalidInput"))
                {
                    System.out.printf("Invalid input!\n");
                }
                else if(e.what().equals("SameProductName"))
                {
                    System.out.printf("Item not added(Name already exist with another product)!\n");
                }
            }
            catch(FileNotFoundException e)
            {
                System.out.println("FileNotFound!");
            }
            
        }while(true);
        scan.close();
    }
}
