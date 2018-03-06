/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Arne
 */
public class OefeningenSchermController extends AnchorPane {

    @FXML
    private AnchorPane AnchorPane;
    
    @FXML
    ListView<String> oefeningenView;
    
    private OefeningBeheerder ob;

    public OefeningenSchermController(OefeningBeheerder ob) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OefeningenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.ob = ob;
        buildGui();
    }

    @FXML
    private Button btnDetail;

    @FXML
    private void detailCurrent(ActionEvent event) {
        
    }

    private void buildGui() {
        ObservableList<Oefening> data = ob.geefOefeningenAsLijst();
        ObservableList<String> names = FXCollections.observableArrayList();
        for(Oefening o : data){
            names.add(o.getNaam());
        }
        names.add("Oefening 1");
//        names.add("Oefening 2");
//        names.add("Oefening 3");
//        names.add("Oefening 4");
//        names.add("Oefening 5");
//        names.add("Oefening 6");
//        names.add("Oefening 7");
//        names.add("Oefening 8");
//        names.add("Oefening 9");
//        names.add("Oefening 10");
//        names.add("Oefening 11");
//        names.add("Oefening 12");
        oefeningenView.setItems(names);
    }
}
