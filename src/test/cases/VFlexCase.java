package test.cases;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;
import static test.cases.HFlexVFlexGenerator.*;

public class VFlexCase extends FlexCase {

	public VFlexCase(String comp, boolean rounded) throws Exception {
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("vflex", "1");
		attrs.put("style", "background:yellow");
		HtmlBasedComponent content = generateContent(comp, rounded, attrs, true);
		fixWidthAndHeight(content);
		view = generateView("Fit-the-Rest Flexibility: { component: " + comp
				+ " }");
		view.appendChild(content);
		setCode(content);
		codeView = generateCodeView(content, rawHTMLCode);
	}

}
