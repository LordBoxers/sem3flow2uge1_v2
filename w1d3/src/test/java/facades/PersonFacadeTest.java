package facades;

import DTO.PersonsDTO;
import entities.Person;
import entities.Address;
import java.util.List;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;
    private static Address a1, a2, a3;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        p1 = new Person("p1", "p1sen", "1");
        p2 = new Person("p2", "p2sen", "2");
        p3 = new Person("p3", "p3sen", "3");

        a1 = new Address("Zipcity1", "Zip", "City");
        a2 = new Address("Zipcity1", "Zip", "City");
        a3 = new Address("Zipcity1", "Zip", "City");

        p1.setAddress(a1);
        p2.setAddress(a1);
        p3.setAddress(a1);
        em.getTransaction().commit();

        try {
            em.getTransaction().begin();

            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllPersons() {
        List<PersonsDTO> testGetAllPersons = (List<PersonsDTO>) facade.getAllPersons();
        assertEquals(testGetAllPersons.size(), 3);
    }

}
