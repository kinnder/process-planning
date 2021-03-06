package application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import application.command.Command;
import application.command.CommandData;
import application.command.HelpCommand;
import application.command.HelpCommandDataMatcher;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandDataMatcher;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandDataMatcher;
import application.command.PlanCommand;
import application.command.PlanCommandDataMatcher;

public class ApplicationTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	Application testable;

	HelpCommand helpCommand_mock;

	PlanCommand planCommand_mock;

	NewSystemTransformationsCommand newSystemTransformationsCommand_mock;

	NewTaskDescriptionCommand newTaskDescriptionCommand_mock;

	@BeforeEach
	public void setup() {
		helpCommand_mock = context.mock(HelpCommand.class);
		planCommand_mock = context.mock(PlanCommand.class);
		newSystemTransformationsCommand_mock = context.mock(NewSystemTransformationsCommand.class);
		newTaskDescriptionCommand_mock = context.mock(NewTaskDescriptionCommand.class);

		testable = new Application();
		testable.commands.put(HelpCommand.NAME, helpCommand_mock);
		testable.commands.put(PlanCommand.NAME, planCommand_mock);
		testable.commands.put(NewSystemTransformationsCommand.NAME, newSystemTransformationsCommand_mock);
		testable.commands.put(NewTaskDescriptionCommand.NAME, newTaskDescriptionCommand_mock);
	}

	@Test
	public void newInstance() {
		testable = new Application();
		assertTrue(testable.commands.get(HelpCommand.NAME) instanceof HelpCommand);
		assertTrue(testable.commands.get(PlanCommand.NAME) instanceof PlanCommand);
		assertTrue(testable.commands.get(NewSystemTransformationsCommand.NAME) instanceof NewSystemTransformationsCommand);
		assertTrue(testable.commands.get(NewSystemTransformationsCommand.NAME) instanceof NewSystemTransformationsCommand);
	}

	@Test
	public void registerUserInterface() {
		final UserInterface ui_mock = context.mock(UserInterface.class);

		context.checking(new Expectations() {
			{
				oneOf(helpCommand_mock).registerUserInterface(ui_mock);

				oneOf(planCommand_mock).registerUserInterface(ui_mock);

				oneOf(newSystemTransformationsCommand_mock).registerUserInterface(ui_mock);

				oneOf(newTaskDescriptionCommand_mock).registerUserInterface(ui_mock);
			}
		});

		testable.registerUserInterface(ui_mock);
	}

	@Test
	public void run_HelpCommand() throws Exception {
		Option h_option = new Option("h", "help", false, "prints usage");
		Option td_option = new Option("td", "taskDescription", true, "file with description of the task");
		Option st_option = new Option("st", "systemTransformations", true, "file with description of the system transformations");
		Option p_option = new Option("p", "process", true, "output file with process");
		Option nn_option = new Option("nn", "nodeNetwork", true, "output file with node network");
		Option plan_option = new Option("plan", "plan process");
		Option new_st_option = new Option("new_st", true, "create new file with predefined system transformations");
		new_st_option.setLongOpt("new-system-transformations");
		new_st_option.setArgName("domain");
		new_st_option.setOptionalArg(true);
		Option new_td_option = new Option("new_td", true, "create new file with predefined task description");
		new_td_option.setLongOpt("new-task-description");
		new_td_option.setArgName("domain");
		new_td_option.setOptionalArg(true);

		Options options = new Options();
		options.addOption(h_option);
		options.addOption(td_option);
		options.addOption(st_option);
		options.addOption(p_option);
		options.addOption(nn_option);
		options.addOption(plan_option);
		options.addOption(new_st_option);
		options.addOption(new_td_option);

		context.checking(new Expectations() {
			{
				oneOf(helpCommand_mock).execute(with(new HelpCommandDataMatcher().expectOptions(options)));
			}
		});

		testable.run(new String[] { "-h" });
	}

	@Test
	public void run_PlanCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(planCommand_mock)
						.execute(with(new PlanCommandDataMatcher().expectSystemTransformationsFile("st_file.xml")
								.expectTaskDescriptionFile("td_file.xml").expectProcessFile("p_file.xml")));
			}
		});

		testable.run(new String[] { "-plan", "-taskDescription=td_file.xml", "-systemTransformations=st_file.xml", "-process=p_file.xml" });
	}

	@Test
	public void run_NewSystemTransformationsCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(newSystemTransformationsCommand_mock)
						.execute(with(new NewSystemTransformationsCommandDataMatcher()
								.expectSystemTransformationsFile("st_file.xml").expectDomain("unknown")));
			}
		});

		testable.run(new String[] { "-new_st", "-systemTransformations=st_file.xml" });
	}

	@Test
	public void run_NewTaskDescriptionCommand() throws Exception {
		context.checking(new Expectations() {
			{
				oneOf(newTaskDescriptionCommand_mock).execute(with(new NewTaskDescriptionCommandDataMatcher()
						.expectTaskDescriptionFile("td_file.xml").expectDomain("materialPoints")));
			}
		});

		testable.run(new String[] { "-new_td", "materialPoints", "-taskDescription=td_file.xml" });
	}

	@Test
	public void runCommand() throws Exception {
		final Command command_mock = context.mock(Command.class);
		final CommandData data_mock = context.mock(CommandData.class);

		context.checking(new Expectations() {
			{
				oneOf(command_mock).execute(data_mock);
			}
		});
		testable.commands.put("command", command_mock);

		testable.runCommand("command", data_mock);
	}

	@Test
	public void runCommand_throwException() throws Exception {
		final Command command = new Command() {
			@Override
			public void execute(CommandData data) throws Exception {
				throw new Exception("runtime exception");
			}
		};
		final CommandData data_mock = context.mock(CommandData.class);

		testable.commands.put("command", command);

		assertThrows(Exception.class, () -> {
			testable.runCommand("command", data_mock);
		});
	}
}
