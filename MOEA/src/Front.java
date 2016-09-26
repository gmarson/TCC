import java.util.ArrayList;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

public class Front {

    private int id;
    private ArrayList<Member> members;

    public Front(int FrontCounter)
    {
        this.id = FrontCounter;
    }

    public void addMemberToFront(Member m)
    {
        if(m ==null){
            System.out.println("Empty Member!");
            return;
        }

        members.add(m);
    }


    //Getters and Setters
    public ArrayList<Member> getMembers() {
        return members;
    }
}
