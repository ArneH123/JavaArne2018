package domein;

import java.util.ArrayList;
import javax.persistence.Id;

public class Oefening {
    @Id
    private int Id;
    private String opgave;
    private double antwoord;
    private Boolean feedback;
    private ArrayList<Groepsbewerkingen> groepsbewerkingen;

    public Oefening(String opgave, double antwoord, ArrayList<Groepsbewerkingen> groepsbewerkingen) {
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

    public ArrayList<Groepsbewerkingen> getGroepsbewerkingen() {
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

    public void setGroepsbewerkingen(ArrayList<Groepsbewerkingen> groepsbewerkingen) {
        this.groepsbewerkingen = groepsbewerkingen;
    }

    void setAntwoord(String antwoord) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}