/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domein.Oefening;
import domein.OefeningBeheerder;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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
import javax.sql.rowset.serial.SerialBlob;

public class OefeningenSchermController extends AnchorPane {

    final FileChooser fc = new FileChooser();
    private OefeningBeheerder ob;
    private Oefening laatsteSelectie = null;
    private FilteredList<Oefening> filteredList = null;
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
    
    private Blob opgavePdfBinary;
    private Blob hintPdfBinary;

    public OefeningenSchermController(Stage stg)
    {
        this.stage = stg;
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
            //opgaveField.setStyle(stijl);
            antwoordField.setStyle(stijl);
            //hintField.setStyle(stijl);
        } 
        btnOpslaan.setStyle(stijl);
        
        boolean aanpasBaar = (status==bewerkStatus.AANPASBAAR || status==bewerkStatus.AANGEPAST);

        naamField.setEditable(aanpasBaar);
        antwoordField.setEditable(aanpasBaar);
        btnOpslaan.setDisable(!aanpasBaar);

        btnWijzigHint.setDisable(!aanpasBaar);
        btnWijzigOpgave.setDisable(!aanpasBaar);
        btnOpenHint.setDisable(!aanpasBaar);
        
        boolean foundOpgave = false;
        if (laatsteSelectie!=null)
            opgavePdfBinary = laatsteSelectie.getOpgave();

        boolean foundHint = false;
        if (laatsteSelectie!=null)
            hintPdfBinary = laatsteSelectie.getHint();
        
        foundOpgave = (opgavePdfBinary!=null);
            btnOpenOpgave.setDisable(!foundOpgave);

        foundHint = (hintPdfBinary!=null);
            btnOpenHint.setDisable(!foundHint);
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

     @FXML
    private void wijzigHint(ActionEvent event)
    {
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files (*.txt)", "*.pdf");
        fc.getExtensionFilters().add(extFilter);
        
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        fc.setInitialDirectory(userDirectory);

        File f = fc.showOpenDialog(this.stage);
        
        if (f==null)
            return;

        if(f.exists() && !f.isDirectory()) { 
            try {
                byte[] buff = loadFile(f);
                hintPdfBinary = new SerialBlob(buff);
            } catch (Exception ex) {
                Logger.getLogger(OefeningenSchermController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void wijzigOpgave(ActionEvent event)
    {
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF Files (*.txt)", "*.pdf");
        fc.getExtensionFilters().add(extFilter);
        
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        fc.setInitialDirectory(userDirectory);

        File f = fc.showOpenDialog(this.stage);
        
        if (f==null)
            return;

        if(f.exists() && !f.isDirectory()) { 
            try {
                byte[] buff = loadFile(f);
                opgavePdfBinary = new SerialBlob(buff);
            } catch (Exception ex) {
                Logger.getLogger(OefeningenSchermController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void opslaanWijzigingen(ActionEvent event) {
        
        laatsteSelectie = oefeningenView.getSelectionModel().getSelectedItem();
        if (laatsteSelectie==null)
            return;

        laatsteSelectie.setNaam(naamField.getText());
        //laatsteSelectie.setOpgave(btnWijzigOpgave.);
        //laatsteSelectie.setOpgave(opgaveField.getText());
        laatsteSelectie.setAntwoord(antwoordField.getText());
        //laatsteSelectie.setFeedback(hintField.getText());
        laatsteSelectie.setOpgave(opgavePdfBinary);
        laatsteSelectie.setHint(hintPdfBinary);
        ob.slaOefeningOp(laatsteSelectie);
        laadOefeningenLijst();
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
            btnOpenHint.setDisable(heeftGeenSelectie);
            //btnWijzigOpgave.setDisable(heeftGeenSelectie);
            //btnOpenFeedback.setDisable(heeftGeenSelectie);
            //btnWijzigFeedback.setDisable(heeftGeenSelectie);
            //opgaveField.setText("");
            antwoordField.setText("");
            //hintField.setText("");
            updateEditeerModus(bewerkStatus.GEENSELECTIE);
            btnWis.setDisable(true);
            
            return;
        }
        
        // wel selectie
        // laad de gegevens

        boolean isInGebruik = laatsteSelectie.getIsInGebruik();
        btnWis.setDisable(isInGebruik);
        
        naamField.setText(laatsteSelectie.getNaam());
        //opgaveField.setText(laatsteSelectie.getOpgave());
        antwoordField.setText(laatsteSelectie.getAntwoord());
        //hintField.setText(laatsteSelectie.getFeedback());
        
        
        //if (opgavePdfBinary==null)
        //btnOpenOpgave.setDisable(true);

        updateEditeerModus( ( isInGebruik) ? bewerkStatus.NIETAANPASBAAR : bewerkStatus.AANPASBAAR);
        
    }

    private void laadOefeningenLijst()
    {
        filteredList = new FilteredList<>(ob.geefOefeningenAsLijst());
        oefeningenView.setItems(filteredList);
        txtLijstZoek.setText("");
    }
    
    private void openPdf(){
        /*
        JButton btnOpenOpgave = new JButton("Open");
        JButton btnOpenFeedback = new JButton("Open");
        /*
        btnOpenOpgave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    Desktop.getDesktop().open(new java.io.File("Oefening_Domino.pdf"));
                }
                catch(IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        });  */
        /*
        btnOpenFeedback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    Desktop.getDesktop().open(new java.io.File("Oefening_Domino.pdf"));
                }
                catch(IOException ex) 
                {
                    ex.printStackTrace();
                }
            }
        });*/
    }
    
    
    private void buildGui() {
        
        // Debug - to be removed achteraf
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
        
        //implementeert van listener, is de filter functie voor oefeningen
        txtLijstZoek.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredList.setPredicate( data -> {
                return data.getNaam().toLowerCase().contains(newValue.toLowerCase());
                });
        });
        
        //roept oefeningDetailsGewijzigd aan om wijzigingen op te slaan
        naamField.textProperty().addListener(new OefeningDetailsGewijzigd(naamField));
        //opgaveField.textProperty().addListener(new OefeningDetailsGewijzigd(opgaveField));
        antwoordField.textProperty().addListener(new OefeningDetailsGewijzigd(antwoordField));
        //hintField.textProperty().addListener(new OefeningDetailsGewijzigd(hintField));
            
        laadOefeningenLijst();
        laadOefeningDetail(); // Trigger de geen selectie procedure
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
            String stijl = "-fx-border-color: #"+kleuren.get(bewerkStatus.AANGEPAST) +";"; 
            source.setStyle(stijl);
            updateEditeerModus(bewerkStatus.AANGEPAST,false);
        }

  };
}
