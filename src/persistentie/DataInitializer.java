/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistentie;

import domein.Oefening;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Arne
 */
public class DataInitializer {
    static EntityManagerFactory emfactory;
    static EntityManager em;
    static final String EntityName = "P2G11PU";
    
    public static void run(){
        try
        {
            emfactory = Persistence.createEntityManagerFactory(EntityName);
            em = emfactory.createEntityManager();

            em.getTransaction().begin();
            initializeOefeningen();
            em.getTransaction().commit();
        }
        catch (Exception e)
        {
            dataBaseFout();
        }
    }
    
    public static void dataBaseFout()
    {
        System.err.println("!!!");
        System.err.println("Database initialisatie fout !");
        System.err.println("Controleer volgende zaken:");
        System.err.println("-1- Services > Databases > JavaDB > right click 'Start Server'");
        System.err.println("-2- Services > Databases > jdbc:deby... > right click 'connect'");
        System.err.println("-3- Projects > META-INF > persistence.xml");
        System.err.println("  Kijk of de naam "+EntityName+" gekoppeld is aan de database van -2-");
        System.err.println("---");
        System.err.println("Het programma wordt nu gestopt. Gelieve de fout eerst te verhelpen");
        System.err.println("!!!");
        System.exit(0);
    }
    
    public static void initializeOefeningen(){
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
        Oefening o10 = new Oefening("Oefening combinatoriek","Wat is de kans dat we slagen voor project?", "50%", "Aantal leden van de groep");
        Oefening o11 = new Oefening("Oefening AB statistiek","blabla", "blabla", "blabla");
        
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
        
        try
        {
            for(Oefening o : oefeningen){
                em.persist(o);
            }
        }
        catch (Exception e)
        {
            dataBaseFout();
        }        
    }
}
