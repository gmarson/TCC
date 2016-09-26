/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;
public abstract class Fronts {
    private static int frontCounter = 0;
    private static ArrayList<Front> fronts = new ArrayList<Front>();

    //Singleton
    private Fronts()
    {

    }
    public static ArrayList<Front> getInstance(){
        return fronts;
    }


    //Basic Operations
    public static void addFront()
    {
        fronts.add(new Front(Fronts.frontCounter));
        frontCounter++;
    }
    public static void resetFronts()
    {
        fronts.clear();
        frontCounter = 0;
    }

    public void makeFronts()
    {
        ArrayList<Member> p = Population.getInstance();
        if(p.isEmpty()) {
            System.out.println("Empty Population while building Fronts!");
            return;
        }




    }


    //Getters and Setters
    public static ArrayList<Front> getFronts() {
        return fronts;
    }
    public static void setFronts(ArrayList<Front> fronts) {
        Fronts.fronts = fronts;
    }
}
