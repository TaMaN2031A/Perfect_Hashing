import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class n_Hashing implements Perfect_Hashing_Interface {
    private class secondLevel{
        private String[] internalValues;

        private ArrayList<String> beforeHashing= new ArrayList();
        long no_elements;
        long a;
        long b;
        long p = 5000000021L;
        long m;

        private long hashFunction(long number){
            return (a*number+b)%p%m;
        }


        long toKey(String s) {
            long hashVal = 0;
            for(int i = 0; i < s.length(); i++){
                hashVal = (hashVal << 16) + (s.charAt(i)) +
                        (hashVal << 6) - hashVal;
            }
            hashVal = hashVal<0? hashVal*-1 : hashVal;
            return hashVal%Integer.MAX_VALUE;
        }


        public void add(String n){
            no_elements++;
            beforeHashing.add(n);
        }

        public int insert(String value){
            int index = (int)(hashFunction(toKey(value)));
            if(internalValues[index] == null){
                add(value);
                internalValues[index] = value;
                return 1;
            }else{
                if(internalValues[index].equals(value)){
                    return 0;
                }else{
                    add(value);
                    return 2;
                }
            }
        }

        public int[] hash() throws InterruptedException {
            Random random = new Random();
            a = abs(random.nextInt() + 5);
            b = abs(random.nextInt() + 5);
            m = no_elements*no_elements;
            internalValues = new String[((int)(m))];
            for(int i = 0; i < m; i++){
                internalValues[i] = null;
            }
            int[] returned = new int[2];
            System.out.println("Number of elements here is: " + no_elements);
            System.out.println("Elements are");
            for(String iter: beforeHashing){
                System.out.print(iter + " ");
            }
            System.out.println();
            for(String iter: beforeHashing){
//                Thread.sleep(1000);
                System.out.println("Value of string is "+ iter);
                int index = (int)(hashFunction(toKey(iter)));
                System.out.println("toKey returned "+ toKey(iter));
                System.out.println("toIndex returned "+ hashFunction(toKey(iter)));
                System.out.println("Index is "+ index);
                System.out.println(index);
                if(internalValues[index] != null){
                    if(internalValues[index].equals(iter)){
                        continue;
                    }
                    else{
                        returned[0] = 0;
                        return returned;
                    }
                }
                internalValues[index] = iter;
                System.out.println("Successfuly inserted "+ iter);
                returned[1]++;
            }
            returned[0] = 1;
            return returned;
        }

        public boolean search(String wanted) {
            int index = (int)hashFunction(toKey(wanted));
            return Objects.equals(internalValues[index], wanted);
        }

        public boolean delete(String value) {
            int index = (int)hashFunction(toKey(value));
            if(internalValues[index] == null)
            {
                return false;
            }else{
                if(internalValues[index].equals(value)){
                    internalValues[index] = null;
                    return true;
                }else{
                    return false;
                }
            }
        }

    }
    private secondLevel[] mainHashing = new secondLevel[45003];
    private long p = 5000000021L; // A prime that's bigger than a, (x-y) where x and y are the two keys
    private long m = 45003L;
    private long a;
    private long b;


    public n_Hashing(){
        Random random = new Random();
        a = abs(random.nextInt() + 5);
        b = abs(random.nextInt() + 5);
        for(int i = 0; i < 45003; i++){
            mainHashing[i] = new secondLevel();
        }
        // Print the generated value
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
        return (a*number+b)%p%m;
    }
    public long getElementsOFtable() {
        return 0;
    }

//    private void first_level_hasher(ArrayList<String> input){
//        for(String number: input){
//            long key = toKey(number);
//            System.out.println(key);
//            System.out.println(hashFunction(key));
//            long last = hashFunction(key);
//            mainHashing[(int)last].add(number);
//        }
//    }
//    private int second_level_hasher() throws InterruptedException {
//        int c = 0;
//        for(int i = 0; i < m; i++){
//            if(mainHashing[i].no_elements != 0){
//                while (true){
//                    int[] arr = mainHashing[i].hash();
//                    if(arr[0] == 1){
//                        c+=arr[1];
//                        break;
//                    }
//                }
//            }
//            System.out.println("Number of elements = " +c);
//        }
//
//        return c;
//    }
//    public int batchInsert(ArrayList<String> input) throws InterruptedException {
//        first_level_hasher(input);
//        int c = second_level_hasher();
//        return c;
//    }

    @Override
    public boolean search(String value){
        int key = (int)hashFunction(toKey(value));
        return mainHashing[key].search(value);
    }

    @Override
    public int insert(String value) throws InterruptedException {

        int key = (int)hashFunction(toKey(value));
        if(mainHashing[key].no_elements == 0){
            mainHashing[key].add(value);
            int[] arr;
            while (true){
                arr = mainHashing[key].hash();
                if(arr[0] == 1){
                    break;
                }
            }
            return 2;
        }
        int state = mainHashing[key].insert(value);
        if(state == 0){// Found
            return 0;
        }
        else if(state == 1){ // New added
            return 1;
        }
        else{
            int[] arr;
            while (true){
                arr = mainHashing[key].hash();
                if(arr[0] == 1){
                    break;
                }
            }
            //v = 2;
            return 2;
        }
    }

    @Override
    public boolean delete(String value) throws IOException{ // True if deleted, false if not
        int key = (int)hashFunction(toKey(value));
        return mainHashing[key].delete(value);
    }

    @Override
    public int getSize(){
        int c = 0;
        for(int i = 0; i < mainHashing.length; i++){
            c+=mainHashing[i].m;
        }
        return c;
    }

}
