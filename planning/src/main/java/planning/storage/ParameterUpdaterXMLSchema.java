package planning.storage;

import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

import planning.model.ActionParameterUpdater;
import planning.model.LuaScriptActionParameterUpdater;

//TODO : rename schema to match generic class
public class ParameterUpdaterXMLSchema implements XMLSchema<ActionParameterUpdater> {

	// TODO : пересмотреть положение globals
	private static Globals globals = JsePlatform.standardGlobals();

	// TODO : lines has separate schema

	@Override
	public ActionParameterUpdater parse(Element root) throws DataConversionException {
		List<Element> elements = root.getChildren("line");
		String[] lines = new String[elements.size()];
		for (Element element : elements) {
			int id = element.getAttribute("n").getIntValue() - 1;
			lines[id] = element.getText();
		}
		StringBuilder script = new StringBuilder();
		for (String line : lines) {
			script.append(line).append("\n");
		}
		return new LuaScriptActionParameterUpdater(globals, script.toString());
	}

	@Override
	public Element combine(ActionParameterUpdater parameterUpdater) {
		LuaScriptActionParameterUpdater luaParameterUpdater = (LuaScriptActionParameterUpdater) parameterUpdater;
		String lines[] = luaParameterUpdater.getScript().split("\n");
		Element root = new Element("parameterUpdater");
		for (int i = 0; i < lines.length; i++) {
			Element element = new Element("line");
			element.setText(lines[i]);
			element.setAttribute("n", Integer.toString(i + 1));
			root.addContent(element);
		}
		return root;
	}
}