public class MergeFindSet {

    private int[] set;
    private int numberOfSets;


    public MergeFindSet(int size){
        numberOfSets = size;
        set = new int[size];
        for(int i = 0; i < set.length; i++){
            set[i] = -1;
        }
    }

    /**
     * Returns the index to the root of the searched elemet <tt>x</tt> Set.
     * @param x The element, which parent you are seeking.
     * @return The index to the root of the Set containing <tt>x</tt>.
     */
    public int find(int x){
        if(set[x] < 0){//found root
            return x;
        }else{ //did not found root
            set[x] = find(set[x]);
            return set[x];
        }
    }

    /**
     * Method for merging two Sets. <br/>
     * Works even if the supplied roots are not the actual roots.
     * @param root1 Index to the root of the first Set.
     * @param root2 Index to the root of the first Set.
     * @return True is successful and False if the two elements belong to the same Set.
     */
    public boolean merge(int root1, int root2){
        //ensures that the supplied roots are actually roots
        root1 = find(root1);
        root2 = find(root2);

        if(root1 == root2){
            return false;
        }

        //om root 2 är större än 1 swappa dem, kom ihåg att det är negativa tal
        if(set[root2] < set[root1]){
            int tmp = root1;
            root1 = root2;
            root2 = tmp;
        }

        //Save the size of the second set
        int sizeR2 = set[root2];
        //Add the second set to the first set
        set[root2] = root1;

        //Increase the first set by the size of the second set.
        set[root1] += sizeR2;

        numberOfSets--;

        return true;
    }

    /**
     * Getter for the number of different sets
     * @return the number of unique sets
     */
    public int getnumbOfSets(){
        return numberOfSets;
    }

    /**
     * Used for testing that the implementation of find() and merge() works.
     * @param args Will not use arguments
     */
    public static void main(String[] args) {

        int length = 7;

        MergeFindSet mfs = new MergeFindSet(length);

        for (int i = 0; i < length; i++) {
            System.out.println(mfs.find(i) == i);
        }

        //Remeber: set does not contain duplicates
        mfs.set = new int[] { -4, 0, 0, 1, -2, 4, -1 };

        //Check that all nodes has the right root.
        System.out.println(mfs.find(0) == 0);
        System.out.println(mfs.find(1) == 0);
        System.out.println(mfs.find(2) == 0);
        System.out.println(mfs.find(3) == 0);
        System.out.println(mfs.find(4) == 4);
        System.out.println(mfs.find(5) == 4);
        System.out.println(mfs.find(6) == 6);

        //Check that you have updated the indexes for the visited nodes (by calling find())
        //This makes sure that when trying to access the elements Set again it goes really fast.
        //It's like "splaying"
        System.out.println(mfs.set[1] == 0);
        System.out.println(mfs.set[2] == 0);
        System.out.println(mfs.set[3] == 0);

        //Check that all elements are in the same Set
        mfs.merge(0,4);
        for (int i = 0; i < length - 1; i++) {
            System.out.println(mfs.find(i) == 0);
        }

        //Check that the root knows many element there is
        System.out.println(mfs.set[mfs.find(0)] == -6);

        //Check that a merge with itself works.
        int d = mfs.set[0];
        mfs.merge(0, 0);
        System.out.println(mfs.set[0] == d);





    }



}
