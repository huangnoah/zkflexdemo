package test.cases;

import static test.cases.HFlexVFlexGenerator.generateContent;
import static test.cases.HFlexVFlexGenerator.generateView;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;

public class VFlexCase extends FlexCase {

	public VFlexCase(String comp, boolean rounded) throws Exception {
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("vflex", "1");
		attrs.put("style", "background:yellow");
		HtmlBasedComponent content = generateContent(comp, rounded, attrs, true);
		view = generateView("Fit-the-Rest Flexibility: [" + comp
				+ (rounded ? ", rounded" : "") + "]");
		init(content);
	}

}
