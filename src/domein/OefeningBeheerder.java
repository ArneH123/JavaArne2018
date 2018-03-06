package domein;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import persistentie.OefeningMapper;

public class OefeningBeheerder {
    private OefeningMapper oefeningMapper;
    
    

    public OefeningBeheerder() {
        oefeningMapper = new OefeningMapper();
    }

    public ObservableList<Oefening> geefOefeningen() {
        return oefeningMapper.geefOefeningen();
    }

}
