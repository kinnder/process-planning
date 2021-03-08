package application.storage.owl;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;

public class ActionParametersOWLSchema implements OWLSchema<Map<String, String>> {

	private NodeNetworkOWLModel owlModel;

	public ActionParametersOWLSchema(NodeNetworkOWLModel owlModel) {
		this.owlModel = owlModel;
	}

	@Override
	public Individual combine(Map<String, String> actionParameters) {
		Individual ind_actionParameters = owlModel.getClass_ActionParameters()
				.createIndividual(owlModel.getUniqueURI());
		ind_actionParameters.addLabel("Action Parameters", "en");
		ind_actionParameters.addLabel("Параметры действия", "ru");

		for (String key : actionParameters.keySet()) {
			Individual ind_parameter = owlModel.getClass_Parameter().createIndividual(owlModel.getUniqueURI());
			ind_parameter.addLabel("Parameter", "en");
			ind_parameter.addLabel("Параметр", "ru");
			ind_parameter.addProperty(owlModel.getDataProperty_key(), key);
			ind_parameter.addProperty(owlModel.getDataProperty_value(), actionParameters.get(key));
			ind_actionParameters.addProperty(owlModel.getObjectProperty_hasParameter(), ind_parameter);
			ind_parameter.addProperty(owlModel.getObjectProperty_isParameterOf(), ind_actionParameters);
		}
		return ind_actionParameters;
	}

	@Override
	public Map<String, String> parse(Individual ind_actionParameters) {
		Map<String, String> actionParameters = new HashMap<>();

		owlModel.getClass_Parameter().listInstances().filterKeep((ind_parameter) -> {
			return ind_actionParameters.hasProperty(owlModel.getObjectProperty_hasParameter(), ind_parameter);
		}).forEachRemaining((ind_parameter) -> {
			String name = ind_parameter.getProperty(owlModel.getDataProperty_name()).getString();
			String value = ind_parameter.getProperty(owlModel.getDataProperty_value()).getString();
			actionParameters.put(name, value);
		});

		return actionParameters;
	}
}
