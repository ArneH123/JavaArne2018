/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.DomeinController;
import domein.MaalBewerking;
import domein.Oefening;
import domein.IGroepsBewerking;
import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javax.sql.rowset.serial.SerialBlob;

public class OefeningenSchermController extends AnchorPane {

    final FileChooser fc = new FileChooser();
    private DomeinController dc;
    private Oefening laatsteSelectie = null;
    private FilteredList<Oefening> oefeningenLijst = null;
    private FilteredList<IGroepsBewerking> groepsBewerkingLijst = null; // als we ooit moeten filteren
    
    private Stage stage;
    private enum bewerkStatus {
        AANPASBAAR, AANGEPAST, NIETAANPASBAAR, GEENSELECTIE
    }

    @FXML
    ListView<Oefening> oefeningenView;
    @FXML
    private TextField naamField;
    @FXML
    private TextArea antwoordField;
    @FXML
    private Label txtGroepsBewerking;
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
    @FXML
    private Button btnOpenOpgave;
    @FXML
    private Button btnWijzigHint;
    @FXML
    private Button btnWijzigOpgave;
    @FXML
    private Button btnOpenHint;
    @FXML
    ListView<IGroepsBewerking> iGBView;

    
    private Blob opgavePdfBinary = null;
    private Blob hintPdfBinary = null;

    public OefeningenSchermController(Stage stg)
    {
        this.stage = stg;
    }

    public Parent InitialiseerController(DomeinController ob)
    {
        this.dc = ob;
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
    
    //past de kleuren aan van de vakken
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
            antwoordField.setStyle(stijl);
            btnOpenOpgave.setStyle(stijl);
            btnOpenHint.setStyle(stijl);
            btnWijzigOpgave.setStyle(stijl);
            btnWijzigHint.setStyle(stijl);
            iGBView.setStyle(stijl);
            //opgaveField.setStyle(stijl);
            //hintField.setStyle(stijl);
        } 
        btnOpslaan.setStyle(stijl);
        
        boolean aanpasBaar = (status==bewerkStatus.AANPASBAAR || status==bewerkStatus.AANGEPAST);

        naamField.setEditable(aanpasBaar);
        antwoordField.setEditable(aanpasBaar);
        btnOpslaan.setDisable(!aanpasBaar);

        btnWijzigOpgave.setDisable(!aanpasBaar);
        btnWijzigHint.setDisable(!aanpasBaar);

        iGBView.setDisable(!aanpasBaar);

        btnOpenOpgave.setDisable(opgavePdfBinary==null);
        btnOpenHint.setDisable(hintPdfBinary==null);
        

    }
    
        //laat details van oefening zien
    private void laadOefeningDetail() 
    {
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        boolean heeftGeenSelectie = (laatsteSelectie==null);

        btnKopieer.setDisable(heeftGeenSelectie);
        
        if (heeftGeenSelectie)
        {
            naamField.setText("");
            btnOpenOpgave.setDisable(heeftGeenSelectie);
            btnWijzigOpgave.setDisable(heeftGeenSelectie);
            btnOpenHint.setDisable(heeftGeenSelectie);
            btnWijzigHint.setDisable(heeftGeenSelectie);
            antwoordField.setText("");
            
            btnWis.setDisable(true);
            
            opgavePdfBinary = null;
            hintPdfBinary = null;
            
            updateEditeerModus(bewerkStatus.GEENSELECTIE);
            return;
        }
        
        // wel selectie
        // laad de gegevens

        boolean isInGebruik = laatsteSelectie.getIsInGebruik();
        btnWis.setDisable(isInGebruik);
        
        naamField.setText(laatsteSelectie.getNaam());
        antwoordField.setText(laatsteSelectie.getAntwoord());
        
        if (laatsteSelectie!=null)
            opgavePdfBinary = laatsteSelectie.getOpgave();

        if (laatsteSelectie!=null)
            hintPdfBinary = laatsteSelectie.getHint();

        // selecteer actief
        iGBView.getSelectionModel().clearSelection();
        List<IGroepsBewerking> selectie = laatsteSelectie.getGroepsbewerking();
        for (IGroepsBewerking element : selectie)
             iGBView.getSelectionModel().select(element);

        updateEditeerModus( ( isInGebruik) ? bewerkStatus.NIETAANPASBAAR : bewerkStatus.AANPASBAAR);
        
    }

    
    @FXML
    private void detailCurrent(ActionEvent event) {
          laadOefeningDetail();
    }

    @FXML
    private void nieuweOefening(ActionEvent event) {
        
        Oefening test = new Oefening("Nieuwe oefening");
        

        test.setGroepsbewerking(null);
                
        dc.voegOefeningToe(test);
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
        
        dc.voegOefeningToe(kopie);
        laadOefeningenLijst();
        oefeningenView.getSelectionModel().selectLast();
    }

    @FXML
    private void wisOefening(ActionEvent event) {
        
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        if (laatsteSelectie==null)
            return;

        dc.wisOefening(laatsteSelectie);
        laadOefeningenLijst();
     }

    //leest de bestanden
    public static byte[] readFully(InputStream stream) throws IOException
    {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }
        
    //laad de bestanden
    public static byte[] loadFile(File sourcePath) throws IOException
    {
        InputStream inputStream = null;
        try 
        {
            inputStream = new FileInputStream(sourcePath);
            return readFully(inputStream);
        } 
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

    //laat de pdf verschijnen in een apart scherm
    private void openPDFInDefaultViewer(Blob pdf)
    {
        if (pdf==null)
            return;
        
        byte[] geheugenBuffer = null;
        try {
            
            Path windowsTempDir = Paths.get(System.getProperty("java.io.tmpdir"));
            windowsTempDir = Files.createTempDirectory(windowsTempDir, "java-");
            Path tmp = Files.createTempFile(windowsTempDir,"openen-",".pdf");

            windowsTempDir.toFile().deleteOnExit();
            tmp.toFile().deleteOnExit(); // volgorde belangrijk !
            
            geheugenBuffer = pdf.getBytes( 1, ( int ) pdf.length() );
            
            FileOutputStream out = new FileOutputStream( tmp.toFile() );
            out.write( geheugenBuffer );
            out.close();
            
            Desktop.getDesktop().open(tmp.toFile());
            
        } catch (Exception ex) {
            Logger.getLogger(OefeningenSchermController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void openOpgave(ActionEvent event)
    {
        openPDFInDefaultViewer(laatsteSelectie.getOpgave());
    }
    
    @FXML
    private void openHint(ActionEvent event)
    {
        openPDFInDefaultViewer(laatsteSelectie.getHint());
    }
    
    //laad het pdf bestand
    private Blob loadPDF()
    {
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files (*.txt)", "*.pdf");
        fc.getExtensionFilters().add(extFilter);
        
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        fc.setInitialDirectory(userDirectory);

        File f = fc.showOpenDialog(this.stage);
        
        if (f==null)
            return null;

        if(f.exists() && !f.isDirectory()) { 
            try {
                byte[] buff = loadFile(f);
                return new SerialBlob(buff);
            } catch (Exception ex) {
                Logger.getLogger(OefeningenSchermController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null; // fallback
    }

    @FXML
    private void wijzigHint(ActionEvent event)
    {
        hintPdfBinary = loadPDF();
        if (hintPdfBinary!=null)
            zetGewijzigd(btnWijzigHint);
    }
    
    @FXML
    private void wijzigOpgave(ActionEvent event)
    {
        opgavePdfBinary = loadPDF();
        if (opgavePdfBinary!=null)
            zetGewijzigd(btnWijzigOpgave);
    }

    @FXML
    private void opslaanWijzigingen(ActionEvent event) {
        
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        if (laatsteSelectie==null)
            return;

        laatsteSelectie.setNaam(naamField.getText());
        laatsteSelectie.setAntwoord(antwoordField.getText());
        laatsteSelectie.setOpgave(opgavePdfBinary);
        laatsteSelectie.setHint(hintPdfBinary);

        ObservableList<IGroepsBewerking> selectedItems =  iGBView.getSelectionModel().getSelectedItems();
        laatsteSelectie.setGroepsbewerking(selectedItems);
        
        dc.slaOefeningOp(laatsteSelectie);
        laadOefeningenLijst();
    }
    

    private void laadOefeningenLijst()
    {
        oefeningenLijst = new FilteredList<>(dc.geefOefeningenAsLijst());
        oefeningenView.setItems(oefeningenLijst);
        txtLijstZoek.setText("");
    }
    
    private void laadGroepsBewerkingen()
    {
        // vul volledig
        groepsBewerkingLijst = new FilteredList<IGroepsBewerking>(IGroepsBewerking.beschikbareBewerkingen);
        iGBView.setItems(groepsBewerkingLijst);
    }
    
    private void buildGui() {
        
        // Debug - to be removed achteraf
        if (dc==null)
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
            
            //kijken of er gewijzigd is of niet, eerste if niet, tweede if is veranderd, oude wordt door nieuwe vervangen
            //return: laatst geselecteerde naam moet overeenkomen met wijziging van naam
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
        
        //implementeert van listener, per klik methode changed uitvoeren van laadOef
        oefeningenView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Oefening>() {
            @Override
            public void changed(ObservableValue<? extends Oefening> observable, Oefening oldValue, Oefening newValue) {
                laadOefeningDetail();
            }
        });
        
        iGBView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        iGBView.setCellFactory(param -> new ListCell<IGroepsBewerking>() {
            @Override
            protected void updateItem(IGroepsBewerking item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null ) {
                    setText(null);
                } else {
                    String text = item.geefNaam();
                    setText(text);
                }
            } 
            
        });
        
        iGBView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IGroepsBewerking>() {
            @Override
            public void changed(ObservableValue<? extends IGroepsBewerking> observable, IGroepsBewerking oldValue, IGroepsBewerking newValue) {
                        zetGewijzigd(iGBView);
            }
        });
        
        //implementeert van listener, is de filter functie voor oefeningen
        txtLijstZoek.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            oefeningenLijst.setPredicate( data -> {
                return data.getNaam().toLowerCase().contains(newValue.toLowerCase());
                });
        });
       
        //roept oefeningDetailsGewijzigd aan om wijzigingen op te slaan
        naamField.textProperty().addListener(new OefeningDetailsGewijzigd(naamField));
        antwoordField.textProperty().addListener(new OefeningDetailsGewijzigd(antwoordField));
        
        laadGroepsBewerkingen();
                
        laadOefeningenLijst();
        laadOefeningDetail(); // Trigger de geen selectie procedure
    }
    
    private void zetGewijzigd(Control element)
    {
        String stijl = "-fx-border-color: #"+kleuren.get(bewerkStatus.AANGEPAST) +";"; 
        element.setStyle(stijl);
        updateEditeerModus(bewerkStatus.AANGEPAST,false);
    }
    
    public class OefeningDetailsGewijzigd implements ChangeListener
    {
        private Control source;
        public OefeningDetailsGewijzigd(Control source) {
            this.source = source;
        }

        //kleur van randen + status oefening aanpassen 
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) 
        {
            zetGewijzigd(this.source);
        }
 
  };
}
