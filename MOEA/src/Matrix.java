/**
 * Created by gabrielm on 12/01/17.
 */
import java.text.DecimalFormat;
import java.util.ArrayList;

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
        DecimalFormat df = new DecimalFormat("#.##");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <columns ; j++) {
                System.out.print(df.format(distance[i][j])+"   ");
            }
            System.out.print("\n");
        }
    }

    public int size()
    {
        return size;
    }


    public ArrayList<Double> getDistanceFromMemberIndex(int memberIndex)
    {
        ArrayList<Double> distanceArray = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            if (i != memberIndex) // todo a treta ta aki, ele ta retornando uma menos por causa 
            //todo do incide. Entao vamo fazer assim, vamo usar o pair pq ai eu retorno a lista dos values
            //todo com o indice do individuo na matriz.
            {
                distanceArray.add(distance[memberIndex][i]);
            }
        }
        System.out.println("Ta ai a distancia: "+distanceArray);
        return distanceArray;

    }

}
