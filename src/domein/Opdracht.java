
package domein;

public class Opdracht {

    final int nummerOpdracht;
	Oefening oefening;
	Actie actie;
	Toegangscode toegangscode;

    public Opdracht(Oefening oefening, Actie actie, Toegangscode toegangscode, int nummerOpdracht)
    {
        this.oefening = oefening;
        this.actie = actie;
        this.toegangscode = toegangscode;
        this.nummerOpdracht = nummerOpdracht;
    }

    public int getNummerOpdracht()
    {
        return nummerOpdracht;
    }

	public Oefening getOefening() {
		return this.oefening;
	}

	public void setOefening(Oefening oefening) {
		this.oefening = oefening;
	}

	public Actie getActie() {
		return this.actie;
	}

	public void setActie(Actie actie) {
		this.actie = actie;
	}

	public Toegangscode getToegangscode() {
		return this.toegangscode;
	}

	public void setToegangscode(Toegangscode toegangscode) {
		this.toegangscode = toegangscode;
	}
    
    
}
