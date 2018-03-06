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
    
        
}
