package dbo;

import dto.Vehicle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class VehicleFileManagerment {

    final String fileName = "src\\output\\vehicle.dat";

    public ArrayList readFile() throws Exception {
        ArrayList result = null;
        try {
            String thisLine; 
            BufferedReader myInput;
            File f = new File(fileName);
            String fullPath = f.getAbsolutePath();
            FileInputStream file = new FileInputStream(fullPath);
            myInput = new BufferedReader(new InputStreamReader(file));
            while ((thisLine = myInput.readLine()) != null) {
                if (!thisLine.trim().isEmpty()) {
                    String[] split = thisLine.split(",");
                    // No, id , name ,color ,price ,brand, type, productYear
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                   
                    for (String string : split) {
                        string = string.trim();
                    }
                    
                    Vehicle vehicle = null;
                    String id = split[1];
                    String name = split[2];
                    String color = split[3];
                    int price = Integer.parseInt(split[4].trim());
                    String brand = split[5];
                    String type = split[6];
                    int productYear = Integer.parseInt(split[7].trim());
                    vehicle = new Vehicle(id, name, color, price, brand, type, productYear);
                    result.add(vehicle);
                }
            }
            myInput.close();
        } catch (IOException | NumberFormatException ex) {
            throw ex;
        }
        return result;
    }

    public boolean writeFile(ArrayList arr) throws Exception {
        File f;
        FileOutputStream file;
        BufferedWriter myOutput;
        try {
            f = new File(fileName);

            String fullPath = f.getAbsolutePath();
            file = new FileOutputStream(fullPath);
            myOutput = new BufferedWriter(new OutputStreamWriter(file));
            for (int i = 0; i < arr.size(); i++) {
                if (i > 0) {
                    myOutput.newLine();
                }
                myOutput.write((i + 1) + "," + arr.get(i).toString());
            }
            myOutput.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public boolean checkFile() {
        boolean result;
        File f;
        try {
            f = new File(fileName);
            result = f.exists();
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

}
