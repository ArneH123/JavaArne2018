/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class iGroepsBewerking {

    public static ObservableList<iGroepsBewerking> beschikbareBewerkingen = FXCollections.observableArrayList();
    public static int currectId;
    static {
           beschikbareBewerkingen.add(new MaalBewerking("Doe maal 12", 12));
           beschikbareBewerkingen.add(new MaalBewerking("Doe maal 50", 50));
           beschikbareBewerkingen.add(new MaalBewerking("Doe maal 5", 5));
    }
    
    abstract String geefBewerkingToString();
    public abstract String geefNaam();
    public abstract int haalID();
    abstract boolean uitkomstIsGeldig(Oefening oefening, double getal);
}
