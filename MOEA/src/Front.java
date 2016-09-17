import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */
public class Front {
    private static int idCounter= 0; //TODO lembra de zerar antes da nova geração
    private int id;
    private int numberOfMembers=0;
    private HashMap<Front,ArrayList<Member>> mapFrontToMembers;

    public Front()
    {
        this.id = idCounter;
        idCounter++;
        this.mapFrontToMembers = new HashMap<Front,ArrayList<Member>>();
        this.mapFrontToMembers.put(this,null);
    }


}
