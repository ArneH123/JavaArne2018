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
public class OefeningMapper implements GenericDao<Oefening>{

    private EntityManagerFactory emfactory;
    private EntityManager em;
    private final String EntityName = "P2G11PU";

    public OefeningMapper() {
        try
        {
            emfactory = Persistence.createEntityManagerFactory(EntityName);
            em = emfactory.createEntityManager();
        }
        catch (Exception e)
        {
            dataInitBaseFout();
        }
        
        boolean resetAllData = false;
        if (resetAllData)
        {
            System.out.print("Opgelet ! DataInitializer doet een dataReset op alle gegevens! Gegevens gaan dus verloren. Gelieve buiten debug resetAllData in OefeningMapper op vals te zetten");
            dataInit();
        }
        
    }
    public void dataInit()
        {
            try
            {
                em.getTransaction().begin();
                
                    em.createNativeQuery("DELETE FROM ROOT.OEFENING_GROEPSBEWERKINGEN").executeUpdate();
                    em.createNativeQuery("DELETE FROM ROOT.OEFENING").executeUpdate();
                    
                     em.persist(new Oefening("Oefening 1", "10",false));
                     em.persist(new Oefening("Oefening breuken (in gebruik)", "4",true));
                     em.persist(new Oefening("Oefening vermenigvuldigen (in gebruik)", "16", true));
                em.getTransaction().commit();
            } catch (Exception e) { System.err.println("Databasefout tijdens datainitialisatie. Klopt databasestructuur ?");  }
    }
    
    
    public List<Oefening> findAll(){
        return (List<Oefening>)em.createQuery("SELECT o FROM Oefening o ORDER BY o.id ASC", Oefening.class).getResultList();
    }

    @Override
    public Oefening findById(int id) {
        return em.find(Oefening.class, id);
    }

    @Override
    public void insert(Oefening object) 
    {
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    @Override
    public void update(Oefening object) {
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();        
    }

    @Override
    public void delete(Oefening object) {
        em.getTransaction().begin();
        em.remove(object);
        em.getTransaction().commit();
    }
    
    final void dataInitBaseFout()
    {
        System.err.println("!!!");
        System.err.println("Database initialisatie fout (DataInitializer) !");
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
    
}
