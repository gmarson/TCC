/**
 * Created by gmarson on 9/8/2016.
 */

import java.util.ArrayList;
public class Fronts {
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
        for(int i=0;i<this.fronts.size();i++)
        {
            this.fronts.remove(i);
        }
    }

}
