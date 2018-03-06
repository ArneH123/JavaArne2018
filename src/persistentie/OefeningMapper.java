/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Oefening;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Arne
 */
public class OefeningMapper {

    ObservableList<Oefening> oefeningen;

    public OefeningMapper() {
        maakOefeningen();
    }

    public ObservableList<Oefening> geefOefeningen() {
        ObservableList<Oefening> oefeningenfx = FXCollections.observableArrayList();
        oefeningenfx = oefeningen;
        return oefeningenfx;
    }
    
    public void maakOefeningen(){
        Oefening o1 = new Oefening("Oefening 1");
        Oefening o2 = new Oefening("Oefening 2");
        Oefening o3 = new Oefening("Oefening 3");
        oefeningen.add(o1);
        oefeningen.add(o3);
        oefeningen.add(o2);
    }
}
