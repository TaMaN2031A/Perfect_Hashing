import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Dictionary {
    Perfect_Hashing_Interface hash;
    Factory factory = new Factory();

    long start,end;

    public Dictionary(String type, int size){
        hash = factory.getPerfectHASHING(type, size);
    }


    void ends() throws IOException {
        System.out.println("\033[0;31mExecution Times have been writen in files!\033[0m");
        System.out.println("\033[0;32m\nThanks for Using Our Dictionary\033[0m");
       // hash.ends();
    }

    void BatchInsert(String route) throws IOException, InterruptedException {
        int count=0, fileSize=0;
        start=System.nanoTime();
        Path path = Paths.get(route);
        List<String> lines = Files.readAllLines(path);
        fileSize = lines.size();
     //   count = hash.batchInsert((ArrayList<String>) lines);
        int[] state = new int[3];
        for (String line : lines) {
            state[insert(line)]++;
            System.out.println("no. Elements is now " + hash.getElementsOfTable());
        }
        end=System.nanoTime();
        System.out.println("\nBatch insertion Done Successfully!");
        System.out.println(state[1]+" new keys inserted without hashing!");
        System.out.println(state[0]+" were already their");
        System.out.println(state[2]+" new keys inserted with rehashing their inner table!");

        //System.out.println((fileSize-count)+" keys already exist in the Dictionary!");
        System.out.println("\nTime of insertion is : "+(end-start)+" ns");

    }

    void search(String a) {
        boolean res = hash.search(a);
        System.out.println("Search for:" + a + "'s result is: " + res);
    }
    int insert(String a) throws IOException, InterruptedException {
        int x = hash.insert(a);
        if(x == 0){
            System.out.println("Already found it");
        } else if(x == 1){
            System.out.println("Added successfully");
        }else{
            System.out.println("Added, but we rehashed the code");
        }
        return x;
    }

    void BatchDelete(String route) throws IOException, InterruptedException {
        start=System.nanoTime();
        Path path = Paths.get(route);
        List<String> lines = Files.readAllLines(path);
        int deleted = 0, notDeleted = 0;
        for (String line : lines) {
            if (delete(line))
                deleted++;
            else
                notDeleted++;
        }
        end=System.nanoTime();
        System.out.println("\nBatch delete Done Successfully!");
        System.out.println(deleted+" Successfully deleted Items!");
        System.out.println(notDeleted+" Doesn't exist");
        System.out.println("\nTime of delete is : "+(end-start)+" ns");

    }
    boolean delete(String a) throws IOException {
        boolean x = hash.delete(a);
        if(x){
            System.out.println("Successfully Deleted");
        }else{
            System.out.println("Doesn't exist");
        }
        return x;
    }

    int getSize(){
        return hash.getSize();
    }

}
