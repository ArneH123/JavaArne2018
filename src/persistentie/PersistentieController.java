package persistentie;

import domein.Oefening;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersistentieController {
    private GenericDaoJPA gdj;

    public PersistentieController() {
        gdj = new GenericDaoJPA();
    }

    public ObservableList<Oefening> geefOefeningenAsLijst() {
        return FXCollections.observableArrayList(gdj.findAll());
    }
    
    public Oefening geefOefeningMetId(int id){
        return gdj.findById(id);
    }
    
    public void voegOefeningToe(Oefening nieweOefening){
        gdj.insert(nieweOefening);
    }

    public void slaOefeningOp(Oefening gewijzigdeOefening){
        gdj.update(gewijzigdeOefening);
    }

    public void wisOefening(Oefening teWissenoefening){
        gdj.delete(teWissenoefening);
    }
    
}