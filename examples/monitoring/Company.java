import java.util.*;

public class Company {
    private String name;
    private String location;
    private Set employee;

    public Company(String name, String location) {
	this.name = name;
	this.location = location;
	this.employee = new HashSet();
    }

    public void hire(Person p) {
	employee.add(p);
    }

    public void fire(Person p) {
	employee.remove(p);
    }
}
