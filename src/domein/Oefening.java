package domein;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;

public class Oefening {
    @Id
    private int Id;
    private int naam;
    private String opgave;
    private double antwoord;
    private Boolean feedback;
    private List<Groepsbewerking> groepsbewerkingen;

    public Oefening(String opgave, double antwoord, ArrayList<Groepsbewerking> groepsbewerkingen) {
        this.opgave = opgave;
        this.antwoord = antwoord;
        this.groepsbewerkingen = groepsbewerkingen;
    }

    public String getOpgave() {
        return opgave;
    }

    public double getAntwoord() {
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

    public void setAntwoord(double antwoord) {
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