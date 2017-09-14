package SupportingFiles; /**
 * Created by gabrielm on 12/01/17.
 */
import Population.Member;
import Population.Population;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Matrix {

    public int rows;
    public int columns;
    int size;
    public double[][] distance;
    public int[][] binaryMatrix;
    public int[][] decimalMatrix;
    public int[] sizeOfNonZeroElementsInDecimalMatrixRow;
    public Member[][] memberMatrix;


    public Matrix(int rows, int columns, Population population){
        memberMatrix = new Member[rows][columns];
        setDimensions(rows,columns);
        for (int i = 0; i < rows; i++) {
            memberMatrix[i][0] = population.population.get(i);
        }
    }

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
                binaryNumber = Utils.integerToBinary(i+1, Parameters.PROBLEM_SIZE+1);
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
        sizeOfNonZeroElementsInDecimalMatrixRow = new int[this.rows];
        decimal.setDimensions(this.rows,this.columns);

        for (int i = 0; i <this.rows ; i++) {
            int nonZeroElements = 0;

            for (int j = this.columns-1, idOfObjective =1; j >0 ; j--, idOfObjective++) {
                int number = this.binaryMatrix[i][j];

                if (number != 0)
                    nonZeroElements++;



                decimal.decimalMatrix[i][j] = number == 0? number : idOfObjective;
            }
            sizeOfNonZeroElementsInDecimalMatrixRow[i] = nonZeroElements;
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
        else if(decimalMatrix != null)
        {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j <columns ; j++) {
                    System.out.print((decimalMatrix[i][j])+"   ");
                }
                System.out.print("\n");
            }
        }
        else{
            for (int i = 0; i < rows; i++) {
                Member[] neighborhood = memberMatrix[i];
                for (int j = 0; j <columns ; j++) {
                    if(neighborhood[j] != null){
                        double distance = ( Utils.euclideanDistanceBasedOnWeightVector(neighborhood[0], neighborhood[j]) );
                        //String dstring = new DecimalFormat("##.#########").format(distance);
                        //System.out.print( distance +"   ");
                        System.out.print(neighborhood[j]+ "   ");
                    }


                }
                System.out.print("\n");
            }
        }

    }


    public int size()
    {
        return size;
    }


    public int getNeighborhoodSize(int cellIndex){
        for (int i = 0; i < Parameters.NEIGHBOURHOOD_SIZE; i++) {
            if(memberMatrix[cellIndex][i] != null){
                return i;
            }
        }

        return -1;
    }



}
