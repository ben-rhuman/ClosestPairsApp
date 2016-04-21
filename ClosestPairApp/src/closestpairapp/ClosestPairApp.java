package closestpairapp;
/*
 * Authors: Ben Rhuman, Isaac Sotelo, Brendan Tracey
 * CSCI 232 - Lab 3
 *
 */

import java.util.ArrayList;
import java.util.Arrays;

public class ClosestPairApp {

    //Arrays holding coordinate values sorted by x coordinates.
    public static Point[] coordinateX = {new Point(2.0, 7.0), new Point(4.0, 13.0), new Point(5.0, 8.0), new Point(10.0, 5.0), new Point(14.0, 9.0), new Point(15.0, 5.0), new Point(17.0, 7.0), new Point(19.0, 10.0), new Point(22.0, 7.0), new Point(25.0, 10.0), new Point(29.0, 14.0), new Point(30.0, 2.0)};
    
    public static void main(String[] args) { // Start main
        System.out.print("Input points: \n");
        for (Point val : coordinateX) { //Prints out the points that are going to be searched
            val.print();
        }
        System.out.print("\n---------------------------------------------\n");
        System.out.println("Solving Problem: Point[0]...Point[" + (coordinateX.length - 1) + "]");
        Pair finalPair = closestPairs(coordinateX, 0, coordinateX.length - 1);   //Finds the closest pair within the data set
        System.out.println("-------------------------------------------------------------------");
        System.out.print("Final result: P1: " );         //Prints out final results
        finalPair.left.print();
        System.out.print(", P2: ");
        finalPair.right.print();
        System.out.printf(", Distance: %.1f\n" , finalPair.distance);       
    } // End main

    private static Pair closestPairs(Point[] data, int start, int end) {  //Uses a divide and conquer technique to find the closest points in the data set.
        if (data.length == 1) {  //If only one point
            Pair p = new Pair(data[0], null); //return a pair with a distance of positive infinity
            printResult(p);
            return p;
        }
        if (data.length == 2) { //If ther are two points
            Pair p = new Pair(data[0], data[1]); //return a pair with the distance between them
            printResult(p);
            return p;
        }
        int median = (data.length - 1) / 2;  //finds the breaking point of the array
        int divPoint = start + ((end - start) / 2); //divPoint, start, and end all keep track of the array fragment's position in the main array, used for printing results.
        System.out.println("  Dividing at Point[" + divPoint + "]");      //Prints out dividing point
        
        Point[] sl = Arrays.copyOfRange(data, 0, median + 1);      //Breaks the array at the median and copys it to a new smaller array
        Point[] sr = Arrays.copyOfRange(data, median + 1, data.length);
        
        System.out.println("Solving Problem: Point[" + start + "]...Point[" + divPoint + "]" + sl.length);  //Prints out the segment of the original array that is being tested
        Pair dl = closestPairs(sl, start, divPoint);       //Finds the closest point on the left side of the break
        
        System.out.println("Solving Problem: Point[" + (divPoint + 1) + "]...Point[" + end + "]" + sr.length);  //Prints out the segment of the original array that is being tested
        Pair dr = closestPairs(sr, divPoint + 1, end);       //Finds the closest point on the right side of the break
        
        System.out.println("Combining Problems: Point[" + start + "]...Point[" + divPoint + "] and Point[" + (divPoint + 1) + "]...Point[" + end + "]" + data.length); //Prints out the segments that are to be combined.
        return combine(median, data, min(dl, dr));        //Finds and returns the closest points when the left and right side are combined.  
    }// End closestPairs   

    private static void printResult(Pair r) {  //Prints the data contained in a pair in a specific format
        if (r.distance < Double.POSITIVE_INFINITY) {
            System.out.print("  Found result: P1: ");
            r.left.print();
            System.out.print(", P2: ");
            r.right.print();
            System.out.printf(", Distance: %.1f\n", r.distance);
        } else {
            System.out.print("  Found result: INF\n");
        }
    }// End printResults

    private static Pair min(Pair dl, Pair dr) { //Returns the closest of two inputed pairs
        if (dl.distance < dr.distance) {
            return dl;
        } else {
            return dr;
        }
    }// End min

    private static Pair combine(int median, Point[] data, Pair md) {   //Combines two smaller arrays, then looks to see if there is a closer pair within md distance from the median
        ArrayList<Point> Ly = new ArrayList<>(); //Use array list because we dont know how many point fall within the "band"
        Point medianP = data[median];   //Need to keep track of median for calculations
        quickSort(data, 0, data.length - 1); //Sorts the data array by y value instead of x
        
        for (Point val : data) {
            if (Math.abs(val.x - medianP.x) < md.distance){  //If within md distance from the median point
                Ly.add(val);                                 //then add to the arraylist
            }
        }
        for (int i = 0; i < Ly.size(); i++) { 
            if (Ly.get(i).x <= medianP.x) {           //If the point is within the band but on the left of the median                                                      
                md = minOfBand(md, Ly, i);            //Check the points around it for a closer pair     
            }
        }
        printResult(md);  //Prints the results of the combine step
        return md;  //Returns the new closest pair
    }// End combine

    private static Pair minOfBand(Pair md, ArrayList<Point> Ly, int i) {
        Pair min;
        
        for(int j = i-3; j < i+4; j++){
            if(j > -1 && j < Ly.size() && j != i){  //Checks up to 6 values around a given point from the left of the band looking for a closest pair.
                min = min(md, new Pair(Ly.get(i), Ly.get(j)));                         
                if(min.distance < md.distance){
                    md = min;
                }
            } 
        }  
        return md;
    }// End minOfBand

    private static void quickSort(Point[] coordinateY,int lowerIndex, int higherIndex) { // Start quicksort

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        double pivot = coordinateY[lowerIndex + (higherIndex - lowerIndex) / 2].y;
        // Divide into two arrays
        while (i <= j) {
            while (coordinateY[i].y < pivot) {
                i++;
            }
            while (coordinateY[j].y > pivot) {
                j--;
            }
            if (i <= j) {
                Point temp = coordinateY[i];
                coordinateY[i] = coordinateY[j];
                coordinateY[j] = temp;
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j) {
            quickSort(coordinateY, lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(coordinateY,i, higherIndex);
        }
    } // End quicksort
}// End ClosestPairsApp class

//--------------------------------------------------------------
class Point {

    public double x = 0.0;
    public double y = 0.0;

    public Point(double x, double y) {// Start Point, a Point consists of an X value and a Y value
        this.x = x;
        this.y = y;
    }// End Point

    public void print() {// Start Print
        System.out.print("(" + x + "," + y + ")");
    }// End Print
}// End Point class

//--------------------------------------------------------------
class Pair {

    public Point left;
    public Point right;
    public double distance;

    public Pair(Point left, Point right) {//A pair consists of two Points and the distance between them.
        this.left = left;
        this.right = right;
        if (right == null) {
            distance = Double.POSITIVE_INFINITY; //Returns positive infinity if there is only one point in a pair
        } else {
            distance = calcDist(left, right);
        }
    }//End of Pair constructor

    private double calcDist(Point pl, Point pr) {
        return Math.sqrt((pl.x - pr.x) * (pl.x - pr.x) + (pl.y - pr.y) * (pl.y - pr.y));
    }// End of calcDist
}//End pair class
