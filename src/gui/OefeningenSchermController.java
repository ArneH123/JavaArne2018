/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.io.IOException;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Arne
 */
public class OefeningenSchermController extends AnchorPane {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    ListView<Oefening> oefeningenView;
    
    @FXML
    private TextField naamField;
    
    @FXML
    private TextArea opgaveField;
    
    @FXML
    private TextArea antwoordField;

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
//        OefeningDetailSchermController odc = new OefeningDetailSchermController(ob, oefeningenView.getSelectionModel().getSelectedIndex());
//        Stage stage1 = new Stage();
//        Scene scene1 = new Scene(odc);
//        stage1.setScene(scene1);
//        stage1.show();
          Oefening current = ob.geefOefeningMetId(oefeningenView.getSelectionModel().getSelectedItem().getId());
          System.out.println(current.getNaam());
          naamField.setText(current.getNaam());
          opgaveField.setText(current.getOpgave());
          antwoordField.setText(current.getAntwoord());
           
    }

    private void buildGui() {
        ObservableList<Oefening> data = ob.geefOefeningenAsLijst();
        ObservableList<String> names = FXCollections.observableArrayList();
        for (Oefening o : data) {
            names.add(o.getNaam());
        }
        Collections.sort(names);
        oefeningenView.setItems(data);
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
    }
}
