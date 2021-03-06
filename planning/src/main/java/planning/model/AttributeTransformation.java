package planning.model;

public class AttributeTransformation extends Transformation {

	private String attributeName;

	private Object attributeValue;

	public AttributeTransformation(String objectId, String attributeName, Object attributeValue) {
		super(objectId);
		this.attributeName = attributeName;
		this.attributeValue = attributeValue;
	}

	@Override
	public void applyTo(SystemVariant systemVariant) {
		SystemObject object = systemVariant.getObjectByIdMatch(getObjectId());
		Attribute attribute = object.getAttribute(attributeName);
		attribute.setValue(attributeValue);
	}

	public String getAttributeName() {
		return attributeName;
	}

	public Object getAttributeValue() {
		return attributeValue;
	}
}
