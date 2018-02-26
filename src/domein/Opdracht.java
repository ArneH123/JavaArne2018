
package domein;

public class Opdracht {

    Oefening attribute;
    Actie attribute2;
    Toegangscode attribute3;
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

    public Oefening getAttribute()
    {
        return attribute;
    }

    public void setAttribute(Oefening oefening)
    {
        this.attribute = oefening;
    }

    public Actie getAttribute2()
    {
        return attribute2;
    }

    public void setAttribute2(Actie actie)
    {
        this.attribute2 = actie;
    }

    public Toegangscode getAttribute3()
    {
        return attribute3;
    }

    public void setAttribute3(Toegangscode toegangscode)
    {
        this.attribute3 = toegangscode;
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
