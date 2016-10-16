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

    public void crowdingDistanceOfFront()
    {

        ProblemSCH sch = ProblemSCH.getInstance();
        ArrayList<Front> f= Fronts.getInstance();
        int membersSize = this.members.size();
        double fiplus1, fiminus1,fmax,fmin;
        if(members.isEmpty()) return;


        switch (membersSize)
        {
            case 1:
            {
                this.members.get(0).setCrowdingDistanceValue(Integer.MAX_VALUE);
                return;
            }
            case 2:
            {
                this.members.get(0).setCrowdingDistanceValue(Integer.MAX_VALUE);
                this.members.get(membersSize-1).setCrowdingDistanceValue(Integer.MAX_VALUE);
                return;
            }
            default:
                break;
        }


        Double currentFunctionValue, previousFunctionValue;
        for(int i=0;i<sch.getNumberOfFunctions();i++)
        {
            //atualizo as funções objetivos do laço
            for(Member m : this.members)
            {
                m.changeCurrentFunctionValue(i);
            }

            //ordeno os individuos pela função objetivo
            Collections.sort(this.members);

            //seto os extremos com infinito
            this.members.get(0).setCrowdingDistanceValue(Integer.MAX_VALUE);
            this.members.get(membersSize-1).setCrowdingDistanceValue(Integer.MAX_VALUE);
            fmax = this.members.get(0).getCurrentFunctionValue();
            fmin = this.members.get(membersSize-1).getCurrentFunctionValue();

            if(fmax - fmin == 0)
            {
                fmax += 0.1;
            }

            //faço o calculo do crownding distance normal para o resto
            for(int j=1;j<membersSize-1;j++)
            {
                fiplus1 = (double) this.members.get(j+1).getCurrentFunctionValue();
                fiminus1 =(double) this.members.get(j-1).getCurrentFunctionValue();
                this.members.get(j).addToCrowdingDistanceValue((fiplus1-fiminus1)/(fmax-fmin));
            }
        }
    }



    //Getters and Setters
    public ArrayList<Member> getMembers() {
        return members;
    }
    public int getId() {
        return id;
    }
}
