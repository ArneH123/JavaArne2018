package domein;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private double antwoordd;
    private boolean isInGebruik = false;
//    @OneToMany(mappedBy="Groepsbewerking", cascade = CascadeType.PERSIST)
    private byte[] opgavePDFBlob;
    private byte[] hintPDFBlob;
    private List<iGroepsBewerking> groepsbewerkingen;

    // Copy constructir
    public Oefening(Oefening copyOef) {
        //copyOef
        this.naam = copyOef.naam;
        this.antwoord = copyOef.antwoord;
        this.opgavePDFBlob = copyOef.opgavePDFBlob;
        this.hintPDFBlob = copyOef.hintPDFBlob;
        this.isInGebruik = copyOef.isInGebruik;
        this.groepsbewerkingen = copyOef.groepsbewerkingen;
    }
 
    public Oefening(String naam, String opgave, String antwoord, String feedback) {
        this.naam = naam;
        this.antwoord = antwoord;
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
    
    /*
    public String getOpgave() {
        return opgave;
    }
    */

    public String getAntwoord() {
        return antwoord;
    }
    
    public boolean getIsInGebruik() {
        return isInGebruik;
    }

    public List<iGroepsBewerking> getGroepsbewerking() {
        return groepsbewerkingen;
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

    public void setIsInGebruik(boolean isInGebruik) {
        this.isInGebruik = isInGebruik;
    }

    public void setGroepsbewerking(List<iGroepsBewerking> groepsbewerkingen) {
        this.groepsbewerkingen = groepsbewerkingen;
    }    

    public String toonOverzicht() {
        return null;
    }

    public double getAntwoordd() {
        return antwoordd;
    }
    
    
}