/**
 * Created by gabrielm on 12/01/17.
 */
public class Matrix {

    int rows;
    int columns;
    int size;
    double[][] distance;

    
    
    public Matrix(int rows, int columns) {
        this.rows =rows;
        this.columns = columns;
        this.size = rows * columns;
        distance = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <columns ; j++) {
                distance[i][j] = (i == j)? 0 :  -1;
            }
        }
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <columns ; j++) {
                System.out.print(distance[i][j]+"  ");
            }
            System.out.print("\n");
        }
    }
    
}
