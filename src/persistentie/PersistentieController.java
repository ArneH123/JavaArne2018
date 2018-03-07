package persistentie;

import domein.Oefening;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersistentieController {
    private OefeningMapper oefeningMapper;

    public PersistentieController() {
        oefeningMapper = new OefeningMapper();
    }

    public ObservableList<Oefening> geefOefeningenAsLijst() {
        return FXCollections.observableArrayList(oefeningMapper.findAll());
    }
    
    public Oefening geefOefeningMetId(int id){
        return oefeningMapper.findById(id);
    }
    
    public void voegOefeningToe(Oefening nieweOefening){
        oefeningMapper.insert(nieweOefening);
    }

    public void slaOefeningOp(Oefening gewijzigdeOefening){
        oefeningMapper.update(gewijzigdeOefening);
    }

    public void wisOefening(Oefening teWissenoefening){
        oefeningMapper.delete(teWissenoefening);
    }
    
}