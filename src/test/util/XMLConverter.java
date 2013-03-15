package test.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import nu.xom.Attribute;
import nu.xom.Element;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Doublespinner;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.LabelElement;

public class XMLConverter {

	private HtmlBasedComponent comp;

	public XMLConverter(HtmlBasedComponent comp) {
		super();
		this.comp = comp;
	}

	private static void setAttrs(Element elem, HtmlBasedComponent comp) {
		if (!(comp instanceof Panelchildren)) {
			setAttrIfExists(elem, "hflex", comp.getHflex());
			setAttrIfExists(elem, "vflex", comp.getVflex());
		}
		setAttrIfExists(elem, "height", comp.getHeight());
		setAttrIfExists(elem, "width", comp.getWidth());
		setAttrIfExists(elem, "style", comp.getStyle());
		setAttrIfExists(elem, "mold", comp.getMold());

		if (comp instanceof Label) {
			setAttrIfExists(elem, "value", ((Label) comp).getValue());
		} else if (comp instanceof LabelElement) {
			setAttrIfExists(elem, "label", ((LabelElement) comp).getLabel());
		} else if (comp instanceof Doublespinner) {
			setAttrIfExists(elem, "value", ((Doublespinner) comp).getValue());
		} else if (comp instanceof Spinner) {
			setAttrIfExists(elem, "value", ((Spinner) comp).getValue());
		} else if (comp instanceof Decimalbox) {
			setAttrIfExists(elem, "value", ((Decimalbox) comp).getValue());
		} else if (comp instanceof Doublebox) {
			setAttrIfExists(elem, "value", ((Doublebox) comp).getValue());
		} else if (comp instanceof Intbox) {
			setAttrIfExists(elem, "value", ((Intbox) comp).getValue());
		} else if (comp instanceof Longbox) {
			setAttrIfExists(elem, "value", ((Longbox) comp).getValue());
		} else if (comp instanceof Textbox) {
			setAttrIfExists(elem, "value", ((Textbox) comp).getValue());
		} else if (comp instanceof Window) {
			setAttrIfExists(elem, "title", ((Window) comp).getTitle());
			setAttrIfExists(elem, "border", ((Window) comp).getBorder());
		}
	}

	private static void setAttrIfExists(Element elem, String attrName,
			Object val) {
		if (val != null) {
			String value = val.toString();
			if (value.length() > 0 && !"default|vertical|horizontal".contains(value))
				elem.addAttribute(new Attribute(attrName, value));
		}
	}

	private Element unapply() {
		return unapply(comp, new HashMap<String, Boolean>());
	}

	private Element unapply(HtmlBasedComponent comp,
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
				self.appendChild(unapply(((HtmlBasedComponent) child), checked));
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

	public String toXML() {
		Element root = unapply();
		return new XMLFormatter(root.toXML()).getXML();
	}

	public static String toRawHTML(String xml) {
		String code = Pattern.compile("<").matcher(xml).replaceAll("&lt;");
		code = Pattern.compile(">").matcher(code).replaceAll("&gt;");
		code = Pattern.compile("\\r?\\n").matcher(code).replaceAll("<br/>");
		code = Pattern.compile("\\s").matcher(code).replaceAll("&nbsp;");
		return code;
	}

	public String toRawHTML() {
		return toRawHTML(this.toXML());
	}
}
