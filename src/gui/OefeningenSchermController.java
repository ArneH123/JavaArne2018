/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ruben
 */
public class OefeningenSchermController implements Initializable {

    public OefeningenSchermController() {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        

        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    

    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnAanpassen;
    
    @FXML
    private void oefeningToevoegen (ActionEvent event) {
        
    }
    
    @FXML
    private void oefeningAanpassen (ActionEvent event) {
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
