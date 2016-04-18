package closestpairapp;
/*
 * Authors: Ben Rhuman, Isaac Sotelo, Brendan Tracey
 * CSCI 232 - Lab 3
 *
 */
public class ClosestPairApp {

    /**
     * @param args the command line arguments
     */
    public static Point[] coordinate = {new Point(2.0, 7.0), new Point(4.0,13.0), new Point(5.0,8.0), new Point(10.0,5.0), new Point(14.0,9.0), new Point(15.0,5.0), new Point(17.0,7.0), new Point(19.0,10.0), new Point(22.0,7.0), new Point(25.0,10.0), new Point(29.0,14.0), new Point(30.0,2.0)};
        
    public static void main(String[] args) {
        for(Point val : coordinate)
              val.print();
        quickSort(0, coordinate.length - 1);
        System.out.println("\n");
        for(Point val : coordinate)
              val.print();
    }
    
        
    
 
    private static void quickSort(int lowerIndex, int higherIndex) {
         
        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        double pivot = coordinate[lowerIndex+(higherIndex-lowerIndex)/2].y;
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a number
             * from right side which is less then the pivot value. Once the search
             * is done, then we exchange both numbers.
             */
            while (coordinate[i].y < pivot) {
                i++;
            }
            while (coordinate[j].y > pivot) {
                j--;
            }
            if (i <= j) {
                Point temp = coordinate[i];
                coordinate[i] = coordinate[j];
                coordinate[j] = temp;
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }
    
}

class Point {
    public double x = 0.0;
    public double y = 0.0;
    
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void print(){
        System.out.print("(" + x + "," + y + ")");        
    }
}
