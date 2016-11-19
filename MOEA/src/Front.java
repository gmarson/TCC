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
            System.out.println("Empty Member while adding it in front!");
            return;
        }

        m.setFrontId(this.id);
        members.add(m);
    }


    public void crowdingDistanceOfFront()
    {

        //System.out.println("Printando os membros antes de fazer o crowding distance");
        //this.printFront();

        ProblemSCH sch = ProblemSCH.getInstance();
        int membersSize = this.members.size();
        double fiplus1, fiminus1,fmax,fmin;
        if(members.isEmpty()) return;
        Scanner s = new Scanner(System.in);

        switch (membersSize)
        {
            case 1:
            {
                this.members.get(0).setCrowdingDistanceValue(Double.POSITIVE_INFINITY);
                return;
            }
            case 2:
            {
                this.members.get(0).setCrowdingDistanceValue(Double.POSITIVE_INFINITY);
                this.members.get(membersSize-1).setCrowdingDistanceValue(Double.POSITIVE_INFINITY);
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
            this.members.get(0).setCrowdingDistanceValue(Double.POSITIVE_INFINITY);
            this.members.get(membersSize-1).setCrowdingDistanceValue(Double.POSITIVE_INFINITY);
            this.members.get(0).setInfinity(true);
            this.members.get(membersSize-1).setInfinity(true);
            fmin = this.members.get(0).getCurrentFunctionValue();
            fmax = this.members.get(membersSize-1).getCurrentFunctionValue();

            //System.out.println("Imprimindo membros na front");
            //this.printFront();
            //System.out.println("Pessoal que recebeu infinto no crowding ");
            //this.members.get(0).printMember();
            //this.members.get(membersSize-1).printMember();

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

        for(Member m : this.members)
        {
            m.changeToCrowdingDistanceValue();
        }
        Collections.sort(this.members);
        //System.out.println("Printando os membros depois de fazer o crowding distance");
        //this.printFront();
        //s.nextLine();
    }


    public void removeMembersGivenRange(int max)
    {
        Scanner s = new Scanner(System.in);
        int firstFontSize = this.members.size();
        Member memberToBeRemoved;

        if(firstFontSize==2)
        {
            memberToBeRemoved = this.getMembers().get(0);

            Population.removeMemberGivenObject(memberToBeRemoved);
            this.members.remove(memberToBeRemoved);
        }
        else
        {
            while(this.members.size() > max)
            {

                //System.out.println("TO DENTRO DO LAÇO AKI Ó");
                //System.out.println("primeiro tamanho da front: "+ firstFontSize);
                //System.out.println("tamanho da front: "+ this.getMembers().size());


                //this.printFront();
                memberToBeRemoved = this.getMembers().get(0); //deleto sempre a mesma posição pois o array regride
                //System.out.println("Membro a ser removido ");
                //memberToBeRemoved.printMember();

                //s.nextLine();
                Population.removeMemberGivenObject(memberToBeRemoved);
                this.members.remove(memberToBeRemoved);

                //s.nextLine();
            }
        }
    }

    public void removeMember(int index)
    {
        //System.out.println("Removendo membro ");
        //this.members.get(index).printMember();
        this.members.remove(index);
    }

    //Debugging ...

    public void printFront()
    {
        System.out.println("Membros na fronte");
        for(Member m: this.members)
        {
            m.printMember();
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
