/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "Groepsbewerking")
public abstract class IGroepsBewerking {
    @Id
    private int id;
    
    @ManyToOne
    @JoinTable
    Oefening o;
    
    public static ObservableList<IGroepsBewerking> beschikbareBewerkingen = FXCollections.observableArrayList();
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
