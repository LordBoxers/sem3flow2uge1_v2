/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entities.Address;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mibsen
 */
public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("per1","person","2323");
        Person p2 = new Person("per2","person","2323");
        Person p3 = new Person("per3","person","2323");
        
        Address a1 = new Address("Zipcity1", "Zip", "City");
        Address a2 = new Address("Zipcity2", "Zip", "City");
        Address a3 = new Address("Zipcity3", "Zip", "City");
        
        p1.setAddress(a1);
        p2.setAddress(a2);
        p3.setAddress(a3);
        
        try {
            em.getTransaction().begin();
            
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            
            em.getTransaction().commit();
            
        } finally {
            em.close();
        }
        
        
    }
}
