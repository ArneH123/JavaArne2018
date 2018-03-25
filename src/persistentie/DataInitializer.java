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

/**
 *
 * @author Arne
 */
public class DataInitializer {
    
    
    public static void run(EntityManager em, boolean wisOudeGegevens)
    {
        try
        {
            em.getTransaction().begin();
            if (wisOudeGegevens)
                em.createNativeQuery("DELETE FROM ROOT.OEFENING").executeUpdate();

            initializeOefeningen(em);
            em.getTransaction().commit();
            
        } catch (Exception e) { System.err.println("Databasefout tijdens datainitialisatie. Klopt databasestructuur ?");  }
    }
    
    
    private static void initializeOefeningen(EntityManager em){
        /*
        List<Oefening> oefeningen = new ArrayList();
        
        Oefening o1 = new Oefening("Oefening wortels AB", "2");
        Oefening o2 = new Oefening("Oefening breuken", "4");
        Oefening o3 = new Oefening("Oefening vermenigvuldigen", "16");
        Oefening o4 = new Oefening("Oefening deling", "2");
        Oefening o5 = new Oefening("Oefening machten", "25");
        Oefening o6 = new Oefening("Oefening pythagoras", "driehoeken");
        Oefening o7 = new Oefening("Oefening sinus AB", "1");
        Oefening o8 = new Oefening("Oefening cosinus", "-1");
        Oefening o9 = new Oefening("Oefening tangens", "1");
        Oefening o10 = new Oefening("XXX - Oefening combinatoriek", "50%");
        Oefening o11 = new Oefening("XXX - Oefening AB statistiek", "blabla");
        
        o10.setIsInGebruik(true);
        o11.setIsInGebruik(true);
        
        oefeningen.add(o1);
        oefeningen.add(o2);
        oefeningen.add(o3);
        oefeningen.add(o4);
        oefeningen.add(o5);
        oefeningen.add(o6);
        oefeningen.add(o7);
        oefeningen.add(o8);
        oefeningen.add(o9);
        oefeningen.add(o10);
        oefeningen.add(o11);      
        
        for(Oefening o : oefeningen){
            em.persist(o);
        }
*/
    }
}
