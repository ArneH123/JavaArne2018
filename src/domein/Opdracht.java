
package domein;

public class Opdracht {

    Oefening oefening;
    Actie actie;
    Toegangscode toegangscode;
    final int nummerOpdracht;

    public Opdracht(Oefening oefening, Actie actie, Toegangscode toegangscode, int nummerOpdracht)
    {
        this.oefening = oefening;
        this.actie = actie;
        this.toegangscode = toegangscode;
        this.nummerOpdracht = nummerOpdracht;
    }

    public Oefening getOefening()
    {
        return oefening;
    }

    public void setOefening(Oefening oefening)
    {
        this.oefening = oefening;
    }

    public Actie getActie()
    {
        return actie;
    }

    public void setActie(Actie actie)
    {
        this.actie = actie;
    }

    public Toegangscode getToegangscode()
    {
        return toegangscode;
    }

    public void setToegangscode(Toegangscode toegangscode)
    {
        this.toegangscode = toegangscode;
    }

    public int getNummerOpdracht()
    {
        return nummerOpdracht;
    }
    
    
}
