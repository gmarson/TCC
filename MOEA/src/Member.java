/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;
import java.util.Comparator;


/**
 * ndi = Numero de soluções que dominam a solução i, ou seja que me dominam (dado que sou i)
 * ui = Conjunto de soluções dominadas por i. Cada i do tipo member está associado a um arrayList de Members
 */
public class Member implements Comparable<Member>
{
    private int ndi = 0;
    private ArrayList<Member> ui =  null;
    private ArrayList<Integer> resultOfFunctions = null;
    private int currentFunctionValue; //TODO implementa comparable normal usando esse atributo e passa os valores das funçoes pra ca
    private double data;
    private int partialNdi = 0;
    private boolean alreadyInFront = false;
    private double crowdingDistanceValue =0.0;

    //constructor
    public Member(double data)
    {
        this.ui = new ArrayList<Member>();
        this.resultOfFunctions = new ArrayList<Integer>();
        this.data =data;
    }

    public void changeCurrentFunctionValue(int functionId)
    {
        this.currentFunctionValue = this.resultOfFunctions.get(functionId);
    }

    public void addToCrowdingDistanceValue(double value)
    {
        this.crowdingDistanceValue += value;
    }

    public void removeAllMembersFromUi(){
        for(int i=0;i<this.ui.size();i++)
        {
            this.ui.remove(i);
        }
    }

    //will be used for fronts calculations
    public void removeFromPartialNdi()
    {
        this.partialNdi--;
    }

    // add to ui and ndi
    public void addNdi()
    {
        this.ndi ++;
        this.partialNdi =this.ndi;
    }
    public void addMemberToUi(Member m)
    {
        if (m != null)
        {
            this.ui.add(m);
        }
    }

    //For Debugging Purposes ...
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
    public ArrayList<Integer> getResultOfFunctions() {
        if (resultOfFunctions.isEmpty()){
            System.out.println("Result of Functions Empty!");
            return null;
        }
        return resultOfFunctions;
    }
    public void setResultOfFunctions(ArrayList<Object> resultOfFunctions) {
        this.resultOfFunctions = (ArrayList<Integer>) resultOfFunctions.clone();
    }
    public double getData() {
        return data;
    }
    public void setData(double data) {
        this.data = data;
    }
    public int getPartialNdi() {
        return this.partialNdi;
    }
    public void setPartialNdi(int partialNdi) {
        this.partialNdi = partialNdi;
    }
    public boolean isAlreadyInFront() {
        return alreadyInFront;
    }
    public void setAlreadyInFront(boolean alreadyInFront) {
        this.alreadyInFront = alreadyInFront;
    }
    public double getCrowdingDistanceValue() {
        return crowdingDistanceValue;
    }
    public void setCrowdingDistanceValue(int crowdingDistanceValue) {
        this.crowdingDistanceValue = crowdingDistanceValue;
    }
    public int getCurrentFunctionValue() {
        return currentFunctionValue;
    }
    public void setCurrentFunctionValue(int currentFunctionValue) {
        this.currentFunctionValue = currentFunctionValue;
    }

    @Override
    public int compareTo(Member other) {
        if(this.currentFunctionValue > other.currentFunctionValue)
        {
            return 1;
        }
        else if(this.currentFunctionValue < other.currentFunctionValue)
        {
            return -1;
        }
        return 0;
    }
}
