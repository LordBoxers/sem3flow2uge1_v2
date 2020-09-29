package facades;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PersonFacade implements interfaces.PersonInterface {

    private static EntityManagerFactory emf;
    private static PersonFacade instance;

    private PersonFacade() {
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) {
        EntityManager em = getEntityManager();
        Person person = new Person(fName, lName, phone);

        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    @Override
    public PersonDTO deletePerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            if (person == null) {
                throw new PersonNotFoundException("Person with id: "+id+" not found");
            }
            em.remove(person);
            em.remove(person.getAddress());
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();

        try {
            Person result = em.find(Person.class, id);

            if (result == null) {
                throw new PersonNotFoundException("Person with id: "+id+" not found");
            } else {

            PersonDTO resultDTO = new PersonDTO(result);
            return resultDTO;
            }
        } finally {
            em.close();
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
            return new PersonsDTO(query.getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, p.getId());
            if (person == null) {
                throw new PersonNotFoundException("Person not found");
            }
            person.setFirstName(p.getfName());
            person.setLastName(p.getlName());
            person.setPhone(p.getPhone());
            person.setLastEdited(new Date());
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

}
