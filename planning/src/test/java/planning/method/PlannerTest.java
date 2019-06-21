package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.Element;
import planning.model.System;
import planning.model.SystemVariant;

public class PlannerTest {

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

	Planner testable;

	System initial_system_mock;

	System final_system_mock;

	Element element_mock;

	Element elements[];

	@BeforeEach
	public void setup() {
		initial_system_mock = context.mock(System.class, "initial-system");
		final_system_mock = context.mock(System.class, "final-system");
		element_mock = context.mock(Element.class, "element");
		elements = new Element[] { element_mock };

		testable = new Planner(initial_system_mock, final_system_mock, elements);
	}

	@Test
	public void plan() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemVariant systemVariants[] = new SystemVariant[] { systemVariant_mock };
		final System system_mock = context.mock(System.class, "system");
		final Action action_mock = context.mock(Action.class);

		context.checking(new Expectations() {
			{
				oneOf(initial_system_mock).contains(final_system_mock);
				will(returnValue(false));

				oneOf(element_mock).applyTo(initial_system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(systemVariant_mock).getAction();
				will(returnValue(action_mock));

				oneOf(system_mock).contains(final_system_mock);
				will(returnValue(true));

				oneOf(element_mock).applyTo(system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(systemVariant_mock).getAction();
				will(returnValue(action_mock));
			}
		});

		testable.plan();
	}

	@Test
	public void plan_no_variants() {
		final SystemVariant systemVariants[] = new SystemVariant[] {};

		context.checking(new Expectations() {
			{
				oneOf(initial_system_mock).contains(final_system_mock);
				will(returnValue(false));

				oneOf(element_mock).applyTo(initial_system_mock);
				will(returnValue(systemVariants));
			}
		});

		testable.plan();
	}

	@Test
	public void getShortestPath() {
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final SystemVariant systemVariants[] = new SystemVariant[] { systemVariant_mock };
		final System system_mock = context.mock(System.class, "system");
		final Action action_mock = context.mock(Action.class);

		context.checking(new Expectations() {
			{
				oneOf(initial_system_mock).contains(final_system_mock);
				will(returnValue(false));

				oneOf(element_mock).applyTo(initial_system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(systemVariant_mock).getAction();
				will(returnValue(action_mock));

				oneOf(system_mock).contains(final_system_mock);
				will(returnValue(true));

				oneOf(element_mock).applyTo(system_mock);
				will(returnValue(systemVariants));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(systemVariant_mock).getAction();
				will(returnValue(action_mock));
			}
		});
		testable.plan();

		assertEquals(action_mock, testable.getShortestPlan().get(0));
	}
}
