public class Main {

    public static void main(String[] args) {
	Company company = new Company("Foo, Inc.", "Bremen");
	Person bob = new Person("Bob", 35, 2000);
	company.hire(bob);
	bob.raiseSalary(0.10);
	company.fire(bob);
    }
}
