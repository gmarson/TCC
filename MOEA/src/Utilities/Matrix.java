package Utilities; /**
 * Created by gabrielm on 12/01/17.
 */
import Constants.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Matrix {

    public int rows;
    public int columns;
    int size;
    public double[][] distance;
    public int[][] binaryMatrix;
    public int[][] decimalMatrix;


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
            binaryMatrix = new int[rows][columns];
            for (int i = 0; i < rows; i++) {
                binaryNumber = Utils.integerToBinary(i+1,Constants.PROBLEM_SIZE+1);
                for (int j = columns-1; j >=0 ; j--) {
                    binaryMatrix[i][j] = binaryNumber.get(j);
                }
            }
        }
    }

    public Matrix(){}

    public Matrix buildDecimalMatrixGivenBinary(){
        Matrix decimal = new Matrix();
        decimal.decimalMatrix = new int [this.rows][this.columns];
        decimal.setDimensions(this.rows,this.columns);
        for (int i = 0; i <this.rows ; i++) {
            for (int j = this.columns-1, idOfObjective =1; j >0 ; j--, idOfObjective++) {
                int number = this.binaryMatrix[i][j];
                decimal.decimalMatrix[i][j] = number == 0? number : idOfObjective;
            }
        }

        return decimal;
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
        else if(binaryMatrix != null)
        {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <columns ; j++) {
                    System.out.print((binaryMatrix[i][j])+"   ");
                }
                System.out.print("\n");
            }
        }
        else
        {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <columns ; j++) {
                    System.out.print((decimalMatrix[i][j])+"   ");
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
