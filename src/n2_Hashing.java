import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.*;

public class n2_Hashing implements Perfect_Hashing_Interface{


    String[] hashTable;

    ArrayList<String> beforeHashing = new ArrayList<>();
    private long p = 5000000021L; // A prime that's bigger than a, (x-y) where x and y are the two keys
    private long sizeOFTable;

    public long getElementsOFtable() {
        return elementsOFtable;
    }

    private long elementsOFtable = 0;

    private long a;
    private long b;

    public n2_Hashing(){
        Random random = new Random();
        a = abs(random.nextInt() + 5);
        b = abs(random.nextInt() + 5);
        sizeOFTable = 1;
        hashTable = new String[(int)sizeOFTable];
    }

    private int[] rehash(){
        Random random = new Random();
        a = abs(random.nextInt() + 5);
        b = abs(random.nextInt() + 5);
        sizeOFTable = elementsOFtable*elementsOFtable;
        hashTable = new String[(int)sizeOFTable];
        for(int i = 0; i < elementsOFtable; i++){
            hashTable[i] = null;
        }
        int[] returned = new int[2];
//        System.out.println("Number of elements here is: " + elementsOFtable);
//        System.out.println("Elements are");
//        for(String iter: beforeHashing){
//            System.out.print(iter + " ");
//        }
       // System.out.println();
        for(String iter: beforeHashing){
//                Thread.sleep(1000);
//            System.out.println("Value of string is "+ iter);
            int index = (int)(hashFunction(toKey(iter)));
//            System.out.println("toKey returned "+ toKey(iter));
//            System.out.println("toIndex returned "+ hashFunction(toKey(iter)));
//            System.out.println("Index is "+ index);
        //    System.out.println(index);
            if(hashTable[index] != null){
                if(hashTable[index].equals(iter)){
                    continue;
                }
                else{
                    System.out.println(iter + " and " + hashTable[index] + " has same key");
                    System.out.println((toKey(iter)) + " and " + (toKey(hashTable[index])));
                    System.out.println((a*toKey(iter)+b) + " and " +(a* (toKey(hashTable[index]))+b));
                    returned[0] = 0;
                    return returned;
                }
            }
            hashTable[index] = iter;
//            System.out.println("Successfuly inserted "+ iter);
            returned[1]++;
        }
        returned[0] = 1;
        return returned;
    }
    private long toKey(String key) {
        long hashVal = 0;
        for(int i = 0; i < key.length(); i++){
            hashVal = (hashVal << 4) + (key.charAt(i));
            long g = hashVal & 0xF0000000L;
            if (g != 0) hashVal ^= g >>> 24;
            hashVal &= ~g;
        }
        return hashVal;
    }
    private long hashFunction(long number){
        return (long)((floor(((double)a)*number/b))+ceil(((double) b)*number/a))%p%sizeOFTable;
    }
    @Override
    public boolean search(String value){
        int index = (int)hashFunction(toKey(value));
        return Objects.equals(hashTable[index], value);
    }

    @Override
    public int insert(String value) throws InterruptedException {
        if(elementsOFtable == sizeOFTable){
            elementsOFtable++;
            beforeHashing.add(value);
            int[] arr;
            while (true){
                beforeHashing.add(value);
                elementsOFtable++;
                arr = rehash();
                if(arr[0] == 1){
                    break;
                }
            }
            return 2;
        }else{
            int key = (int)hashFunction(toKey(value));
            if(hashTable[key] == null){
                beforeHashing.add(value);
                elementsOFtable++;
                hashTable[key] = value;
                return 1;
            }else{
                if(hashTable[key].equals(value)){
                    return 0;
                }else{
                    int[] arr;
                    while (true){
                        beforeHashing.add(value);
                        elementsOFtable++;
                        arr = rehash();
                        if(arr[0] == 1){
                            break;
                        }
                    }
                    return 2;
                }
            }
        }

    }

    @Override
    public boolean delete(String value) throws IOException { // True if deleted, false if not
        int index = (int)hashFunction(toKey(value));
        if(hashTable[index] == null)
        {
            return false;
        }else{
            if(hashTable[index].equals(value)){
                hashTable[index] = null;
                return true;
            }else{
                return false;
            }
        }
    }

    public int getSize(){
        return (int)sizeOFTable;
    }
}
