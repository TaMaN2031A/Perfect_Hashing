import java.io.IOException;
import java.util.Scanner;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.StringTokenizer;
public class CLI {
    Dictionary dictionary = null;
    String type = "n/a";
    int size = 0;

    void Welcome() {
        System.out.println("\033[0;31mWelcome to Our Dictionary\n\033[0m");
    }


    void options() {
        System.out.println("\033[0;31mChoose an Option from the following:\033[0m");
        System.out.println("\033[0;34m1)Insert a new key.\n2)Delete an existing key.\n3)Search for a key.\n4)Batch insert.\n5)Batch Delete.\n6)size.\n7)Exit.\n\nEnter an Option:\033[0m");
    }


    void run() throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        
        while (!type.equals("N2") && !type.equals("N")) {
            try {
                System.out.println("\033[0;32mInsert \"N2\" for using N^2 Perfect Hashing, or \"N\" for N Perfect Hashing\033[0m");
                type = sc.nextLine();
                if (!type.equals("N2") && !type.equals("N")) {
                    System.out.println("PLease Enter a Valid Option!");
                }
                // System.out.print("\033\143");
               // Thread.sleep(200);
                // System.out.print("\033\143");
            } catch (Exception e) {

            }
        }

        while (size <= 0) {
            try {
                System.out.println("\033[0;32mInsert the value of N: \033");
                size = sc.nextInt();
                
                if (size < 0) {
                    System.out.println("PLease Enter a Valid Option!");
                } else {
                    dictionary = new Dictionary(type, size);
                }
                System.out.print("\033\143");
                // Thread.sleep(200);
                System.out.print("\033\143");
            } catch (Exception e) {

            }
        }

        System.out.println("\033[0;32m" + type + " is Selected!\033[0m");
        
        
        String[] in;
        sc.nextLine();
        while (true) {
                System.out.print("\033\143");
                this.options();
                in = new String[20];
                String buffer;
                buffer = sc.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(buffer, " ");
                in[0] = tokenizer.nextToken();
                if (in[0].equals("exit")) {
                    dictionary.ends();
                    break;
                }
                if (in[0].equals("size")) {
                    System.out.println("i am here");
                    System.out.println(dictionary.getSize());
                }
                
                if (tokenizer.hasMoreElements()) {
                    in[1] = tokenizer.nextToken();
                } else {
                    System.out.print("\033\143");
                    System.out.println("Enter a Valid Option!");
                 //   Thread.sleep(1500);
                    continue;
                }
                if (tokenizer.hasMoreElements())
                    in[2] = tokenizer.nextToken();
                if (tokenizer.hasMoreElements()) {
                    System.out.print("\033\143");
                    System.out.println("Too many Arguments!");
                //    Thread.sleep(1500);
                    continue;
                }

                if (in[0].equals("insert")) {
                     dictionary.insert(in[1]);
                } else if (in[0].equals("delete")) {
                      dictionary.delete(in[1]);
                } else if (in[0].equals("search")) {
                      dictionary.search(in[1]);
                } else if (in[0].equals("batch")) {
                    if (in[1].equals("insert"))
                        dictionary.BatchInsert(in[2]);
                    else if (in[1].equals("delete"))
                         dictionary.BatchDelete(in[2]);
                    else {
                        System.out.print("\033\143");
                        System.out.println("Please Enter a Valid Option!");
                        //Thread.sleep(1500);
                       // continue;
                    }
                } else {
                    System.out.print("\033\143");
                    System.out.println("Please Enter a Valid Option!");
                }
                Thread.sleep(2000);
//            }catch (Exception e){
//                System.out.print("\033\143");
//                System.out.println("Enter a Valid Option!");
//            }

        }

    }
}
