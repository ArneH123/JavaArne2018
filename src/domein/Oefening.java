package domein;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Oefening")
public class Oefening {
    @Id
    @GeneratedValue
    private int id;
    private String naam;
    private String opgave;
    private String antwoord;
    private double antwoordd;
    private String feedback;
    private boolean isInGebruik = false;
//    @OneToMany(mappedBy="Groepsbewerking", cascade = CascadeType.PERSIST)
    private List<iGroepsBewerking> groepsbewerkingen;

    // Copy constructir
    public Oefening(Oefening copyOef) {
        //copyOef
        this.naam = copyOef.naam;
        this.opgave = copyOef.opgave;
        this.antwoord = copyOef.antwoord;
        this.feedback = copyOef.feedback;
        this.isInGebruik = copyOef.isInGebruik;
        this.groepsbewerkingen = copyOef.groepsbewerkingen;
    }
 
    public Oefening(String naam, String opgave, String antwoord, String feedback) {
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.feedback = feedback;
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
    
    public String getOpgave() {
        return opgave;
    }

    public String getAntwoord() {
        return antwoord;
    }

    public String getFeedback() {
        return feedback;
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

    public void setOpgave(String opgave) {
        this.opgave = opgave;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
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