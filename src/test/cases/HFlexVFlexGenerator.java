package test.cases;

import static test.util.XMLConverter.unapplyXML;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.zkoss.lang.Strings;
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

public class HFlexVFlexGenerator {

	public static final String div = "Div";
	public static final String vlayout = "Vlayout";
	public static final String vbox = "Vbox";
	public static final String hlayout = "Hlayout";
	public static final String hbox = "Hbox";
	public static final String panel = "panel";
	public static final String window = "Window";
	public static final String groupbox = "Groupbox";
	public static final String tabbox = "Tabbox";

	private static HtmlBasedComponent generateElement(String comp,
			String style, boolean isDiv) throws Exception {
		if (panel.equalsIgnoreCase(comp)) {
			Panel p = new Panel();
			Panelchildren pc = new Panelchildren();
			pc.appendChild(generateDiv(style));
			p.appendChild(pc);
			return p;
		} else if (window.equalsIgnoreCase(comp)) {
			Window w = new Window();
			w.appendChild(generateDiv(style));
			return w;
		} else if (groupbox.equalsIgnoreCase(comp)) {
			Groupbox g = new Groupbox();
			g.appendChild(generateDiv(style));
			return g;
		} else if (tabbox.equalsIgnoreCase(comp)) {
			Tabbox tabbox = new Tabbox();
			Tabs tabs = new Tabs();
			Tab tab = new Tab("case");
			Tabpanels tabpanels = new Tabpanels();
			Tabpanel tabpanel = new Tabpanel();
			tabs.appendChild(tab);
			tabpanel.appendChild(generateDiv(style));
			tabpanels.appendChild(tabpanel);
			tabbox.appendChild(tabs);
			tabbox.appendChild(tabpanels);
			return tabbox;
		} else if (vlayout.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(vlayout);
			if (!(layout instanceof Vlayout)) {
				throw new Exception(layout + "can't cast to valyout");
			} else {
				LayoutCase.applyProperties(layout, div, "vflex");
			}
			return layout;
		} else if (vbox.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(vbox);
			if (!(layout instanceof Vbox)) {
				throw new Exception(layout + "can't cast to vbox");
			} else {
				LayoutCase.applyProperties(layout, div, "vflex");
			}
			return layout;
		} else if (hlayout.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(hlayout);
			if (!(layout instanceof Hlayout)) {
				throw new Exception(layout + "can't cast to hlayout");
			} else {
				LayoutCase.applyProperties(layout, div, "hflex");
			}
			return layout;
		} else if (hbox.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(hbox);
			if (!(layout instanceof Hbox)) {
				throw new Exception(layout + "can't cast to hbox");
			} else {
				LayoutCase.applyProperties(layout, div, "hflex");
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
					component.appendChild(isDiv ? generateDiv(style)
							: new Label("1"));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return component;
		}
	}

	public static HtmlBasedComponent generateContent(String comp,
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

	public static HtmlBasedComponent generateLayout(String layout) {
		return generateLayout(layout, "200px", "200px");
	}

	public static Window generateView(String title) {
		Window view = new Window(title, "normal", false);
		view.setWidth("600px");
		view.setHeight("450px");
		return view;
	}

	public static Div generateDiv(String style) {
		Div d = new Div();
		d.setWidth("100%");
		d.setHeight("100%");
		d.setStyle(style);
		d.appendChild(new Label("1"));
		return d;
	}

	public static void fixWidthAndHeight(HtmlBasedComponent layout) {
		if (!Strings.isBlank(layout.getHeight())
				&& !Strings.isBlank(layout.getVflex()))
			layout.setHeight(null);
		if (!Strings.isBlank(layout.getWidth())
				&& !Strings.isBlank(layout.getHflex()))
			layout.setWidth(null);

		for (Component rawChild : layout.getChildren()) {
			HtmlBasedComponent child = (HtmlBasedComponent) rawChild;
			if (!Strings.isBlank(child.getHeight())
					&& !Strings.isBlank(child.getVflex()))
				child.setHeight(null);
			if (!Strings.isBlank(child.getWidth())
					&& !Strings.isBlank(child.getHflex()))
				child.setWidth(null);
		}
	}

	public static Window generateCodeView(HtmlBasedComponent layout) {
		String code = unapplyXML(layout);
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

	static HtmlBasedComponent generateLayout(String layout,
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

}
