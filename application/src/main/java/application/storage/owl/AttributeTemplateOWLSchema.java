package application.storage.owl;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Statement;

import planning.model.AttributeTemplate;

public class AttributeTemplateOWLSchema implements OWLSchema<AttributeTemplate> {

	private SystemTransformationsOWLModel owlModel;

	public AttributeTemplateOWLSchema(SystemTransformationsOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(AttributeTemplate attributeTemplate) {
		Individual ind_attributeTemplate = owlModel.getClass_AttributeTemplate().createIndividual(owlModel.getUniqueURI());
		ind_attributeTemplate.addLabel("Attribute Template", "en");
		ind_attributeTemplate.addLabel("Шаблон атрибута", "ru");
		ind_attributeTemplate.addProperty(owlModel.getDataProperty_name(), attributeTemplate.getName());
		Object value = attributeTemplate.getValue();
		if (value instanceof Boolean) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDboolean);
		} else if (value instanceof Integer) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDinteger);
		} else if (value instanceof String) {
			ind_attributeTemplate.addProperty(owlModel.getDataProperty_value(), value.toString(), XSDDatatype.XSDstring);
		}
		return ind_attributeTemplate;
	}

	@Override
	public AttributeTemplate parse(Individual ind_attributeTemplate) {
		String name = ind_attributeTemplate.getProperty(owlModel.getDataProperty_name()).getString();

		Statement valueStatement = ind_attributeTemplate.getProperty(owlModel.getDataProperty_value());
		if (valueStatement != null) {
			Literal valueLiteral = valueStatement.getLiteral();
			if (valueLiteral.getDatatype() == XSDDatatype.XSDboolean) {
				return new AttributeTemplate(name, valueLiteral.getBoolean());
			}
			if (valueLiteral.getDatatype() == XSDDatatype.XSDstring) {
				return new AttributeTemplate(name, valueLiteral.getString());
			}
			if (valueLiteral.getDatatype() == XSDDatatype.XSDinteger) {
				return new AttributeTemplate(name, valueLiteral.getInt());
			}
		}
		return new AttributeTemplate(name);
	}
}
