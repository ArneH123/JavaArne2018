/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Arne
 */
public class MaalBewerking implements iGroepsBewerking{
    
    private double uitkomst;
    private Oefening oefening;
    private double getal;

    public MaalBewerking(Oefening oefening, double getal) {
        this.oefening = oefening;
        this.getal = getal;
        this.uitkomst = oefening.getAntwoordd()*getal;
    }
    
    

    @Override
    public String geefBewerkingToString() {
        return String.format("Doe je uitkomst maal %d", getal);
    }

    @Override
    public boolean uitkomstIsGeldig(Oefening oefening, double getal) {
        if(oefening.getAntwoordd()*getal == oefening.getAntwoordd()){
            return true;
        }
        return false;
    }
    
}
