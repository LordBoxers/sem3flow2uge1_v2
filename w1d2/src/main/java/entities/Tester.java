package entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Tester {
    
    public static void main(String[] args) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("Jens",1963);
        Person p2 = new Person("Aage",1953);
        
        Address a1 = new Address("Store Torv 1", 2323, "br");
        Address a2 = new Address("Store Torv 2", 2324, "br2");
        
        p1.setAddress(a1);
        p2.setAddress(a2);
        
        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);
        
        p1.addFee(f1);
        p1.addFee(f3);
        p2.addFee(f2);
        
        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("Butterfly");
        SwimStyle s3 = new SwimStyle("Breast Stroke");
        
        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);
        
        em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
            p1.removeSwimStyle(s3);
        em.getTransaction().commit();
        
        
        
        System.out.println("id for p1: "+p1.getP_id());
        System.out.println("id for p2: "+p2.getP_id());
        
        System.out.println("Jens' gade: "+p1.getAddress().getStreet());
        
        System.out.println("Lad os se om to-vejs virker: "+a1.getPerson().getName());
        
        System.out.println("hvem har betalt f2? det har: "+f2.getPerson().getName());
        
        System.out.println("Hvad er der blevet betalt i alt?");
        
        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();
        
        for (Fee f: fees){
            System.out.println(f.getPerson().getName() + ": " + f.getAmount() + " Kr. Den: " + f.getPayDate() + "Adr: " + f.getPerson().getAddress().getCity());
        }
    }
}
