package closestpairapp;
/*
 * Authors: Ben Rhuman, Isaac Sotelo, Brendan Tracey
 * CSCI 232 - Lab 3
 *
 */

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class ClosestPairApp {

    /**
     * @param args the command line arguments
     */
    //Arrays holding coordinate values sorted by x and y coordinates.
    public static Point[] coordinateX = {new Point(2.0, 7.0), new Point(4.0, 13.0), new Point(5.0, 8.0), new Point(10.0, 5.0), new Point(14.0, 9.0), new Point(15.0, 5.0), new Point(17.0, 7.0), new Point(19.0, 10.0), new Point(22.0, 7.0), new Point(25.0, 10.0), new Point(29.0, 14.0), new Point(30.0, 2.0)};
    public static Point[] coordinateY = {new Point(2.0, 7.0), new Point(4.0, 13.0), new Point(5.0, 8.0), new Point(10.0, 5.0), new Point(14.0, 9.0), new Point(15.0, 5.0), new Point(17.0, 7.0), new Point(19.0, 10.0), new Point(22.0, 7.0), new Point(25.0, 10.0), new Point(29.0, 14.0), new Point(30.0, 2.0)};

    public static void main(String[] args) { // Start main
        System.out.print("Input points: \n");
        for (Point val : coordinateX) {
            val.print();
        }
        System.out.print("\n---------------------------------------------\n");
        quickSort(0, coordinateY.length - 1);
        closestPairs(coordinateX);
    } // End main

    private static Pair closestPairs(Point[] data) {
        System.out.println("Solving Problem: Point[" + 0 + "]...Point[" + (data.length - 1) + "]");
        if (data.length == 1) {
            return new Pair(data[0], null);
        }
        if (data.length == 2) {
            return new Pair(data[0], data[1]);
        }
        int median = (data.length - 1) / 2;
        System.out.println("  Dividing at Point[" + median + "]");
        Point[] sl = Arrays.copyOfRange(data, 0, median + 1);
        Point[] sr = Arrays.copyOfRange(data, median + 1, data.length);
    //    System.out.println("Solving Problem: Point[" + 0 + "]...Point[" + median + "]");
        Pair dl = closestPairs(sl);
    //    System.out.println("Solving Problem: Point[" + (median + 1) + "]...Point[" + data.length + "]");
        Pair dr = closestPairs(sr);
        Pair dc = combine(median, data, min(dl, dr).distance);
        return min(dl, dr, dc);
    }// End closestPaits   

    private static Pair min(Pair dl, Pair dr) {
        if (dl.distance < dr.distance) {
            return dl;
        } else {
            return dr;
        }
    }

    private static Pair min(Pair dl, Pair dr, Pair dc) {
        Pair sp = min(dl,dr);
        if (sp.distance < dc.distance) {
            return sp;
        } else {
            return dc;
        }
    }

    private static Pair combine(int median, Point[] data, double dist) {
         ArrayList<Point> Ly = new ArrayList<Point>();
         for (Point val : coordinateY) {
             if(Math.abs(val.x - data[median].x) < dist){
                 Ly.add(val);
             }
         }
    }

    private static void quickSort(int lowerIndex, int higherIndex) { // Start quicksort

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        double pivot = coordinateY[lowerIndex + (higherIndex - lowerIndex) / 2].y;
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a
             * number from right side which is less then the pivot value. Once
             * the search is done, then we exchange both numbers.
             */
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
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
    } // End quicksort
}// End ClosestPairsApp class

//--------------------------------------------------------------
class Point {

    public double x = 0.0;
    public double y = 0.0;

    public Point(double x, double y) {// Start Point
        this.x = x;
        this.y = y;
    }// End Point

    public void print() {// Start Print
        System.out.print("(" + x + "," + y + ")");
    }// End Print
}// End Point class

class Pair {

    public Point left;
    public Point right;
    public double distance;

    public Pair(Point left, Point right) {
        this.left = left;
        this.right = right;
        if (right == null) {
            distance = Double.POSITIVE_INFINITY;
        } else {
            distance = calcDist(left, right);
        }
        System.out.printf("%.1f\n", distance);
    }

    public double calcDist(Point pl, Point pr) {
        System.out.print("Found result: P1: ");
        pl.print();
        System.out.print(", P2: ");
        pr.print();
        System.out.print(", Distance: ");
        return Math.sqrt((pl.x - pr.x) * (pl.x - pr.x) + (pl.y - pr.y) * (pl.y - pr.y));
    }
}//End pair class
