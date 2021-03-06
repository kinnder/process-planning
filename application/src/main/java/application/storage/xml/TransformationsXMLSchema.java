package application.storage.xml;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;
import planning.model.LinkTransformation;
import planning.model.Transformation;

public class TransformationsXMLSchema implements XMLSchema<Transformation[]> {

	final private static String TAG_transformations = "transformations";

	final private static String TAG_objectId = "objectId";

	final private static String TAG_transformation = "transformation";

	@Override
	public String getSchemaName() {
		return TAG_transformations;
	}

	public TransformationsXMLSchema() {
		this(new AttributeTransformationXMLSchema(), new LinkTransformationXMLSchema());
	}

	TransformationsXMLSchema(AttributeTransformationXMLSchema attributeTransformationXMLSchema, LinkTransformationXMLSchema linkTransformationXMLSchema) {
		this.attributeTransformationSchema = attributeTransformationXMLSchema;
		this.linkTransformationSchema = linkTransformationXMLSchema;
	}

	private AttributeTransformationXMLSchema attributeTransformationSchema;

	private LinkTransformationXMLSchema linkTransformationSchema;

	@Override
	public Transformation[] parse(Element root) throws DataConversionException {
		List<Transformation> transformations = new ArrayList<>();
		List<Element> elements = root.getChildren(linkTransformationSchema.getSchemaName());
		for (Element element : elements) {
			transformations.add(linkTransformationSchema.parse(element));
		}
		elements = root.getChildren(attributeTransformationSchema.getSchemaName());
		for (Element element : elements) {
			transformations.add(attributeTransformationSchema.parse(element));
		}
		return transformations.toArray(new Transformation[0]);
	}

	@Override
	public Element combine(Transformation[] transformations) {
		Element root = new Element(TAG_transformations);
		for (Transformation transformation : transformations) {
			Element element;
			if (transformation instanceof AttributeTransformation) {
				element = attributeTransformationSchema.combine((AttributeTransformation) transformation);
			} else if (transformation instanceof LinkTransformation) {
				element = linkTransformationSchema.combine((LinkTransformation) transformation);
			} else {
				element = combineTransformation(transformation);
			}
			root.addContent(element);
		}
		return root;
	}

	// TODO : remove this or update systemTransformations.xsd
	public Element combineTransformation(Transformation transformation) {
		Element objectId = new Element(TAG_objectId);
		objectId.setText(transformation.getObjectId());
		Element root = new Element(TAG_transformation);
		root.addContent(objectId);
		return root;
	}
}
