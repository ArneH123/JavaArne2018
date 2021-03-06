package domein;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.sql.rowset.serial.SerialBlob;

@Entity
@Table(name = "Oefening")
public class Oefening {
    @Id
    @GeneratedValue
    private int id;
    private String naam;
    private String antwoord;
    private double antwoordDouble;
    private boolean isInGebruik = false;

    private byte[] opgavePDFBlob;
    private byte[] hintPDFBlob;
    
    @ElementCollection 
    private List<Integer> groepsbewerkingen = FXCollections.observableArrayList();

    // Copy constructor
    public Oefening(Oefening copyOef) {
        //copyOef
        this.naam = copyOef.naam;
        this.antwoord = copyOef.antwoord;
        this.opgavePDFBlob = copyOef.opgavePDFBlob;
        this.hintPDFBlob = copyOef.hintPDFBlob;
        this.isInGebruik = copyOef.isInGebruik;
        this.groepsbewerkingen = copyOef.groepsbewerkingen;
    }
 
    public Oefening(String naam,  String antwoord, boolean isInGebruik ) {
        this.naam = naam;
        this.antwoord = antwoord;
        this.isInGebruik = isInGebruik;
    }
 
    public Oefening(String naam) {
        this.naam = naam;
    }

    public Oefening() {
    }

    public int getId() {
        return id;
    }
    
    public String getNaam() {
        return naam;
    }
    
    public String getAntwoord() {
        return antwoord;
    }
    
    public boolean getIsInGebruik() {
        return isInGebruik;
    }
    
    public double getAntwoordd() {
        return antwoordDouble;
    }

    public List<IGroepsBewerking> getGroepsbewerking() {
       ObservableList<IGroepsBewerking> ret = FXCollections.observableArrayList();
       
         for (Integer element : groepsbewerkingen)
             ret.add(IGroepsBewerking.beschikbareBewerkingen.get(element));
         
         return ret;
    }
    
    public Blob getOpgave() {
        try {
            if (this.opgavePDFBlob==null)
                return null;
            
            Blob opgavePdfBinary = new SerialBlob(this.opgavePDFBlob);
            return opgavePdfBinary;
        } catch (SQLException ex) {
            Logger.getLogger(Oefening.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Blob getHint() {
        try {
            if (this.hintPDFBlob==null)
                return null;
            
            Blob hintPdfBinary = new SerialBlob(this.hintPDFBlob);
            return hintPdfBinary;
        } catch (SQLException ex) {
            Logger.getLogger(Oefening.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    public void setOpgave(Blob opgavePDF) {
        
        try {
            if (opgavePDF==null)
                return;
            
            int blobLength = (int) opgavePDF.length();  
            this.opgavePDFBlob = opgavePDF.getBytes(1, blobLength);
        } catch (SQLException ex) {
            Logger.getLogger(Oefening.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setHint(Blob hintPDF) {
        
        try {
            if (hintPDF==null)
                return;
            
            int blobLength = (int) hintPDF.length();  
            this.hintPDFBlob = hintPDF.getBytes(1, blobLength);
        } catch (SQLException ex) {
            Logger.getLogger(Oefening.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setIsInGebruik(boolean isInGebruik) {
        this.isInGebruik = isInGebruik;
    }

    public void setGroepsbewerking(List<IGroepsBewerking> groepsbewerkingen) {
        if (groepsbewerkingen==null)
            return;
        
        this.groepsbewerkingen.clear();
        for (IGroepsBewerking element : groepsbewerkingen)
            this.groepsbewerkingen.add(element.haalID());
    }
}