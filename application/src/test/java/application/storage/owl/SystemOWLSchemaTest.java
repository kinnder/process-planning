package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Link;
import planning.model.System;
import planning.model.SystemObject;

public class SystemOWLSchemaTest {

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

	SystemOWLSchema testable;

	TaskDescriptionOWLModel owlModel_mock;

	SystemObjectOWLSchema systemObjectOWLSchema_mock;

	LinkOWLSchema linkOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(TaskDescriptionOWLModel.class);
		systemObjectOWLSchema_mock = context.mock(SystemObjectOWLSchema.class);
		linkOWLSchema_mock = context.mock(LinkOWLSchema.class);

		testable = new SystemOWLSchema(owlModel_mock, systemObjectOWLSchema_mock, linkOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemOWLSchema(new TaskDescriptionOWLModel());
	}

	@Test
	public void combine() {
		final SystemObject systemObject = new SystemObject("test-object");
		final Link link = new Link("test-link", null, null);
		final System system = new System();
		system.addObject(systemObject);
		system.addLink(link);

		final Individual i_system_mock = context.mock(Individual.class, "i-system");
		final Individual i_systemObject_mock = context.mock(Individual.class, "i-systemObject");
		final Individual i_link_mock = context.mock(Individual.class, "i-link");
		final ObjectProperty op_isSystemObjectOf_mock = context.mock(ObjectProperty.class, "op-isSystemObjectOf");
		final ObjectProperty op_hasSystemObject_mock = context.mock(ObjectProperty.class, "op-hasSystemObject");
		final ObjectProperty op_isLinkOf_mock = context.mock(ObjectProperty.class, "op-isLinkOf");
		final ObjectProperty op_hasLink_mock = context.mock(ObjectProperty.class, "op-hasLink");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_System();
				will(returnValue(i_system_mock));

				oneOf(i_system_mock).addLabel("System", "en");

				oneOf(i_system_mock).addLabel("Система", "ru");

				oneOf(systemObjectOWLSchema_mock).combine(systemObject);
				will(returnValue(i_systemObject_mock));

				oneOf(owlModel_mock).getObjectProperty_isSystemObjectOf();
				will(returnValue(op_isSystemObjectOf_mock));

				oneOf(i_systemObject_mock).addProperty(op_isSystemObjectOf_mock, i_system_mock);

				oneOf(owlModel_mock).getObjectProperty_hasSystemObject();
				will(returnValue(op_hasSystemObject_mock));

				oneOf(i_system_mock).addProperty(op_hasSystemObject_mock, i_systemObject_mock);

				oneOf(linkOWLSchema_mock).combine(link);
				will(returnValue(i_link_mock));

				oneOf(owlModel_mock).getObjectProperty_isLinkOf();
				will(returnValue(op_isLinkOf_mock));

				oneOf(i_link_mock).addProperty(op_isLinkOf_mock, i_system_mock);

				oneOf(owlModel_mock).getObjectProperty_hasLink();
				will(returnValue(op_hasLink_mock));

				oneOf(i_system_mock).addProperty(op_hasLink_mock, i_link_mock);
			}
		});

		assertEquals(i_system_mock, testable.combine(system));
	}

	@Test
	public void parse() {
		final Individual i_system_mock = context.mock(Individual.class, "i-system");
		final OntClass oc_systemObject_mock = context.mock(OntClass.class, "oc-systemObject");
		final OntClass oc_link_mock = context.mock(OntClass.class, "oc-link");
		final ObjectProperty op_hasSystemObject = context.mock(ObjectProperty.class, "op-hasSystemObject");
		final ObjectProperty op_hasLink = context.mock(ObjectProperty.class, "op-hasLink");
		final SystemObject systemObject = new SystemObject("test-object");
		final Link link = new Link("test-link", null, null);

		final Individual i_systemObject_mock = context.mock(Individual.class, "i-systemObject");
		final ExtendedIterator<Individual> systemObjectIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemObject_mock).iterator());

		final Individual i_link_mock = context.mock(Individual.class, "i-link");
		final ExtendedIterator<Individual> linkIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_link_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_SystemObject();
				will(returnValue(oc_systemObject_mock));

				oneOf(oc_systemObject_mock).listInstances();
				will(returnValue(systemObjectIterator));

				oneOf(owlModel_mock).getObjectProperty_hasSystemObject();
				will(returnValue(op_hasSystemObject));

				oneOf(i_system_mock).hasProperty(op_hasSystemObject, i_systemObject_mock);
				will(returnValue(true));

				oneOf(i_systemObject_mock).asIndividual();
				will(returnValue(i_systemObject_mock));

				oneOf(systemObjectOWLSchema_mock).parse(i_systemObject_mock);
				will(returnValue(systemObject));

				oneOf(owlModel_mock).getClass_Link();
				will(returnValue(oc_link_mock));

				oneOf(oc_link_mock).listInstances();
				will(returnValue(linkIterator));

				oneOf(owlModel_mock).getObjectProperty_hasLink();
				will(returnValue(op_hasLink));

				oneOf(i_system_mock).hasProperty(op_hasLink, i_link_mock);
				will(returnValue(true));

				oneOf(i_link_mock).asIndividual();
				will(returnValue(i_link_mock));

				oneOf(linkOWLSchema_mock).parse(i_link_mock);
				will(returnValue(link));
			}
		});

		System result = testable.parse(i_system_mock);
		assertTrue(result.getLinks().contains(link));
		assertTrue(result.getObjects().contains(systemObject));
	}
}