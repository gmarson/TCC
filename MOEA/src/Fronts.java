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
        ArrayList<Member> p = Population.getInstance();
        if(p.isEmpty()) {
            System.out.println("Empty Population while building Fronts!");
            return;
        }
        while(true){
            fronts.add(new Front(frontCounter));
            for(Member member: p ) {
                if(member.isAlreadyInFront()) exitLoop++;

                if (member.getPartialNdi() == 0) {
                    exitLoop++;
                    member.setAlreadyInFront(true);
                    fronts.get(frontCounter).addMemberToFront(member);
                }
                member.getData();
                member.removeFromPartialNdi();
            }
            p.size();
            if(exitLoop == p.size()) break;
            frontCounter++;
            exitLoop = 0;
        }




    }

    public static void getBestMemberGivenFront()
    {
        //di = di + ( f(i+1) - f(i-1) ) / ( fmax - fmin )
        ArrayList<Front> fronts = Fronts.getInstance();


    }


    //Deebugging ...
    public static void printFronts()
    {
        for(Front f: fronts)
        {
            System.out.println("Front Number"+f.getId());
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
