/**
 * Created by gmarson on 9/8/2016.
 * TCC UFU
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * ndi = Numero de soluções que dominam a solução i, ou seja que me dominam (dado que sou i)
 * ui = Conjunto de soluções dominadas por i. Cada i do tipo member está associado a um arrayList de Members
 */
public class Member implements Comparable<Member>
{
    private int frontId = -1;
    private int ndi = 0;
    private ArrayList<Member> ui =  null;
    private ArrayList<Integer> resultOfFunctions = null;
    private double currentFunctionValue;
    private double data;
    private String binaryData;
    private int partialNdi = 0;
    private boolean alreadyInFront = false;
    private boolean negative = false;
    private double crowdingDistanceValue =0.0;

    //constructors
    public Member(double data)
    {
        this.ui = new ArrayList<Member>();
        this.resultOfFunctions = new ArrayList<Integer>();
        this.data =data;
        if(this.data < 0)
        {
            this.negative = true;
        }
        this.doubleToBinary();

    }

    public Member(String binaryData)
    {
        //Scanner s = new Scanner(System.in);
        this.ui = new ArrayList<Member>();
        this.resultOfFunctions = new ArrayList<Integer>();
        this.binaryData = binaryData;
        char signal = binaryData.charAt(0);


        this.setDataGivenBinary(binaryData.substring(1));
        //System.out.println("Data do member criado: "+this.data);
        //s.nextLine();
        if(signal == '1')
        {
            this.data *= -1;
            this.negative = true;
        }
    }

    public Member(Member another)
    {
        //TODO ver se precisa do ndi
        this.ui = another.ui;
        this.resultOfFunctions = another.resultOfFunctions;
        this.data = another.data;
        this.negative = another.negative;
        this.binaryData = another.binaryData;
    }

    public void changeToCrowdingDistanceValue()
    {
        this.currentFunctionValue = this.crowdingDistanceValue;
    }

    public void setNegative(String signal)
    {
        if(signal == "0")
        {
            this.negative = false;
        }
        else
        {
            this.negative = true;
        }
    }

    public void changeCurrentFunctionValue(int functionId)
    {
        this.currentFunctionValue = this.resultOfFunctions.get(functionId);
    }

    public void doubleToBinary()
    {

        Integer number = (int)Math.abs(this.data);
        this.binaryData = Integer.toBinaryString(number);

        if(this.negative)
        {
            this.binaryData = "1" + this.binaryData;
        }
        else
        {
            this.binaryData = "0" + this.binaryData;
        }

        //System.out.println("Binary data = "+this.binaryData+"\nNumber "+this.data);

    }

    public void newGeneration()
    {
        this.ndi = 0;
        this.ui.clear();
        this.partialNdi = 0;
        this.alreadyInFront = false;
        this.crowdingDistanceValue =0.0;
        this.frontId = -1;
    }

    public void setDataGivenBinary(String binaryString)
    {
        int finalNumber =0;

        binaryString = new StringBuilder(binaryString).reverse().toString();
        for(int i=0;i<binaryString.length();i++)
        {
            if(binaryString.charAt(i) == '1')
            {
                finalNumber += Math.pow(2,i);
            }
        }
        this.data = finalNumber;

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

    //used for fronts calculations
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
        else
        {
            System.out.println("Null member while adding to UI");
        }
    }

    //Debugging  ...
    public void printMember()
    {
        System.out.println("Instance:"+this+"\nData: "+this.data+"\nNdi ="+this.ndi+"\nUi Group:"+this.ui+"\nFrontId = "+this.frontId+"\nCrowding Distance"+this.crowdingDistanceValue);

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
            //System.out.println("Result of Functions Empty!");
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
    public void setCrowdingDistanceValue(double crowdingDistanceValue) {
        this.crowdingDistanceValue = crowdingDistanceValue;
    }
    public double getCurrentFunctionValue() {
        return currentFunctionValue;
    }
    public void setCurrentFunctionValue(int currentFunctionValue) {
        this.currentFunctionValue = currentFunctionValue;
    }
    public String getBinaryData() {
        return binaryData;
    }
    public void setBinaryData(String binaryData) {
        this.binaryData = binaryData;
    }
    public int getFrontId() {
        return frontId;
    }
    public void setFrontId(int frontId) {
        this.frontId = frontId;
    }

    public boolean isNegative()
    {
        if (this.data < 0) return true;
        return false;
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
