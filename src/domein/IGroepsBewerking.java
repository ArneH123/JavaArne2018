/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Arne
 */
public abstract class IGroepsBewerking {

    public static ObservableList<IGroepsBewerking> beschikbareBewerkingen = FXCollections.observableArrayList();
    public static int currectId;
    static {
           beschikbareBewerkingen.add(new MaalBewerking("Doe maar maal", 12));
           beschikbareBewerkingen.add(new MaalBewerking("Doe maar gedeeld door", 50));
           beschikbareBewerkingen.add(new MaalBewerking("Maal 50 gedeeld door", 50));
    }
    
    abstract String geefBewerkingToString();
    public abstract String geefNaam();
    public abstract int haalID();
    abstract boolean uitkomstIsGeldig(Oefening oefening, double getal);
}
