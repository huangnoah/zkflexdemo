package test.cases;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import nu.xom.Attribute;
import nu.xom.Element;

import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Layout;
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

import test.util.XMLFormatter;

public class HFlexVFlexGenerator {

	private static final String div = "Div";
	private static final String vlayout = "Vlayout";
	private static final String vbox = "Vbox";
	private static final String hlayout = "Hlayout";
	private static final String hbox = "Hbox";
	private static final String panel = "panel";
	private static final String window = "Window";
	private static final String groupbox = "Groupbox";
	private static final String tabbox = "Tabbox";

	private static HtmlBasedComponent generateElement(String comp,
			String style, boolean isDiv) throws Exception {
		if (panel.equalsIgnoreCase(comp)) {
			Panel p = new Panel();
			Panelchildren pc = new Panelchildren();
			pc.appendChild(genDiv(style));
			p.appendChild(pc);
			return p;
		} else if (window.equalsIgnoreCase(comp)) {
			Window w = new Window();
			w.appendChild(genDiv(style));
			return w;
		} else if (groupbox.equalsIgnoreCase(comp)) {
			Groupbox g = new Groupbox();
			g.appendChild(genDiv(style));
			return g;
		} else if (tabbox.equalsIgnoreCase(comp)) {
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
		} else if (vlayout.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(vlayout);
			if (!(layout instanceof Vlayout)) {
				throw new Exception(layout + "can't cast to valyout");
			} else {
				applyPropertiesLayoutCase(layout, div, "vflex");
			}
			return layout;
		} else if (vbox.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(vbox);
			if (!(layout instanceof Vbox)) {
				throw new Exception(layout + "can't cast to vbox");
			} else {
				applyPropertiesLayoutCase(layout, div, "vflex");
			}
			return layout;
		} else if (hlayout.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(hlayout);
			if (!(layout instanceof Hlayout)) {
				throw new Exception(layout + "can't cast to hlayout");
			} else {
				applyPropertiesLayoutCase(layout, div, "hflex");
			}
			return layout;
		} else if (hbox.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(hbox);
			if (!(layout instanceof Hbox)) {
				throw new Exception(layout + "can't cast to hbox");
			} else {
				applyPropertiesLayoutCase(layout, div, "hflex");
			}
			return layout;
		} else {
			HtmlBasedComponent component = null;
			try {
				Class<?> clazz = null;
				try {
					clazz = Class.forName("org.zkoss.zul." + comp);
				} catch (ClassNotFoundException e) {
					try {
						clazz = Class.forName("org.zkoss.zkex.zul." + comp);
					} catch (ClassNotFoundException e1) {
						try {
							clazz = Class
									.forName("org.zkoss.zkmax.zul." + comp);
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
					}
				}
				component = (HtmlBasedComponent) clazz.newInstance();
				component.setStyle(style);

				if (!div.equalsIgnoreCase(comp))
					component.appendChild(new Label("1"));
				else if (!(component instanceof org.zkoss.zul.impl.InputElement))
					component.appendChild(isDiv ? genDiv(style)
							: new Label("1"));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return component;
		}
	}

	private static HtmlBasedComponent generateContent(String comp,
			Map<String, String> flex, boolean div) throws Exception {

		String hflexVal = flex.get("hflex");
		String vflexVal = flex.get("vflex");
		String widthVal = flex.get("width");
		String heightVal = flex.get("height");
		String styleVal = flex.get("style");
		HtmlBasedComponent element = generateElement(comp, styleVal, div);
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
		view.setWidth("600px");
		view.setHeight("450px");
		return view;
	}

	public static Div genDiv(String style) {
		Div d = new Div();
		d.setWidth("100%");
		d.setHeight("100%");
		d.setStyle(style);
		d.appendChild(new Label("1"));
		return d;
	}

	private static HtmlBasedComponent applyPropertiesMinFlexCase(
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

	public static Window newMinFlexCase(Window main, String comp,
			String layoutName, String flex) throws Exception {
		final Window view = generateView("minimum Flex: { componenet: " + comp
				+ ", layout: " + layoutName + " }");

		HtmlBasedComponent layout = generateLayout(layoutName, null, null);
		applyPropertiesMinFlexCase(layout, comp, flex);
		fixWidthAndHeight(layout);
		view.appendChild(layout);
		main.appendChild(view);
		main.appendChild(generateCodeView(layout));
		return main;
	}

	private static void fixWidthAndHeight(HtmlBasedComponent layout) {
		// if(child instanceof Layout) {
		if (!Strings.isBlank(layout.getHeight()) && !Strings.isBlank(layout.getVflex()))
			layout.setHeight(null);
		if (!Strings.isBlank(layout.getWidth()) && !Strings.isBlank(layout.getHflex()))
			layout.setWidth(null);
		
		for (Component rawChild : layout.getChildren()) {
			HtmlBasedComponent child = (HtmlBasedComponent) rawChild;
			// if(child instanceof Layout) {
			if (!Strings.isBlank(child.getHeight()) && !Strings.isBlank(child.getVflex()))
				child.setHeight(null);
			if (!Strings.isBlank(child.getWidth()) && !Strings.isBlank(child.getHflex()))
				child.setWidth(null);
			// }
		}

	}
	

	private static Window generateCodeView(HtmlBasedComponent layout) {
		String code = buildMinFlexCode(layout);
		Window codeView = generateView("Code View");
		code = Pattern.compile("<").matcher(code).replaceAll("&lt;");
		code = Pattern.compile(">").matcher(code).replaceAll("&gt;");
		code = Pattern.compile("\\r?\\n").matcher(code).replaceAll("<br/>");
		code = Pattern.compile("\\s").matcher(code).replaceAll("&nbsp;");
		Html html = new Html();
		html.setContent("<code>" + code + "</code>");
		codeView.appendChild(html);
		return codeView;
	}

	private static String buildMinFlexCode(HtmlBasedComponent layout) {
		Element root = unapplyXML(layout, new HashMap<String, Boolean>());
		return new XMLFormatter(root.toXML()).getXML();
	}

	private static void setAttrIfExists(Element elem, String attrName,
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
				setAttrs(childElem, (HtmlBasedComponent) child);
				self.appendChild(childElem);
				checked.put(childId, true);
			}

		}

		// unapply itself after all children is unapplyed
		setAttrs(self, comp);
		checked.put(comp.getUuid(), true);

		return self;
	}

	private static void setAttrs(Element elem, HtmlBasedComponent comp) {
		setAttrIfExists(elem, "hflex", comp.getHflex());
		setAttrIfExists(elem, "vflex", comp.getVflex());
		setAttrIfExists(elem, "height", comp.getHeight());
		setAttrIfExists(elem, "width", comp.getWidth());
		setAttrIfExists(elem, "style", comp.getStyle());
		if (comp instanceof Label) {
			setAttrIfExists(elem, "value", ((Label) comp).getValue());
		}
	}

	private static HtmlBasedComponent generateLayout(String layout,
			String width, String height) {
		HtmlBasedComponent component = null;
		try {
			Class<?> clazz = Class.forName("org.zkoss.zul." + layout);
			component = (HtmlBasedComponent) clazz.newInstance();
			if (width != null && width.length() != 0) {
				component.setWidth(width);
			}
			if (height != null && height.length() != 0) {
				component.setHeight(height);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return component;
	}

	public static Window newMinFlexCase(Window main, String comp,
			String testcase) throws Exception {
		if (testcase.endsWith("flex")) {
			return newMinFlexCase(main, comp, div, testcase);
		} else if (testcase.startsWith("V")) {
			return newMinFlexCase(main, comp, testcase, "hflex");
		} else if (testcase.startsWith("H")) {
			return newMinFlexCase(main, comp, testcase, "vflex");
		} else
			return null;
	}

	public static Window newLayoutCase(Window main, String comp, String testcase)
			throws Exception {
		if (testcase.endsWith("flex")) {
			return newLayoutCase(main, comp, div, testcase);
		} else if (testcase.startsWith("V")) {
			return newLayoutCase(main, comp, testcase, "vflex");
		} else if (testcase.startsWith("H")) {
			return newLayoutCase(main, comp, testcase, "hflex");
		} else
			return null;
	}

	private static Window newLayoutCase(Window main, String comp,
			String layoutName, String flex) throws Exception {
		final Window view = generateView("Proportional Flexibility: { component: "
				+ comp + "}");

		HtmlBasedComponent layout = generateLayout(layoutName);
		applyPropertiesLayoutCase(layout, comp, flex);
		fixWidthAndHeight(layout);
		
		view.appendChild(layout);
		main.appendChild(view);
		main.appendChild(generateCodeView(layout));
		return main;
	}

	private static void applyPropertiesLayoutCase(HtmlBasedComponent layout,
			String comp, String flex) throws Exception {
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
		layout.appendChild(generateContent(comp, attrs1, false));
		layout.appendChild(generateContent(comp, attrs2, false));
	}

	public static Window newVFlexCase(Window main, String comp)
			throws Exception {
		final Window view = generateView("Fit-the-Rest Flexibility: { component: "
				+ comp + " }");
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("vflex", "1");
		attrs.put("style", "background:yellow");
		HtmlBasedComponent content = generateContent(comp, attrs, true);
		fixWidthAndHeight(content);
		view.appendChild(content);
		main.appendChild(view);
		main.appendChild(generateCodeView(content));
		return main;
	}
}
