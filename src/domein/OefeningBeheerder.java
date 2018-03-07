package domein;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import persistentie.OefeningMapper;
import persistentie.PersistentieController;

public class OefeningBeheerder {
    private final PersistentieController pc;

    public OefeningBeheerder() {
        this.pc = new PersistentieController();
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
