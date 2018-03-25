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
public class MaalBewerking extends IGroepsBewerking{
    
    private double uitkomst;
    private Oefening oefening;
    private double getal;
    private String naam;
    private int id;
    
    public MaalBewerking(String oefening, double getal) {
        id = IGroepsBewerking.currectId;
        IGroepsBewerking.currectId++;

       this.naam = oefening;
       this.getal = getal;
        //this.uitkomst = oefening.getAntwoordd()*getal;
    }
     
    //@Override
    //public String geefID()
    //{
    //return this.naam; }
    @Override
    public int haalID()
    {
    return this.id; }
    
    @Override
    public String geefNaam()
    {
    return this.naam; }
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
