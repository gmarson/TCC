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
                        System.out.println("Construi a front");
                        fronts.add(new Front(frontCounter));
                        firstMemberInFront = false;
                    }

                    exitLoop++;
                    member.setAlreadyInFront(true);

                    System.out.println("Tamanho de frontS "+fronts.size());
                    System.out.println("Tamanho do frontcounter "+frontCounter);

                    fronts.get(frontCounter).addMemberToFront(member);
                    System.out.println("Membro adicionado na front: "+member);
                }

                member.removeFromPartialNdi();
            }


            System.out.println("front counter  " +frontCounter);
            System.out.println("Exit loop "+exitLoop);
            System.out.println("p.size "+p.size());

            if(exitLoop == p.size()) break;
            exitLoop = 0;

            if(firstMemberInFront == false)
            {
                firstMemberInFront = true;
                frontCounter++;
            }
        }
    }

    //Deebugging ...
    public static void printFronts()
    {
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
