package domein;

import javafx.collections.ObservableList;
import persistentie.PersistentieController;

public class DomeinController {

    private final PersistentieController pc;
    private Oefening oefening;

    public DomeinController() {
        this.pc = new PersistentieController();
        this.oefening = oefening;
    }
    
    public ObservableList<Oefening> geefOefeningenAsLijst(){
        return pc.geefOefeningenAsLijst();
    }
    
    public Oefening geefOefeningMetId(int id){
        return pc.geefOefeningMetId(id);
    }

    public boolean slaOefeningOp(Oefening gewijzigdeOefening){
        try
        {
            pc.slaOefeningOp(gewijzigdeOefening);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public boolean wisOefening(Oefening teWissenOefening){
        try
        {
            pc.wisOefening(teWissenOefening);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public void maakNieuweOefening(){
        pc.voegOefeningToe(new Oefening("Nieuwe Oefening"));
    }    
    
    public boolean voegOefeningToe(Oefening nieuweOefening){
        try
        {
            pc.voegOefeningToe(nieuweOefening);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

}