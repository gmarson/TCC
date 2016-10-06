import java.util.*;

/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

public class Front{

    private int id;
    private ArrayList<Member> members;

    public Front(int FrontCounter)
    {
        this.id = FrontCounter;
        members = new ArrayList<Member>();
    }

    public void addMemberToFront(Member m)
    {
        if(m ==null){
            System.out.println("Empty Member!");
            return;
        }

        members.add(m);
    }

    public ArrayList<Member> crowdingDistance(int frontId)
    {

        ProblemSCH sch = ProblemSCH.getInstance();
        ArrayList<Front> f= Fronts.getInstance();
        ArrayList<Member> members = f.get(frontId).getMembers();
        int membersSize = members.size();
        double fiplus1, fiminus1,fmax,fmin;
        if(members.isEmpty() || membersSize ==1) return members;

        for(int i=0;i<sch.getNumberOfFunctions();i++)
        {
            //ordeno por funcao na posição i
            for(Member m : members)
            {
                m.changeCurrentFunctionValue(i);
            }
            Collections.sort(members);

            members.get(0).setCrowdingDistanceValue(Integer.MAX_VALUE);
            members.get(membersSize).setCrowdingDistanceValue(Integer.MAX_VALUE);

            for(int j=1;j<membersSize-1;j++)
            {
                fiplus1 = (double) members.get(j+1).getCurrentFunctionValue();
                fiminus1 =(double) members.get(j-1).getCurrentFunctionValue();
                fmax = sch.getMaxValue();
                fmin = sch.getMinValue();
                members.get(j).addToCrowdingDistanceValue((fiplus1-fiminus1)/(fmax-fmin));
                //JESUS ABENCOA !!!
            }

        }


        return null;
    }

/*

    //Comparators
    public class getAttribute1Comparator implements Comparator<Member> {
        @Override
        public int compare(Member o1, Member o2) {
            return o1.getResultOfFunctions().get(1).compareTo(o2.getResultOfFunctions().get(1));
        }
    }

    static Comparator<Member> getAttribute2Comparator() {
        return new Comparator<Member>() {
            @Override
            public int compare(Member o1, Member o2) {
                return o1.getResultOfFunctions().get(2).compareTo(o2.getResultOfFunctions().get(2));
            }

        };
    }
*/

    //Getters and Setters
    public ArrayList<Member> getMembers() {
        return members;
    }
    public int getId() {
        return id;
    }
}
