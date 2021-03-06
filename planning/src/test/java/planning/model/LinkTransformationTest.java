package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class LinkTransformationTest {

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

	LinkTransformation testable;

	@BeforeEach
	public void setup() {
		testable = new LinkTransformation("id-template", "link-name", "link-old-value", "link-new-value");
	}

	@Test
	public void applyTo() {
		final SystemObject object_mock = context.mock(SystemObject.class);
		final Link link_in_system_mock = context.mock(Link.class, "link-in-system");
		final SystemVariant systemVariant_mock = context.mock(SystemVariant.class);
		final System system_mock = context.mock(System.class);

		context.checking(new Expectations() {
			{
				oneOf(systemVariant_mock).getObjectByIdMatch("id-template");
				will(returnValue(object_mock));

				oneOf(object_mock).getId();
				will(returnValue("id-actual"));

				oneOf(systemVariant_mock).getObjectIdByIdMatch("link-new-value");
				will(returnValue("link-new-value-actual"));

				oneOf(systemVariant_mock).getObjectIdByIdMatch("link-old-value");
				will(returnValue("link-old-value-actual"));

				oneOf(systemVariant_mock).getSystem();
				will(returnValue(system_mock));

				oneOf(system_mock).getLink("link-name", "id-actual", "link-old-value-actual");
				will(returnValue(link_in_system_mock));

				oneOf(link_in_system_mock).setObjectId2("link-new-value-actual");
			}
		});

		testable.applyTo(systemVariant_mock);
	}

	@Test
	public void getLinkName() {
		assertEquals("link-name", testable.getLinkName());
	}

	@Test
	public void getLinkObjectId2New() {
		assertEquals("link-new-value", testable.getLinkObjectId2New());
	}

	@Test
	public void getLinkObjectId2Old() {
		assertEquals("link-old-value", testable.getLinkObjectId2Old());
	}
}
