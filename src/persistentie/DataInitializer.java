/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Oefening;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Arne
 */
public class DataInitializer {
    static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("P2G11PU");
    static EntityManager em = emfactory.createEntityManager();
    
    public static void run(){
        em.getTransaction().begin();
        initializeOefeningen();
        em.getTransaction().commit();
        
    }
    
    public static void initializeOefeningen(){
        List<Oefening> oefeningen = new ArrayList();
        Oefening o1 = new Oefening("Oefening 1");
        Oefening o2 = new Oefening("Oefening 2");
        Oefening o3 = new Oefening("Oefening 3");
        oefeningen.add(o1);
        oefeningen.add(o3);
        oefeningen.add(o2);
        
        for(Oefening o : oefeningen){
            em.persist(o);
        }
    }
}
