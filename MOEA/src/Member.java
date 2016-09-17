/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ndi = Numero de soluções que dominam a solução i
 * ui = Conjunto de soluções dominadas por i. Cada i do tipo member está associado a um arrayList de Members
 */
public class Member {
    private int ndi = 0;
    private ArrayList<Member> ui =  null;
    private ArrayList<Object> resultOfFunctions = null; // Acho que um arraylist basta
    private double data;

    //constructor
    public Member(double data)
    {
        this.ui = new ArrayList<Member>();
        this.resultOfFunctions = new ArrayList<Object>();
        this.data =data;
    }

    public void addMemberToUi(Member m)
    {
        if (m != null)
        {
            this.ui.add(m);
        }
    }
    public void removeAllMembersFromUi(){
        for(int i=0;i<this.ui.size();i++)
        {
            this.ui.remove(i);
        }
    }

    public void printMember()
    {
        System.out.println("Data: "+this.data+"\nNdi ="+this.ndi+"\nUi Group:"+this.ui);
        for(int i=0;i<this.resultOfFunctions.size();i++)
        {
            System.out.print("F"+i+": "+this.resultOfFunctions.get(i)+"\t");
        }
        System.out.println("\n");
    }





    //Getters and Setters
    public int getNdi() {
        return ndi;
    }
    public void setNdi(int ndi) {
        this.ndi = ndi;
    }
    public ArrayList<Member> getUi() {
        return ui;
    }
    public void setUi(ArrayList<Member> ui) {
        this.ui = ui;
    }
    public ArrayList<?> getResultOfFunctions() {
        return resultOfFunctions;
    }
    public void setResultOfFunctions(ArrayList<Object> resultOfFunctions) {
        this.resultOfFunctions = (ArrayList<Object>) resultOfFunctions.clone();
    }
    public double getData() {
        return data;
    }
    public void setData(double data) {
        this.data = data;
    }
}
