/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Arne
 */
public class OefeningDetailSchermController extends AnchorPane { 
    @FXML
    private AnchorPane AnchorPane;
    
    private OefeningBeheerder ob;
    private int oefeningId;
    private Oefening currentOefening;
       

    public OefeningDetailSchermController(OefeningBeheerder ob, int oefeningId) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OefeningDetailScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        this.ob = ob;
        this.oefeningId = oefeningId;
        buildGui();
        
    }

    @FXML
    private Label oefeningNaam;
            
    private void buildGui() {
        oefeningNaam.setText(ob.geefOefeningenAsLijst().get(oefeningId).getNaam());
    }
    
    
}
