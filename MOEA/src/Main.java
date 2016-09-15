/**
 * Created by gmarson on 9/8/2016.
 */
public class Main {
    public static void main(String args[])
    {
        Function sch = new ProblemSCH();
        Functions.getInstance().add(sch);
        new NSGAII();

    }
}
