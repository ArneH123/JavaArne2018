package domein;

import java.util.*;

public class Moderator {

	public String naam;
	private int wachtwoord;
	private Collection<BoB> boxes;
        private List<Oefening> oefeningen;

	public String getNaam() {
            return this.naam;
	}

	public void setNaam(String Naam) {
            this.naam = Naam;
	}

	public int getWachtwoord() {
            return this.wachtwoord;
	}

	public void setWachtwoord(int Wachtwoord) {
            this.wachtwoord = Wachtwoord;
	}

	public Moderator(String naam, int wachtwoord) {
            this.naam = naam;
            this.wachtwoord = wachtwoord;
	}

	public void voegOefeningToe(Oefening oefening) {
            oefeningen.add(oefening);
	}

	public void wijzigOpgaveOefening(int id, String opgave) {
            oefeningen.get(id).setOpgave(opgave);
	}

	public void wijzigAntwoord(int id, String antwoord) {
            oefeningen.get(id).setAntwoord(antwoord);
	}

	public void wijzigFeedback(int id, String feedback) {
            oefeningen.get(id).setFeedback(feedback);
	}

}