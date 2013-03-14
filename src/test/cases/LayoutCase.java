package test.cases;

import static test.cases.HFlexVFlexGenerator.fixWidthAndHeight;
import static test.cases.HFlexVFlexGenerator.generateCodeView;
import static test.cases.HFlexVFlexGenerator.generateContent;
import static test.cases.HFlexVFlexGenerator.generateLayout;
import static test.cases.HFlexVFlexGenerator.generateView;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;

public class LayoutCase extends FlexCase {

	public LayoutCase(String comp, String testcase, boolean rounded)
			throws Exception {
		this(comp, testcase, testcase.startsWith("V") ? "vflex" : "hflex",
				rounded);
	}

	private LayoutCase(String comp, String layoutName, String flex,
			boolean rounded) throws Exception {
		HtmlBasedComponent layout = generateLayout(layoutName);
		applyProperties(layout, comp, flex, rounded);
		fixWidthAndHeight(layout);

		view = generateView("Proportional Flexibility: { component: " + comp
				+ "}");
		view.appendChild(layout);
		setCode(layout);
		codeView = generateCodeView(layout, rawHTMLCode);
	}

	public static void applyProperties(HtmlBasedComponent layout, String comp,
			String flex, boolean rounded) throws Exception {
		Map<String, String> attrs1 = new HashMap<String, String>();
		Map<String, String> attrs2 = new HashMap<String, String>();
		if ("hflex".equalsIgnoreCase(flex)) {
			attrs1.put("hflex", "1");
			attrs2.put("hflex", "2");
		} else {
			attrs1.put("hflex", "1");
			attrs1.put("vflex", "1");
			attrs2.put("hflex", "1");
			attrs2.put("vflex", "2");
		}

		attrs1.put("style", "background:cyan");
		attrs2.put("style", "background:yellow");
		layout.appendChild(generateContent(comp, rounded, attrs1, false));
		layout.appendChild(generateContent(comp, rounded, attrs2, false));
	}

}
