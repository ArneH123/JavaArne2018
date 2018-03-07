package persistentie;

import domein.Oefening;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersistentieController {
    private OefeningMapper oefeningMapper;

    public PersistentieController() {
        oefeningMapper = new OefeningMapper();
        DataInitializer.run();
    }

    public ObservableList<Oefening> geefOefeningenAsLijst() {
        return FXCollections.observableArrayList(oefeningMapper.findAll());
    }
    
    public Oefening geefOefeningMetId(int id){
        return oefeningMapper.findById(id);
    }
    
}