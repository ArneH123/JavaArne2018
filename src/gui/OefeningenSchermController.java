/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


/**
 * FXML Controller class
 *
 * @author Arne
 */
public class OefeningenSchermController extends AnchorPane {
    private OefeningBeheerder ob;
    private Oefening laatsteSelectie = null;
    private FilteredList<Oefening> filteredList = null;
    
    private enum bewerkStatus {
        AANPASBAAR, AANGEPAST, NIETAANPASBAAR, GEENSELECTIE
    }

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
    private Button btnNieuw;
    @FXML
    private Button btnKopieer;
    @FXML
    private Button btnWis;
    @FXML
    private Label lblInfo;
    @FXML
    private Button btnOpslaan;

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
        // vergeet de @FXML niet > ander wordt deze routine niet uitgevoerd !
        // Soms wordt deze gewist bij auto generatie
        buildGui();
    }

    private static Map<bewerkStatus, String> kleuren = new HashMap<bewerkStatus, String>()
    {{
                    //put(bewerkStatus.AANPASBAAR,"006400");
                    put(bewerkStatus.AANPASBAAR,"008200");
                    put(bewerkStatus.AANGEPAST,"FFA500");
                    put(bewerkStatus.NIETAANPASBAAR,"A52A2A");
                    put(bewerkStatus.GEENSELECTIE,"999999");
    }};

    private static Map<bewerkStatus, String> uitleg = new HashMap<bewerkStatus, String>()
    {{
                    put(bewerkStatus.AANPASBAAR,"Deze oefening is aanpasbaar. Pas de velden aan en druk op opslaan om te wijzigingen te bewaren.");
                    put(bewerkStatus.AANGEPAST,"U ben bezig met aanpassen van de oefeningen. Uw wijzigingen zijn nog niet opgeslaan !");
                    put(bewerkStatus.NIETAANPASBAAR,"U kan deze oefening niet wijzigen! \n Deze oefening is gekoppeld aan een BoB die in een openstaande of actieve sessie zit.");
                    put(bewerkStatus.GEENSELECTIE,"(geen oefening geselecteerd)");
    }};

    private void updateEditeerModus(bewerkStatus status)
    {
        updateEditeerModus(status,true);
    }
    
    private void updateEditeerModus(bewerkStatus status, boolean updateAlleStijlen)
    {
        // later aan te vullen door achtergroundkleur ? (of toch gewoon setDisable gebruiken (lelkijk))
        // Background achter = new Background(new BackgroundFill(Color.GREY, new CornerRadii(10), null));
        // naamField.setBackground(achter);
        
        // Direct gebruik van hasmap > als een status ontbreekt in de hashmap gaat er een fout komen > moeten we dit opvangen met een defualt ??        
        String stijl = "-fx-border-color: #"+kleuren.get(status) +";"; 
        lblInfo.setStyle(stijl + " -fx-text-fill: #"+kleuren.get(status) +";");
        lblInfo.setText(uitleg.get(status));
        
        if (updateAlleStijlen)
        {
            naamField.setStyle(stijl);
            opgaveField.setStyle(stijl);
            antwoordField.setStyle(stijl);
            hintField.setStyle(stijl);
            lstGroepsBewerkingen.setStyle(stijl);
        } 
        btnOpslaan.setStyle(stijl);
        
        boolean aanpasBaar = (status==bewerkStatus.AANPASBAAR || status==bewerkStatus.AANGEPAST);

        naamField.setEditable(aanpasBaar);
        opgaveField.setEditable(aanpasBaar);
        antwoordField.setEditable(aanpasBaar);
        hintField.setEditable(aanpasBaar);
        btnOpslaan.setDisable(!aanpasBaar);
        
    }
    @FXML
    private void detailCurrent(ActionEvent event) {
          laadOefeningDetail();
    }

    @FXML
    private void nieuweOefening(ActionEvent event) {
        ob.voegOefeningToe(new Oefening("Nieuwe oefening"));
        laadOefeningenLijst();
        oefeningenView.getSelectionModel().selectLast();

    }

    @FXML
    private void kopieerOefening(ActionEvent event) {
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        if (laatsteSelectie==null)
            return;

        Oefening kopie = new Oefening(laatsteSelectie);
        kopie.setIsInGebruik(false); // een kopie kan nog nooit in gebruik zijn bij aanmaak
        
        ob.voegOefeningToe(kopie);
        laadOefeningenLijst();
        oefeningenView.getSelectionModel().selectLast();
    }

    @FXML
    private void wisOefening(ActionEvent event) {
        
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        if (laatsteSelectie==null)
            return;

        ob.wisOefening(laatsteSelectie);
        laadOefeningenLijst();
     }

    @FXML
    private void opslaanWijzigingen(ActionEvent event) {
        
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        if (laatsteSelectie==null)
            return;

        laatsteSelectie.setNaam(naamField.getText());
        laatsteSelectie.setOpgave(opgaveField.getText());
        laatsteSelectie.setAntwoord(antwoordField.getText());
        laatsteSelectie.setFeedback(hintField.getText());
        
        ob.slaOefeningOp(laatsteSelectie);
        laadOefeningenLijst();
    }
     
    private void laadOefeningDetail() 
    {
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        boolean heeftGeenSelectie = (laatsteSelectie==null);

        btnKopieer.setDisable(heeftGeenSelectie);
        
        if (heeftGeenSelectie)
        {
            naamField.setText("");
            opgaveField.setText("");
            antwoordField.setText("");
            hintField.setText("");
            updateEditeerModus(bewerkStatus.GEENSELECTIE);
            btnWis.setDisable(true);
            
            return;
        }
        
        // wel selectie
        // laad de gegevens

        boolean isInGebruik = laatsteSelectie.getIsInGebruik();
        btnWis.setDisable(isInGebruik);
        
        naamField.setText(laatsteSelectie.getNaam());
        opgaveField.setText(laatsteSelectie.getOpgave());
        antwoordField.setText(laatsteSelectie.getAntwoord());
        hintField.setText(laatsteSelectie.getFeedback());

        updateEditeerModus( ( isInGebruik) ? bewerkStatus.NIETAANPASBAAR : bewerkStatus.AANPASBAAR);
        
    }

    private void laadOefeningenLijst()
    {
        filteredList = new FilteredList<>(ob.geefOefeningenAsLijst());
        oefeningenView.setItems(filteredList);
        txtLijstZoek.setText("");
    }
    
    private void buildGui() {
        
        // Debug - to be removed avhteraf
        if (ob==null)
            System.out.print("OB niet correct ingeladen");
        if (oefeningenView==null)
            System.out.print("FXML niet correct ingeladen");

        
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
            @Override
            protected boolean isItemChanged(Oefening oldItem, Oefening newItem) {
                
                // Voor de listview is een compare op naam genoeg
                if (newItem==null && oldItem==null)
                    return false;
                
                if (laatsteSelectie==null)
                    return super.isItemChanged(oldItem, newItem);
                
                return laatsteSelectie.getNaam().equals(newItem.getNaam());
            }
            
        });
        
        oefeningenView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Oefening>() {
            @Override
            public void changed(ObservableValue<? extends Oefening> observable, Oefening oldValue, Oefening newValue) {
                laadOefeningDetail();
            }
        });
        
        txtLijstZoek.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredList.setPredicate( data -> {
                return data.getNaam().toLowerCase().contains(newValue.toLowerCase());
                });
        });
        
        naamField.textProperty().addListener(new OefeningDetailsGewijzigd(naamField));
        opgaveField.textProperty().addListener(new OefeningDetailsGewijzigd(opgaveField));
        antwoordField.textProperty().addListener(new OefeningDetailsGewijzigd(antwoordField));
        hintField.textProperty().addListener(new OefeningDetailsGewijzigd(hintField));
            
        laadOefeningenLijst();
        laadOefeningDetail(); // Trigger de geen selectie procedure
    }
    
    public class OefeningDetailsGewijzigd implements ChangeListener
   {
        private Control source;
        public OefeningDetailsGewijzigd(Control source) {
            this.source = source;
        }

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) 
        {
            String stijl = "-fx-border-color: #"+kleuren.get(bewerkStatus.AANGEPAST) +";"; 
            source.setStyle(stijl);
            updateEditeerModus(bewerkStatus.AANGEPAST,false);
        }

  };
}