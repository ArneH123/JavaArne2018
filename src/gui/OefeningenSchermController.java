/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.io.IOException;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Arne
 */
public class OefeningenSchermController extends AnchorPane {

    @FXML
    ListView<Oefening> oefeningenView;
    @FXML
    private TextField naamField;
    @FXML
    private TextArea opgaveField;
    @FXML
    private TextArea antwoordField;
    @FXML
    private TextArea hintField;
    @FXML
    private Label txtGroepsBewerking;
    @FXML
    private ListView<?> lstGroepsBewerkingen;
    @FXML
    private TextField txtLijstZoek;
    @FXML
    private ListView<?> txtListZoek;

    private OefeningBeheerder ob;

    public OefeningenSchermController() {
    }

    public Parent InitialiseerController(OefeningBeheerder ob)
    {
        this.ob = ob;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OefeningenScherm.fxml"));
        loader.setController(this);
        try {
            return loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @FXML
    void initialize(){
        buildGui();
    }

    @FXML
    private Button btnDetail;

    private void activeerEditMode(boolean aanUit)
    {
        // later aan te vullen door achtergroundkleur ? (of toch gewoon setDisable gebruiken (lelkijk))
        // Background achter = new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), null));
        // naamField.setBackground(achter);
        
        String stijl = (aanUit) ? "-fx-border-color: #006400;" : "-fx-border-color: #A52A2A;";
        naamField.setStyle(stijl);
        opgaveField.setStyle(stijl);
        antwoordField.setStyle(stijl);
        hintField.setStyle(stijl);
        lstGroepsBewerkingen.setStyle(stijl);

        naamField.setEditable(aanUit);
        opgaveField.setEditable(aanUit);
        antwoordField.setEditable(aanUit);
        hintField.setEditable(aanUit);
        
        lstGroepsBewerkingen.setEditable(aanUit);
    }
    @FXML
    private void detailCurrent(ActionEvent event) {
          Oefening current = ob.geefOefeningMetId(oefeningenView.getSelectionModel().getSelectedItem().getId());
          naamField.setText(current.getNaam());
          opgaveField.setText(current.getOpgave());
          antwoordField.setText(current.getAntwoord());
          hintField.setText(current.getFeedback());
    }

    private void buildGui() {
        
        // Debug
        if (ob==null)
            System.out.print("OB niet correct ingeladen");
        if (oefeningenView==null)
            System.out.print("FXML niet correct ingeladen");

        ObservableList<Oefening> oefeningData = ob.geefOefeningenAsLijst();
        FilteredList<Oefening> filteredList= new FilteredList<>(oefeningData);
        oefeningenView.setItems(filteredList);
        
        oefeningenView.setCellFactory(param -> new ListCell<Oefening>() {
            @Override
            protected void updateItem(Oefening item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNaam() == null) {
                    setText(null);
                } else {
                    String text = item.getNaam();
                    setText(text);
                }
            }
        });
        
        txtLijstZoek.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredList.setPredicate( data -> {
                return data.getNaam().contains(newValue);
                });
        });
 
        activeerEditMode(false);
    }
}