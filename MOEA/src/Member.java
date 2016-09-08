/**
 * Created by gmarson on 9/8/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Ndi = Numero de soluções que dominam a solução i
 * Ui = Conjunto de soluções dominadas por i. Cada i do tipo member está associado a um arrayList de Members
 */
public class Member {
    private int Ndi;
    private HashMap<Member, ArrayList<Member>> Ui = new HashMap<Member, ArrayList<Member>>(); //TODO mudar a instanciação pro construtor
    private HashMap<Member, ArrayList<Function>> resultingFunctions;
    private Data data;


}
