public class Person {
    private String name;
    private int age;
    private double salary;

    public Person(String name, int age, double salary) {
	this.name = name;
	this.age = age;
	this.salary = salary;
    }

    public double raiseSalary(double rate) {
	return salary += 10;
    }
}
