package DTO;

import entities.Person;


public class PersonDTO {
    private int id;
    private String fName;
    private String lName;
    private String phone;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.phone = person.getPhone();
    }
    public void addPerson(String fName, String lName, String phone) {
        
    }

    public void deletePerson(int id) {

    }

    public void getPerson(int id) {

    }

    public void editPerson(PersonDTO p) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
