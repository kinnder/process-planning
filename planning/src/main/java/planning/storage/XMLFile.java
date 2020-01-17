package planning.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFile {

	private XMLSchema xmlSchema;

	private SAXBuilder builder = new SAXBuilder();

	private XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setIndent("\t"));

	public XMLFile(XMLSchema xmlSchema) {
		this.xmlSchema = xmlSchema;
	}

	XMLFile(XMLSchema xmlSchema, SAXBuilder builder) {
		this(xmlSchema);
		this.builder = builder;
	}

	public void load(URL resource) throws IOException, URISyntaxException, JDOMException {
		InputStream inputStream = new BufferedInputStream(Files.newInputStream(Paths.get(resource.toURI())));
		load(inputStream);
	}

	public void load(InputStream inputStream) throws JDOMException, IOException {
		Element root = builder.build(inputStream).getRootElement();
		xmlSchema.parse(root);
	}

	public void save(URL resource) throws IOException, URISyntaxException {
		OutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(resource.toURI())));
		save(outputStream);
	}

	public void save(OutputStream outputStream) throws IOException {
		Element root = xmlSchema.combine();
		Document document = new Document(root);
		outputter.output(document, outputStream);
	}

	public XMLSchema getXMLSchema() {
		return this.xmlSchema;
	}
}
