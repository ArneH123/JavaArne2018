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
    private Boolean feedback;
//    @OneToMany(mappedBy="Groepsbewerking", cascade = CascadeType.PERSIST)
    private List<Groepsbewerking> groepsbewerkingen;

    public Oefening(String naam,String opgave, String antwoord, ArrayList<Groepsbewerking> groepsbewerkingen) {
        this.naam = naam;
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.groepsbewerkingen = groepsbewerkingen;
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

    public Boolean getFeedback() {
        return feedback;
    }

    public List<Groepsbewerking> getGroepsbewerking() {
        return groepsbewerkingen;
    }

    public void setOpgave(String opgave) {
        this.opgave = opgave;
    }

    public void setAntwoord(String antwoord) {
        this.antwoord = antwoord;
    }

    public void setFeedback(Boolean feedback) {
        this.feedback = feedback;
    }

    public void setGroepsbewerking(List<Groepsbewerking> groepsbewerkingen) {
        this.groepsbewerkingen = groepsbewerkingen;
    }
    
    public String toonOverzicht() {
        return null;
    }
}