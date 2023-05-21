import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.*;

public class n2_Hashing implements Perfect_Hashing_Interface{
    String[] hashTable;
    private long p = 5000000021L; // A prime that's bigger than a, (x-y) where x and y are the two keys
    private final int sizeOfTable;
    long numberofelements=0;
    private long a;
    private long b;

public n2_Hashing(int size) throws InterruptedException {
        sizeOfTable = (int) pow(size,2);
        System.out.println("size is "+sizeOfTable);
        Random random = new Random();
        a = abs(random.nextInt());
        b = abs(random.nextInt());
        hashTable = new String[sizeOfTable];
        Thread.sleep(4000);
    }
private boolean rehash2(String value){
    System.out.println("i am rehashing");
    Random random = new Random();
    a = abs(random.nextInt());
    b = abs(random.nextInt());
    String[] prevtable = hashTable; 
    hashTable = new String[sizeOfTable];
    int key;
    for(int i=0;i<sizeOfTable;i++){
        if(prevtable[i]!=null){
            key=hashFunction(toKey(prevtable[i]));
            if(hashTable[key]!=null){
                hashTable=prevtable;
                return false;
            }else{
                hashTable[key]=prevtable[i];
            }
        }
    }
    key=hashFunction(toKey(value));
    
    if(hashTable[key]!=null){
        hashTable=prevtable;
        return false;
    }else{
        hashTable[key]=value;
    }
    return true;

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
private int hashFunction(long number) {
        return (int)((a*number+b)%p%sizeOfTable);
    }
public boolean search(String value){
        int index = hashFunction(toKey(value));
        return Objects.equals(hashTable[index], value);
}
public int insert(String value) {    
    if (numberofelements == sizeOfTable) {
            int index=hashFunction(toKey(value));
            System.out.println("index is "+index);
            if(!Objects.equals(hashTable[index],value)){
                return 2;
            }
            return 0;
    }else {
            int key = hashFunction(toKey(value));
            if (hashTable[key] == null) {
                numberofelements++;
                hashTable[key] = value;
                return 1;
            } else {
                if (hashTable[key].equals(value)) {
                    return 0;
                } else {
                    boolean check=false;
                    int count=0;
                    while (!check) {
                        check = rehash2(value);
                        count++;   // number of rehashings
                    }
                    numberofelements++;
                    System.out.println("We Rehashed "+count+" Times");
                    return 3;
                }
            }
        }
}
public boolean delete(String value) { // True if deleted, false if not
        int index = hashFunction(toKey(value));
        if(hashTable[index] == null)
        {
            return false;
        }else{
            if(hashTable[index].equals(value)){
                hashTable[index] = null;
                numberofelements--;
                return true;
            }else{
                return false;
            }
        }
}
public int getSize() {
    return sizeOfTable;
    }
public long getElementsOfTable(){
    return numberofelements;

}


}
