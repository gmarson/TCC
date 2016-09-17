/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;
public abstract class Fronts {
    private ArrayList<Front> fronts;

    public Fronts()
    {
        this.fronts = new ArrayList<Front>();
    }

    public void addFront(Front f)
    {
        this.fronts.add(f);
    }

    public void resetFronts()
    {
        this.fronts.clear();
    }

    public void updateDominanceRelations(){

    }

    //Getters and Setters
    public ArrayList<Front> getFronts() {
        return fronts;
    }
    public void setFronts(ArrayList<Front> fronts) {
        this.fronts = fronts;
    }
}
