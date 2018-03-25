/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class IGroepsBewerking {

    private Oefening oefening;
    public static ObservableList<IGroepsBewerking> beschikbareBewerkingen = FXCollections.observableArrayList();
    public static int currectId;
    static {
           beschikbareBewerkingen.add(new Bewerking("Doe maal 10", 12));
           beschikbareBewerkingen.add(new Bewerking("Doe maal 5", 5));
           beschikbareBewerkingen.add(new Bewerking("Doe maal 3", 3));
           beschikbareBewerkingen.add(new Bewerking("Doe gedeeld door 2", 2));
           beschikbareBewerkingen.add(new Bewerking("Doe gedeeld door 4", 4));
           beschikbareBewerkingen.add(new Bewerking("Doe gedeeld door 10", 10));
           beschikbareBewerkingen.add(new Bewerking("Doe plus 12", 10));
           beschikbareBewerkingen.add(new Bewerking("Doe plus 28", 28));
           beschikbareBewerkingen.add(new Bewerking("Doe plus 28", 16));
           beschikbareBewerkingen.add(new Bewerking("Doe min 9", 9));
           beschikbareBewerkingen.add(new Bewerking("Doe min 6", 6));
           beschikbareBewerkingen.add(new Bewerking("Doe min 11", 11));
    }
    
    abstract String geefBewerkingToString();
    public abstract String geefNaam();
    public abstract int haalID();
    abstract boolean uitkomstIsGeldig(Oefening oefening, double getal);
}
