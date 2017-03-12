package Utilities; /**
 * Created by gabrielm on 12/01/17.
 */
import Utilities.Utils;
import Constants.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Matrix {

    public int rows;
    public int columns;
    int size;
    public double[][] distance;
    public int[][] maskHandler;


    public Matrix(int rows, int columns) {
        setDimensions(rows,columns);
        distance = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <columns ; j++) {
                distance[i][j] = (i == j)? 0 :  -1;
            }
        }
    }

    public Matrix(int rows, int columns, boolean binary){
        if(binary){
            ArrayList<Integer> binaryNumber;
            setDimensions(rows,columns);
            maskHandler = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                binaryNumber = Utils.integerToBinary(i+1,Constants.PROBLEM_SIZE+1);
                for (int j = columns-1; j >=0 ; j--) {
                    maskHandler[i][j] = binaryNumber.get(j);
                }
            }
        }
    }

    private void setDimensions(int rows, int columns){
        this.rows =rows;
        this.columns = columns;
        this.size = rows * columns;
    }

    public void addNewDistance(int row, int column, double value)
    {
        if (value >=0 && value != -1)
            distance[row][column] = value;
    }

    public void removeDistance(int row, int column)
    {
        distance[row][column] = -1;
    }


    public void printMatrix()
    {
        if (distance != null)
        {
            DecimalFormat df = new DecimalFormat("#.##");

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <columns ; j++) {
                    System.out.print(df.format(distance[i][j])+"   ");
                }
                System.out.print("\n");
            }
        }
        else
        {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <columns ; j++) {
                    System.out.print((maskHandler[i][j])+"   ");
                }
                System.out.print("\n");
            }
        }

    }

    public int size()
    {
        return size;
    }




}
