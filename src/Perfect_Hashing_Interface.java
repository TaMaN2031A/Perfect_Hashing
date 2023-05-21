import java.io.IOException;
import java.util.ArrayList;

public interface Perfect_Hashing_Interface {
    int insert(String node) throws IOException, InterruptedException;
    boolean delete(String node) throws IOException;
    boolean search(String node);
    int getSize();
    long getElementsOfTable();
}
