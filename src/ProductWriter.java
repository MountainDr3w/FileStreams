import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;
/*
a.	ID (a String as before in Person)
b.	Name (a String)
c.	Description (a String a short sentence)
d.	Cost (This is currency so it will be a Java double)

 */

public class ProductWriter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //Declare Vars
        boolean done = false;
        String ID= "";
        String name = "";
        String desc = "";
        double cost;

        ArrayList<Product> productList = new ArrayList<>();

        do {
            ID = SafeInput.getNonZeroLenString(in, "Please enter the item ID");
            name = SafeInput.getNonZeroLenString(in, "Please enter the item name");
            desc = SafeInput.getNonZeroLenString(in, "Please enter the description");
            cost = SafeInput.getDouble(in, "Please enter the cost of the item");

            Product rec = new Product(name, desc, ID, cost);


            productList.add(rec);

            done = SafeInput.getYNConfirm(in, "Are you done?");
        } while (!done);

        //COPIED FROM NIOWriteTextFile
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\ProductTestData.txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(Product product : productList)
            {
                writer.write(product.toCSV());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // rec. length() is how many chars to write (all)
                writer.newLine();  // adds the new line

            }
            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
