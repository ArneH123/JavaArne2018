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
    static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("P2G11PU");
    static EntityManager em = emfactory.createEntityManager();
    
    public static void run(){
        em.getTransaction().begin();
        initializeOefeningen();
        em.getTransaction().commit();
        
    }
    
    public static void initializeOefeningen(){
        List<Oefening> oefeningen = new ArrayList();
        Oefening o1 = new Oefening("Oefening wortels");
        Oefening o2 = new Oefening("Oefening breuken");
        Oefening o3 = new Oefening("Oefening vermenigvuldigen");
        Oefening o4 = new Oefening("Oefening integralen");
        Oefening o5 = new Oefening("Oefening machten");
        Oefening o6 = new Oefening("Oefening pythagoras");
        Oefening o7 = new Oefening("Oefening sinus");
        Oefening o8 = new Oefening("Oefening cosinus");
        Oefening o9 = new Oefening("Oefening tangens");
        Oefening o10 = new Oefening("Oefening combinatoriek");
        Oefening o11 = new Oefening("Oefening statistiek");
        
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
