package Ajutor;

import DAO.ProdusDAO;
import Tables.Produs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriteReadFile {

    public void writeFile(String message, String path) throws IOException {
        File file = new File(path);
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(message+"\n");

        br.close();
        fr.close();
    }


    public void createFile(String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String readFile(String mail, String path) {
        String[] vector = new String[3];
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);


            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains(mail)) {
                    vector = data.split(" ");
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return vector[1];
    }

    public String[] read(String path) {
        String[] strings = new String[3];
        int i = 0;
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            //String[] vector = new String[3];

            i = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data != null) {
                    strings[i] = data;
                }
                i++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return strings;
    }

    public void addRowFromFile(String file, DefaultTableModel model) throws IOException {
        FileReader fileReader = new FileReader(file);
        String line;
        String[] fields=new String[3];
        // Always wrap FileReader in BufferedReader
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            // Split the line into fields
            fields= line.split(" ");
            model.addRow(fields);
        }
        // Close the file
        bufferedReader.close();
    }

    public List<String> readFileNotifications(String path) {
        List<String> strings = new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            String[] vector = new String[3];

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                    vector = data.split(" ");
                    if(vector[0] != null) {
                        strings.add(vector[0]);
                    }
                    System.out.println("from file: " + vector[0]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return strings;
    }

    public void deleteReader(String id, String fileName) throws IOException {

            File inFile = new File(fileName);
            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            while ((line = br.readLine()) != null) {
                if (!line.trim().contains(id)) {
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

    }

    public static void main(String[] args) {
        WriteReadFile writeReadFile = new WriteReadFile();
        List<String> strings = writeReadFile.readFileNotifications("notificareStocGol.txt");
        for (String s: strings){
            System.out.println(s);
            ProdusDAO produsDAO = new ProdusDAO();
            String id = s;
            Produs p = produsDAO.getProdus(id);
            System.out.println(p.getIdProdus());
        }
    }
}