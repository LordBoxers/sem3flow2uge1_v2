package rest;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    
    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPerson(@PathParam("id")int id) throws PersonNotFoundException {
        PersonDTO person = FACADE.getPerson(id);
        return GSON.toJson(person);
    }
    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllCars() {
        PersonsDTO pers = FACADE.getAllPersons();
        return GSON.toJson(pers);
    }
    
    @Path("{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id")int id) throws PersonNotFoundException {
        PersonDTO pDeleted = FACADE.deletePerson(id);
        return GSON.toJson(pDeleted);
    }
    
    @Path("{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editPerson(@PathParam("id")int id, String pers) throws PersonNotFoundException {
        PersonDTO persDTO = GSON.fromJson(pers, PersonDTO.class);
        persDTO.setId(id);
        PersonDTO pEdit = FACADE.editPerson(persDTO);
        return GSON.toJson(pEdit);
    }
    
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String pers) {
        PersonDTO persDTO = GSON.fromJson(pers, PersonDTO.class);
        PersonDTO pAdd = FACADE.addPerson(persDTO.getfName(), persDTO.getlName(), persDTO.getPhone());
        return GSON.toJson(pAdd);
    }
}
