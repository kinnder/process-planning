package application;

//TODO : remove this annotation
@SuppressWarnings("PMD")
public class Main {

	public static void main(String[] args) {
		try {
			UserInterface ui = new UserInterface(System.out);
			Application application = new Application();
			application.registerUserInterface(ui);
			application.run(args);
			// TODO : remove
			// application.run(new String[] { "-help" });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
