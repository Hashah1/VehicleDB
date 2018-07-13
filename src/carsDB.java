/**
 * Created by mianhashimshah on 2/6/18.
 *
 This class is designed to boot up mongoDB server, and use an instance of the class to manipulate the DB
 i.e Add, Remove, and update and delete the database(CRUD)*/
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Scanner;
import java.util.logging.*;

public class carsDB
{
    private MongoClient mongoClient; //connect to the host
    private MongoCollection vehicleRecordsCollection;

    //constructor
    carsDB()
    {
        //get rid of logs
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        mongoClient = new MongoClient( "LocalHost" , 27017 ); //connect to server
        System.out.println("Successfully connected to the server!\n");

        // connect to database, hardcoded for now
        MongoDatabase myDB = mongoClient.getDatabase("Automotive");
        //get the collection from DB(Hard coded for now)
         vehicleRecordsCollection = myDB.getCollection("vehicleRecords");
    }

    public void create(String custom_id, String make, String model, int year)
    {
        //INSERTING DOCUMENTS//
        //assumption for custom_id, will act as the ID for each entry, ignoring the automatically alloc. id by mongodb // can never be the same with other documents
        Document insertFirstDoc = new Document("\n\t\t\tcustom_id", custom_id).append("\n\t\t\tmake", make).append("\n\t\t\tmodel", model).append("\n\t\t\tyear", year);
        vehicleRecordsCollection.insertOne(insertFirstDoc); //insert this Document
        System.out.println("Successfully inserted an entry!\n");
    }

    //displays all cars
    public void read()
    {
        if (vehicleRecordsCollection.count() == 0) //check collection size
        {
            System.out.println("collection is empty..");
            return;
        }
        //        //FIND AND DISPLAY ALL CARS//
        System.out.println("Now displaying all documents in collection...");

        // getting the all documents from collection
        FindIterable<Document> documents = vehicleRecordsCollection.find();
        // preparing for an iteration
        MongoCursor<Document> cursor = documents.iterator();

        // iterating the documents
        while(cursor.hasNext())
        {
            System.out.println(cursor.next());
        }
    }
    //finds a car and displays it
    public boolean find(String custom_id)
    {
        System.out.println("Searching in the database...");

        //FINDING A SINGLE CAR and displaying it// 
        Document findThisCar = (Document) vehicleRecordsCollection.find(new Document("custom_id", custom_id)).first();

        if (findThisCar != null) //if we find the car //
        {
             System.out.println("Found it:\t" + findThisCar ); //then we print it //
            return true;
        }
        else
        {
            System.out.println("Could not find car");
            return false;
        }
    }

    public void update(int choice)
    {
        Scanner input =new Scanner(System.in);

        Bson updateThisValue = null;
        String x;

        //UPDATE//
        System.out.println("Enter 'custom_id' of the car you want to update: ");
        String custom_id = input.nextLine();

        Document updateThisUser = (Document) vehicleRecordsCollection.find(new Document( "custom_id", custom_id)).first();//find the certain user we need to update

        if (updateThisUser != null) // if user is found
        {
            if (choice  == 1)
            {
                //update make
                System.out.println("What do you want to update it to? ");
                x = input.nextLine();

                 updateThisValue = new Document("make", x); //this will replace the value on our target doc
            }
            else if (choice == 2)
            {
                //update model
                System.out.println("What do you want to update it to? ");
                String modelVal = input.nextLine();

                 updateThisValue = new Document("model", modelVal); //this will replace the value on our target doc
            }
            else if (choice == 3)
            {
                //update year
                System.out.println("What do you want to update it to? ");
                String yearValue = input.nextLine();

                 updateThisValue = new Document("year", yearValue); //this will replace the value on our target doc
            }

            Bson updateOperation = new Document("$set", updateThisValue); //set the existing user with the update operation
            vehicleRecordsCollection.updateOne(updateThisUser, updateOperation); //successfully update that user with its updated value
            System.out.println("User updated!");//user not found
        }else
        {
            System.out.println("User not found!");//user not found
        }
    }

    public void deleteAll()
    {
        vehicleRecordsCollection.drop();
        System.out.println("Successfully removed entries!\n");
    }

    public void deleteOne(String value)
    {
        if (find(value) == true)
        {
            vehicleRecordsCollection.deleteOne(new Document("custom_id", value)); // delete a specified value 
            System.out.println("deleted one doc\n");
        }
        else
            System.out.println("Entry not found");
    }
}
