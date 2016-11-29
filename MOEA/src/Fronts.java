/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;
import java.util.Scanner;

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

    public static void removeFronts(int index)
    {
        int oldFrontsSize = fronts.size();
        int oldIndex = index;
        Front front;

        while(index < oldFrontsSize) {
            //System.out.println("Removendo a front");
            front = fronts.get(oldIndex);
            for(Member m:front.getMembers())
            {
                Population.removeMemberGivenObject(m);
            }
            fronts.remove(oldIndex);
            frontCounter--;
            index++;
        }
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

    public static void makeFronts()
    {
        int exitLoop = 0;
        boolean firstMemberInFront = true;
        ArrayList<Member> p = Population.getInstance();
        if(p.isEmpty()) {
            System.out.println("Empty Population while building Fronts!");
            return;
        }

        while(true)
        {
            for(Member member: p )
            {
                if(member.isAlreadyInFront()) {
                    exitLoop++;
                }
                else if(member.getPartialNdi() ==0)
                {

                    if(firstMemberInFront)
                    {
                        //System.out.println("Construi a front");
                        fronts.add(new Front(frontCounter));
                        firstMemberInFront = false;
                    }

                    exitLoop++;
                    member.setAlreadyInFront(true);

                    //System.out.println("Tamanho de frontS "+fronts.size());
                    //System.out.println("Tamanho do frontcounter "+frontCounter);

                    fronts.get(frontCounter).addMemberToFront(member);
                    //System.out.println("Membro adicionado na front: "+member);
                }

                member.removeFromPartialNdi();
            }


            //System.out.println("front counter  " +frontCounter);
            //System.out.println("Exit loop "+exitLoop);
            //System.out.println("p.size "+p.size());

            if(exitLoop == p.size()) break;
            exitLoop = 0;

            if(firstMemberInFront == false)
            {
                firstMemberInFront = true;
                frontCounter++;
            }
        }

    }

    public static ArrayList<Integer> returnFirstFrontData()
    {
        ArrayList<Member> firstFront = fronts.get(0).getMembers();
        ArrayList<Integer> dataOfFront = new ArrayList<Integer>();
        boolean newElement = true;

        for(int i =0; i<firstFront.size();i++)
        {

            int data =  (int) firstFront.get(i).getData();

            if (dataOfFront.isEmpty())
            {
                dataOfFront.add( new Integer (data) );
            }
            else
            {
                for(int j=0;j<dataOfFront.size();j++)
                {
                    if (dataOfFront.get(j) == data) {
                        newElement = false;
                        break;
                    }
                }

                if (newElement)
                {
                    dataOfFront.add(new Integer(data));
                }
                newElement = true;
            }

        }

        return  dataOfFront;
    }

    //Deebugging ...
    public static void printFronts()
    {
        if(fronts.size() ==0)
        {
            System.out.println("Empty Fronts while printing them!");
            return;
        }
        for(Front f: fronts)
        {
            System.out.println("Front Number"+f.getId());
            if(f.getMembers() == null)
            {
                System.out.println("tem nada aki");
            }
            f.getMembers().forEach(Member::printMember);
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
