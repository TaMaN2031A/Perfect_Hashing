//import AVL.AvlTree;
//import Interfaces.Tree_Interface;
//import RB.RbTree;

public class Factory <T>{
    public Perfect_Hashing_Interface getPerfectHASHING(String treeType, int size) throws InterruptedException
    {
        if(treeType.equals("N"))
            return new n_Hashing(size);
        else if(treeType.equals("N2"))
            return new n2_Hashing(size);
        else
            return null;
    }
}