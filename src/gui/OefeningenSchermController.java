/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Arne
 */
public class OefeningenSchermController extends AnchorPane {

    @FXML
    private AnchorPane AnchorPane;
    
    ListView<String> oefeningenView;
    
    private OefeningBeheerder ob;
    @FXML
    private TextField txtZoekOefeningen;
    @FXML
    private ListView<?> lstOefeningen;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtOpgave;
    @FXML
    private TextField txtFeedback;
    @FXML
    private TextField txtAntwoord;
    @FXML
    private ListView<?> lstGpb;

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
        ArrayList<String> names = new ArrayList<String>(); //FXCollections.observableArrayList();
        for(Oefening o : data){
            names.add(o.getNaam());
        }
        
        Collections.sort(names);
        lstOefeningen.setItems(
                    FXCollections.observableArrayList(names)
                );
        //oefeningenView.setItems(names);
    }
}
