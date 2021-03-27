package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Statement;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTransformation;

public class AttributeTransformationOWLSchemaTest {

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

	AttributeTransformationOWLSchema testable;

	SystemTransformationsOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(SystemTransformationsOWLModel.class);

		testable = new AttributeTransformationOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final AttributeTransformation attributeTransformation = new AttributeTransformation("attribute-objectId",
				"attribute-name", null);
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTransformation();
				will(returnValue(i_attributeTransformation_mock));

				oneOf(i_attributeTransformation_mock).addLabel("Трансформация атрибута", "ru");

				oneOf(i_attributeTransformation_mock).addLabel("Attribute transformation", "en");

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_objectId_mock, "attribute-objectId");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_name_mock, "attribute-name");
			}
		});

		assertEquals(i_attributeTransformation_mock, testable.combine(attributeTransformation));
	}

	@Test
	public void combine_boolean() {
		final AttributeTransformation attributeTransformation = new AttributeTransformation("attribute-objectId",
				"attribute-name", new Boolean(true));
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTransformation();
				will(returnValue(i_attributeTransformation_mock));

				oneOf(i_attributeTransformation_mock).addLabel("Трансформация атрибута", "ru");

				oneOf(i_attributeTransformation_mock).addLabel("Attribute transformation", "en");

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_objectId_mock, "attribute-objectId");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_name_mock, "attribute-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_value_mock, "true", XSDDatatype.XSDboolean);
			}
		});

		assertEquals(i_attributeTransformation_mock, testable.combine(attributeTransformation));
	}

	@Test
	public void combine_integer() {
		final AttributeTransformation attributeTransformation = new AttributeTransformation("attribute-objectId",
				"attribute-name", new Integer(100));
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTransformation();
				will(returnValue(i_attributeTransformation_mock));

				oneOf(i_attributeTransformation_mock).addLabel("Трансформация атрибута", "ru");

				oneOf(i_attributeTransformation_mock).addLabel("Attribute transformation", "en");

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_objectId_mock, "attribute-objectId");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_name_mock, "attribute-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_value_mock, "100", XSDDatatype.XSDinteger);
			}
		});

		assertEquals(i_attributeTransformation_mock, testable.combine(attributeTransformation));
	}

	@Test
	public void combine_string() {
		final AttributeTransformation attributeTransformation = new AttributeTransformation("attribute-objectId",
				"attribute-name", "attribute-value");
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_AttributeTransformation();
				will(returnValue(i_attributeTransformation_mock));

				oneOf(i_attributeTransformation_mock).addLabel("Трансформация атрибута", "ru");

				oneOf(i_attributeTransformation_mock).addLabel("Attribute transformation", "en");

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_objectId_mock, "attribute-objectId");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_name_mock, "attribute-name");

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).addProperty(dp_value_mock, "attribute-value",
						XSDDatatype.XSDstring);
			}
		});

		assertEquals(i_attributeTransformation_mock, testable.combine(attributeTransformation));
	}

	@Test
	public void parse() {
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("attribute-objectId"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_value_mock);
				will(returnValue(null));
			}
		});

		AttributeTransformation result = testable.parse(i_attributeTransformation_mock);
		assertEquals("attribute-name", result.getAttributeName());
		assertEquals("attribute-objectId", result.getObjectId());
		assertNull(result.getAttributeValue());
	}

	@Test
	public void parse_boolean() {
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("attribute-objectId"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDboolean));

				oneOf(l_value_mock).getBoolean();
				will(returnValue(true));
			}
		});

		AttributeTransformation result = testable.parse(i_attributeTransformation_mock);
		assertEquals("attribute-name", result.getAttributeName());
		assertEquals("attribute-objectId", result.getObjectId());
		assertEquals(true, result.getAttributeValue());
	}

	@Test
	public void parse_string() {
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("attribute-objectId"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDstring));

				oneOf(l_value_mock).getString();
				will(returnValue("attribute-value"));
			}
		});

		AttributeTransformation result = testable.parse(i_attributeTransformation_mock);
		assertEquals("attribute-name", result.getAttributeName());
		assertEquals("attribute-objectId", result.getObjectId());
		assertEquals("attribute-value", result.getAttributeValue());
	}

	@Test
	public void parse_integer() {
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("attribute-objectId"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDinteger));

				oneOf(l_value_mock).getInt();
				will(returnValue(100));
			}
		});

		AttributeTransformation result = testable.parse(i_attributeTransformation_mock);
		assertEquals("attribute-name", result.getAttributeName());
		assertEquals("attribute-objectId", result.getObjectId());
		assertEquals(100, result.getAttributeValue());
	}

	@Test
	public void parse_unsupported_type() {
		final Individual i_attributeTransformation_mock = context.mock(Individual.class, "i-attributeTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId_mock = context.mock(DatatypeProperty.class, "dp-objectId");
		final DatatypeProperty dp_value_mock = context.mock(DatatypeProperty.class, "dp-value");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId_mock = context.mock(Statement.class, "st-objectId");
		final Statement st_value_mock = context.mock(Statement.class, "st-value");
		final Literal l_value_mock = context.mock(Literal.class, "l-value");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("attribute-name"));

				oneOf(owlModel_mock).getDataProperty_objectId();
				will(returnValue(dp_objectId_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_objectId_mock);
				will(returnValue(st_objectId_mock));

				oneOf(st_objectId_mock).getString();
				will(returnValue("attribute-objectId"));

				oneOf(owlModel_mock).getDataProperty_value();
				will(returnValue(dp_value_mock));

				oneOf(i_attributeTransformation_mock).getProperty(dp_value_mock);
				will(returnValue(st_value_mock));

				oneOf(st_value_mock).getLiteral();
				will(returnValue(l_value_mock));

				oneOf(l_value_mock).getDatatype();
				will(returnValue(XSDDatatype.XSDanyURI));
			}
		});

		AttributeTransformation result = testable.parse(i_attributeTransformation_mock);
		assertEquals("attribute-name", result.getAttributeName());
		assertEquals("attribute-objectId", result.getObjectId());
		assertNull(result.getAttributeValue());
	}
}