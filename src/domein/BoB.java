package domein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BoB {
    
    private final ArrayList<Actie> acties;
    private final ArrayList<Opdracht> opdrachten;
    private final ArrayList<Toegangscode> toegangscodes;
    
    public BoB(ArrayList<Actie> a, ArrayList<Opdracht> o, ArrayList<Toegangscode> t){
        if(a.size() == 8 && o.size() == 8 && t.size() == 8){
            acties = a;
            opdrachten = o;
            toegangscodes = t;
        }else{
            throw new IllegalArgumentException("Elke List moet size 8 zijn!");
        }
    }
    
    public ArrayList<Actie> GeefActies(){
        return acties;
    }
    
    public ArrayList<Opdracht> GeefOpdrachtenShuffled(){
       ArrayList<Opdracht> l =  opdrachten;
       Collections.shuffle(l);
       return l;
    }
}