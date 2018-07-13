
import java.util.Objects;
import java.util.Scanner;

public class main
{
    public static void main(String [] args)
    {
        Scanner input =new Scanner(System.in);
       int choice = 0;

        carsDB d = new carsDB();

        do {
            cout("\n\nWelcome!\n" +
                    "This is what you're working with so far:");
            d.read();

            cout ("\nEnter '1' to create a vehicle entry\n" +
                    "Enter '2' to read out vehicle entries\n" +
                    "Enter '3' to update an existing entry\n" +
                    "Enter '4' to remove an existing vehicle entry\n" +
                    "Enter '5' to drop an entire collection\n" +
                    "Enter '-1' to exit the program.\n");
            choice = input.nextInt();


            switch (choice)
            {
                case 1: // Fulfils the 'C' part of CRUD
                {
                    cout("Enter the make, model and year of the car you want to add.");
                    String custom_id, make, model;
                    int year;
                    cout("custom_id: ");
                    custom_id = input.next();
                    cout("make: ");
                    make = input.next(); //read in the make of the car
                    cout("model: ");
                    model = input.next(); //read in the model of the car
                    cout("year: ");
                    year = input.nextInt();
                    d.create(custom_id,make, model, year);

                    break;
                }
                case 2: // Fulfils the 'R' part of CRUD
                {
                    String C2Choice;
                    cout("Would you like to read the entire Database?");
                    cout("or just find a specific entry?('A' for Database and 'B' for specific entry)");
                    C2Choice = input.next(); //read char
                    C2Choice = C2Choice.toUpperCase(); //make it uppercase

                    if (Objects.equals(C2Choice, "A"))// read entire DB
                    {
                        d.read();
                    } else if (Objects.equals(C2Choice, "B")) {
                        cout("Enter id name: ");
                        String custom_id = input.next();
                        d.find(custom_id);
                    }
                    else
                    {
                        cout("invalid entry..");
                    }
                    break;
                }
                case 3: // Fulfils the 'U' part of CRUD
                {
                            cout("Type '1' to update make\n" +
                                    "Type '2' to update model\n" +
                                    "Type '3' to update the year");
                            int C3Choice = input.nextInt();

                            if (C3Choice <= 3 && C3Choice >= 1)
                                d.update(C3Choice);
                            else
                                cout("Incorrect input");
                    break;
                }
                case 4: {
                    cout("Enter 'model' you want to delete");
                    String modelValue = input.next();

                    d.deleteOne(modelValue);
                    break;
                }
                case 5: {
                    d.deleteAll(); // if without any arg, drop entire collection
                    break;
                }
                default: {
                    cout("exiting");
                    break;
                }
            }
        }while (choice != -1);

    }

    //an easier way to Sysout for this project. Esp since all my values are strings or ints
    private static void cout (String s)
    {
        System.out.println(s);
    }
    private static void cout (int x)
    {
        System.out.println(x);
    }
}
