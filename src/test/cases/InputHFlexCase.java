package test.cases;

import static test.cases.HFlexVFlexGenerator.fixWidthAndHeight;
import static test.cases.HFlexVFlexGenerator.generateCodeView;
import static test.cases.HFlexVFlexGenerator.generateContent;
import static test.cases.HFlexVFlexGenerator.generateView;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;

public class InputHFlexCase extends FlexCase {
	public InputHFlexCase(String comp, boolean rounded) throws Exception {
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("hflex", "1");
		HtmlBasedComponent content = generateContent(comp, rounded, attrs, true);
		fixWidthAndHeight(content);
		view = generateView("Input HFlex Case: { input: " + comp + " }");
		view.appendChild(content);
		setCode(content);
		codeView = generateCodeView(content, rawHTMLCode);
	}
}
