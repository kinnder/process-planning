package application.storage.owl;

import org.apache.jena.ontology.OntModel;

public interface OWLModel {

	OntModel createOntologyModelBase();

	void createOntologyModel();

	void connectOntologyModel(OntModel ontModel);

	OntModel getOntologyModel();

	String getUniqueURI();
}
