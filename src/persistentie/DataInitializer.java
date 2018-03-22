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
        List<Oefening> oefeningen = new ArrayList();
        Oefening o1 = new Oefening("Oefening wortels AB", "Wat is de vierkantswortel van 4?", "2", "De helft");
        Oefening o2 = new Oefening("Oefening breuken","Wat is 8/2?", "4", "Het dubbele");
        Oefening o3 = new Oefening("Oefening vermenigvuldigen","Wat is 4 *4?", "16", "4 + 4 + 4 + 4");
        Oefening o4 = new Oefening("Oefening deling","Wat is 20 gedeeld door 10?", "2", "Doe de 0 weg");
        Oefening o5 = new Oefening("Oefening machten", "Wat is 5²?","25","De helft van 50");
        Oefening o6 = new Oefening("Oefening pythagoras","Geldt de stelling van Pythagoras voor driehoeken of vierkanten","driehoeken","som 180°");
        Oefening o7 = new Oefening("Oefening sinus AB","Wat is sinus 90°?", "1", "één");
        Oefening o8 = new Oefening("Oefening cosinus", "Wat is de cosinus van 180°?","-1","Negatief getal");
        Oefening o9 = new Oefening("Oefening tangens","Wat is de tangens van 45°?","1","één");
        Oefening o10 = new Oefening("XXX - Oefening combinatoriek","Wat is de kans dat we slagen voor project?", "50%", "Aantal leden van de groep");
        Oefening o11 = new Oefening("XXX - Oefening AB statistiek","blabla", "blabla", "blabla");
        
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
    }
}
