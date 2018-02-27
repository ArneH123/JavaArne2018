/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author TessaWalraet
 */
public class JPAUtil
{
    private final static EntityManagerFactory entityManagerFactory = 
            Persistence.createEntityManagerFactory("P2G11PU");
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    private JPAUtil() {
        
    }
}

