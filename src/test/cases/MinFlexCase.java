package test.cases;

import static test.cases.HFlexVFlexGenerator.fixWidthAndHeight;
import static test.cases.HFlexVFlexGenerator.generateCodeView;
import static test.cases.HFlexVFlexGenerator.generateLayout;
import static test.cases.HFlexVFlexGenerator.generateView;
import static test.cases.HFlexVFlexGenerator.generateContent;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;

public class MinFlexCase extends FlexCase {

	public MinFlexCase(String comp, String testcase) throws Exception {
		this(comp, testcase, testcase.startsWith("V") ? "hflex" : "vflex");
	}

	private MinFlexCase(String comp, String layoutName, String flex)
			throws Exception {
		HtmlBasedComponent layout = generateLayout(layoutName, null, null);
		applyProperties(layout, comp, flex);
		
		view = generateView("minimum Flex: { componenet: " + comp
				+ ", layout: " + layoutName + " }");
		fixWidthAndHeight(layout);
		view.appendChild(layout);
		codeView = generateCodeView(layout);
	}

	private static HtmlBasedComponent applyProperties(
			HtmlBasedComponent layout, String comp, String flex)
			throws Exception {
		// build attrs
		Map<String, String> attrs1 = new HashMap<String, String>();
		Map<String, String> attrs2 = new HashMap<String, String>();
		Map<String, String> attrs3 = new HashMap<String, String>();
		if ("hflex".equalsIgnoreCase(flex)) {
			layout.setHflex("min");
			layout.setHeight("200px");
			attrs1.put("hflex", "1");
			attrs1.put("vflex", "1");
			attrs2.put("width", "75px");
			attrs2.put("vflex", "1");
			attrs3.put("width", "120px");
			attrs3.put("vflex", "1");
		} else {
			layout.setVflex("min");
			layout.setWidth("200px");
			attrs1.put("hflex", "1");
			attrs1.put("vflex", "1");
			attrs2.put("height", "60px");
			attrs2.put("hflex", "1");
			attrs3.put("height", "100px");
			attrs3.put("hflex", "1");
		}

		attrs1.put("style", "background:yellow");
		attrs2.put("style", "background:cyan");
		attrs3.put("style", "background:red");

		// append
		layout.appendChild(generateContent(comp, attrs1, false));
		layout.appendChild(generateContent(comp, attrs2, false));
		layout.appendChild(generateContent(comp, attrs3, false));
		return layout;
	}

}
