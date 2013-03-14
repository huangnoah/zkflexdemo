package test.cases;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zkex.zul.Colorbox;
import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Bandpopup;
import org.zkoss.zul.Calendar;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Slider;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

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
	public static final String bandbox = "Bandbox";
	public static final String calendar = "Calendar";
	public static final String chosenbox = "Chosenbox";
	public static final String colorbox = "Colorbox";
	public static final String combobox = "Combobox";
	public static final String datebox = "Datebox";
	public static final String decimalbox = "Decimalbox";
	public static final String doublebox = "Doublebox";
	public static final String intbox = "Intbox";
	public static final String longbox = "Longbox";
	public static final String textbox = "Textbox";
	public static final String timebox = "Timebox";
	public static final String doublespinner = "Doublespinner";
	public static final String spinner = "Spinner";
	public static final String slider = "Slider";

	private static HtmlBasedComponent generateElement(String comp,
			String style, boolean isDiv) throws Exception {

		// container
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

			// input
		} else if (doublespinner.equalsIgnoreCase(comp)) {
			return new Doublespinner(0.5);
		} else if (spinner.equalsIgnoreCase(comp)) {
			return new Spinner(2);
		} else if (slider.equalsIgnoreCase(comp)) {
			return new Slider();
		} else if (decimalbox.equalsIgnoreCase(comp)) {
			return new Decimalbox(new BigDecimal(1111111111111L));
		} else if (doublebox.equalsIgnoreCase(comp)) {
			return new Doublebox(1.1);
		} else if (intbox.equalsIgnoreCase(comp)) {
			return new Intbox(1);
		} else if (longbox.equalsIgnoreCase(comp)) {
			return new Longbox(1111111111111L);
		} else if (textbox.equalsIgnoreCase(comp)) {
			return new Textbox("1");
		} else if (timebox.equalsIgnoreCase(comp)) {
			return new Timebox();
		} else if (datebox.equalsIgnoreCase(comp)) {
			return new Datebox();
		} else if (combobox.equalsIgnoreCase(comp)) {
			Combobox combobox = new Combobox();
			Comboitem apple = new Comboitem("apple");
			Comboitem banana = new Comboitem("banana");
			combobox.appendChild(apple);
			combobox.appendChild(banana);
			return combobox;
		} else if (calendar.equalsIgnoreCase(comp)) {
			return new Calendar();
		} else if (chosenbox.equalsIgnoreCase(comp)) {
			Chosenbox chosenbox = new Chosenbox();
			chosenbox.setModel(generateListModel());
			return chosenbox;
		} else if (colorbox.equalsIgnoreCase(comp)) {
			return new Colorbox();
		} else if (bandbox.equalsIgnoreCase(comp)) {
			Bandbox bandbox = new Bandbox();
			Bandpopup bandpopup = new Bandpopup();
			Vbox vbox = new Vbox();
			Listbox listbox = new Listbox();
			listbox.setWidth("200px");
			Listhead listhead = new Listhead();
			Listheader listheader = new Listheader("Name");
			listhead.appendChild(listheader);
			listbox.appendChild(listhead);
			Listitem listitem = new Listitem();
			Listcell listcell = new Listcell("John");
			listitem.appendChild(listcell);
			listbox.appendChild(listitem);
			vbox.appendChild(listbox);
			bandpopup.appendChild(vbox);
			bandbox.appendChild(bandpopup);
			return bandbox;
			// layout
		} else if (vlayout.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(vlayout);
			if (!(layout instanceof Vlayout)) {
				throw new Exception(layout + "can't cast to valyout");
			} else {
				LayoutCase.applyProperties(layout, div, "vflex", false);
			}
			return layout;
		} else if (vbox.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(vbox);
			if (!(layout instanceof Vbox)) {
				throw new Exception(layout + "can't cast to vbox");
			} else {
				LayoutCase.applyProperties(layout, div, "vflex", false);
			}
			return layout;
		} else if (hlayout.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(hlayout);
			if (!(layout instanceof Hlayout)) {
				throw new Exception(layout + "can't cast to hlayout");
			} else {
				LayoutCase.applyProperties(layout, div, "hflex", false);
			}
			return layout;
		} else if (hbox.equalsIgnoreCase(comp)) {
			HtmlBasedComponent layout = generateLayout(hbox);
			if (!(layout instanceof Hbox)) {
				throw new Exception(layout + "can't cast to hbox");
			} else {
				LayoutCase.applyProperties(layout, div, "hflex", false);
			}
			return layout;

			// default
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

	private static ListModelList<String> generateListModel() {
		return new ListModelList<String>(Arrays.asList(new String[] { "apple",
				"banana" }));
	}

	public static HtmlBasedComponent generateContent(String comp, boolean rounded,
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
		if(rounded && element instanceof InputElement){
			((InputElement) element).setMold("rounded");
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

	public static Window generateCodeView(HtmlBasedComponent layout, String code) {
		Window codeView = generateView("Code View");
		Html html = new Html();
		html.setContent("<code>" + code + "</code>");
		codeView.appendChild(html);
		return codeView;
	}

	static HtmlBasedComponent generateLayout(String layout, String width,
			String height) {
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
