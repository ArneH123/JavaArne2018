package domein;

import java.util.ArrayList;

public class Leerkracht {

	public String Naam;
	private int Wachtwoord;
        private ArrayList<Oefening> oefeningen;
        private ArrayList<BoB> boxes;

    public Leerkracht(String Naam, int Wachtwoord) {
        this.Naam = Naam;
        this.Wachtwoord = Wachtwoord;
        oefeningen = new ArrayList<Oefening>();
        boxes = new ArrayList<BoB>();
    }
        
        

	public String getNaam() {
		return this.Naam;
	}

	public void setNaam(String Naam) {
		this.Naam = Naam;
	}

	public int getWachtwoord() {
		return this.Wachtwoord;
	}

	public void setWachtwoord(int Wachtwoord) {
		this.Wachtwoord = Wachtwoord;
	}
        
        public void voegOefeningToe(Oefening oefening){
            oefeningen.add(oefening);
        }
        
        public void wijzigOpgaveOefening(int Id, String opgave){
            oefeningen.get(Id).setOpgave(opgave);
        }
        
        public void wijzigAntwoord(int Id, String antwoord){
            oefeningen.get(Id).setAntwoord(antwoord);
        }
        
        public void wijzigFeedback(int Id, Boolean feedback){
            oefeningen.get(Id).setFeedback(feedback);
        }
        
//        public void verwijderOefening(Oefening oefening){
//            oefeningen.remove(oefening.)
//        }

}