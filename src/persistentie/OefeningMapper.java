/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Oefening;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.persistence.Query;
import static persistentie.DataInitializer.em;

/**
 *
 * @author Arne
 */
public class OefeningMapper implements GenericDao<Oefening>{

    ObservableList<Oefening> oefeningen;

    public OefeningMapper() {
    }

    public ObservableList<Oefening> geefOefeningen() {
        ObservableList<Oefening> oefeningenfx = FXCollections.observableArrayList();
        oefeningenfx = oefeningen;
        return oefeningenfx;
    }
    
    public List<Oefening> findAll(){
        Query queryAll = em.createQuery("SELECT o FROM Oefening o", Oefening.class);
        return (List<Oefening>) queryAll.getResultList();
    }

    @Override
    public Oefening findById(int id) {
        return em.find(Oefening.class, id);
    }

    @Override
    public void insert(Oefening object) {
        em.persist(object);
    }

    @Override
    public void update(Oefening object) {
        em.merge(object);
    }

    @Override
    public void delete(Oefening object) {
        em.remove(object);
    }
}
