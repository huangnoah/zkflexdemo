package test.cases;

import java.util.HashMap;
import java.util.Map;

import nu.xom.Attribute;
import nu.xom.Element;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import test.util.XMLFormatter;

public class HFlexVFlexGenerator {

	private static final String div = "div";
	private static final String vlayout = "vlayout";
	private static final String vbox = "vbox";
	private static final String hlayout = "hlayout";
	private static final String hbox = "hbox";
	private static final String panel = "panel";
	private static final String window = "window";
	private static final String groupbox = "groupbox";
	private static final String tabbox = "tabbox";

	private static XulElement generateElement(String comp, String style) {
		if (div.equalsIgnoreCase(comp)) {
			Div d = new Div();
			d.setStyle(style);
			d.appendChild(new Label("1"));
			return d;
		} else if ("panel".equalsIgnoreCase(comp)) {
			Panel p = new Panel();
			Panelchildren pc = new Panelchildren();
			pc.appendChild(genDiv(style));
			p.appendChild(pc);
			return p;
		} else if ("window".equalsIgnoreCase(comp)) {
			Window w = new Window();
			w.appendChild(genDiv(style));
			return w;
		} else if ("groupbox".equalsIgnoreCase(comp)) {
			Groupbox g = new Groupbox();
			g.appendChild(genDiv(style));
			return g;
		} else if ("tabbox".equalsIgnoreCase(comp)) {
			Tabbox tabbox = new Tabbox();
			Tabs tabs = new Tabs();
			Tab tab = new Tab("case");
			Tabpanels tabpanels = new Tabpanels();
			Tabpanel tabpanel = new Tabpanel();
			tabs.appendChild(tab);
			tabpanel.appendChild(genDiv(style));
			tabpanels.appendChild(tabpanel);
			tabbox.appendChild(tabs);
			tabbox.appendChild(tabpanels);
			return tabbox;
		} else {
			return new XulElement() {
			};
		}
	}

	private static HtmlBasedComponent generateContent(String comp,
			Map<String, String> flex) {

		String hflexVal = flex.get("hflex");
		String vflexVal = flex.get("vflex");
		String widthVal = flex.get("width");
		String heightVal = flex.get("height");
		String styleVal = flex.get("style");
		HtmlBasedComponent element = generateElement(comp, styleVal);
		if (hflexVal != null) {
			element.setHflex(hflexVal);
		}
		if (vflexVal != null) {
			element.setVflex(vflexVal);
		}
		if (widthVal != null) {
			element.setWidth(widthVal);
		}
		if (heightVal != null) {
			element.setHeight(heightVal);
		}

		return element;
	}

	private static HtmlBasedComponent generateLayout(String layout) {
		return generateLayout(layout, "200px", "200px");
	}

	private static Window generateView(String title) {
		Window view = new Window(title, "normal", false);
		view.setWidth("512px");
		view.setHeight("384px");
		return view;
	}

	private static void setWidthAndHeight(HtmlBasedComponent e, String width,
			String height) {
		if (width != null && width.length() != 0) {
			e.setWidth(width);
		}
		if (height != null && height.length() != 0) {
			e.setHeight(height);
		}
	}

	public static Div genDiv(String style) {
		Div d = new Div();
		d.setWidth("100%");
		d.setHeight("100%");
		d.setStyle(style);
		d.appendChild(new Label("1"));
		return d;
	}

	private static HtmlBasedComponent applyProperties(
			HtmlBasedComponent layout, String comp, String flex) {
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
		layout.appendChild(generateContent(comp, attrs1));
		layout.appendChild(generateContent(comp, attrs2));
		layout.appendChild(generateContent(comp, attrs3));
		return layout;
	}

	public static Window newMinFlexCase(Window main, String comp,
			String layoutName, String flex) {
		final Window view = generateView("minimum Flex: { componenet: " + comp
				+ ", layout: " + layoutName + " }");

		HtmlBasedComponent layout = generateLayout(layoutName, null, null);
		applyProperties(layout, comp, flex);
		view.appendChild(layout);
		main.appendChild(view);
		main.appendChild(generateCodeView(layout));
		return main;
	}

	private static Window generateCodeView(HtmlBasedComponent layout) {
		String code = buildMinFlexCode(layout);
		Label lb = new Label();
		lb.setMultiline(true);
		lb.setValue(code);
		Window codeView = generateView("Code View");
		codeView.appendChild(lb);
		return codeView;
	}

	private static String buildMinFlexCode(HtmlBasedComponent layout) {
		Element root = unapplyXML(layout, new HashMap<String, Boolean>());
		return new XMLFormatter(root.toXML()).getXML();
	}

	private static void setAttributeIfExists(Element elem, String attrName,
			String val) {
		if (val != null) {
			Attribute attr = new Attribute(attrName, val);
			elem.addAttribute(attr);
		}
	}

	private static Element unapplyXML(HtmlBasedComponent comp,
			Map<String, Boolean> checked) {
		Element self = new Element(comp.getDefinition().getName());

		// unapply children
		for (Component child : comp.getChildren()) {
			String childId = child.getUuid();
			Element childElem = new Element(child.getDefinition().getName());
			if (checked.get(childId) != null) {
				// if it checked, then skip
				continue;
			} else if (child.getChildren().size() > 0) {
				self.appendChild(unapplyXML(((HtmlBasedComponent) child),
						checked));
			} else {
				applyAttrs(childElem, (HtmlBasedComponent) child);
				self.appendChild(childElem);
				checked.put(childId, true);
			}

		}

		// unapply itself after all children is unapplyed
		applyAttrs(self, comp);
		checked.put(comp.getUuid(), true);

		return self;
	}

	private static void applyAttrs(Element elem, HtmlBasedComponent comp) {
		setAttributeIfExists(elem, "hflex", comp.getHflex());
		setAttributeIfExists(elem, "vflex", comp.getVflex());
		setAttributeIfExists(elem, "height", comp.getHeight());
		setAttributeIfExists(elem, "width", comp.getWidth());
		setAttributeIfExists(elem, "style", comp.getStyle());
		if (comp instanceof Label) {
			setAttributeIfExists(elem, "value", ((Label) comp).getValue());
		}
	}

	private static HtmlBasedComponent generateLayout(String layout,
			String width, String height) {
		if (div.equalsIgnoreCase(layout)) {
			Div d = new Div();
			setWidthAndHeight(d, width, height);
			return d;
		} else if (hlayout.equalsIgnoreCase(layout)) {
			Hlayout hl = new Hlayout();
			setWidthAndHeight(hl, width, height);
			return hl;
		} else if (hbox.equalsIgnoreCase(layout)) {
			Hbox hb = new Hbox();
			setWidthAndHeight(hb, width, height);
			return hb;
		} else if (vlayout.equalsIgnoreCase(layout)) {
			Vlayout vl = new Vlayout();
			setWidthAndHeight(vl, width, height);
			return vl;
		} else if (vbox.equalsIgnoreCase(layout)) {
			Vbox vb = new Vbox();
			setWidthAndHeight(vb, width, height);
			return vb;
		}

		else {
			return new XulElement() {
			};
		}
	}

	public static Window newMinFlexCase(Window main, String comp,
			String testcase) {
		if (testcase.equals("vflex")) {
			return newMinFlexCase(main, comp, "div", "vflex");
		} else if (testcase.equals("hflex")) {
			return newMinFlexCase(main, comp, "div", "hflex");
		} else if (testcase.equals("vlayout")) {
			return newMinFlexCase(main, comp, "vlayout", "hflex");
		} else if (testcase.equals("hlayout")) {
			return newMinFlexCase(main, comp, "hlayout", "vflex");
		} else if (testcase.equals("vbox")) {
			return newMinFlexCase(main, comp, "vbox", "hflex");
		} else if (testcase.equals("hbox")) {
			return newMinFlexCase(main, comp, "hbox", "vflex");
		} else
			return null;
	}

	public static Window newLayoutCase(Window main, String comp, String testcase) {
		if (testcase.equals("vflex")) {
			return newLayoutCase(main, comp, "div", "vflex");
		} else if (testcase.equals("hflex")) {
			return newLayoutCase(main, comp, "div", "hflex");
		} else if (testcase.equals("vlayout")) {
			return newLayoutCase(main, comp, "vlayout", "vflex");
		} else if (testcase.equals("hlayout")) {
			return newLayoutCase(main, comp, "hlayout", "hflex");
		} else if (testcase.equals("vbox")) {
			return newLayoutCase(main, comp, "vbox", "vflex");
		} else if (testcase.equals("hbox")) {
			return newLayoutCase(main, comp, "hbox", "hflex");
		} else
			return null;
	}

	private static Window newLayoutCase(Window main, String comp,
			String layoutName, String flex) {
		final Window view = generateView("Proportional Flexibility: { component: "
				+ comp + "}");

		HtmlBasedComponent layout = generateLayout(layoutName);

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

		attrs1.put("style", "background:blue");
		attrs2.put("style", "background:yellow");
		layout.appendChild(generateContent(comp, attrs1));
		layout.appendChild(generateContent(comp, attrs2));

		view.appendChild(layout);
		main.appendChild(view);
		main.appendChild(generateCodeView(layout));
		return main;
	}

	public static Window newVFlexCase(Window main, String comp) {
		final Window view = generateView("Fit-the-Rest Flexibility: { component: "
				+ comp + " }");
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("vflex", "1");
		attrs.put("style", "background:yellow");
		HtmlBasedComponent content = generateContent(comp, attrs);
		view.appendChild(content);
		main.appendChild(view);
		main.appendChild(generateCodeView(content));
		return main;
	}
}
