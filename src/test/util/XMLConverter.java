package test.util;

import java.util.HashMap;
import java.util.Map;

import nu.xom.Attribute;
import nu.xom.Element;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Label;

public class XMLConverter {
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

	private static void setAttrIfExists(Element elem, String attrName,
			String val) {
		if (val != null) {
			Attribute attr = new Attribute(attrName, val);
			elem.addAttribute(attr);
		}
	}

	public static Element unapplyElement(HtmlBasedComponent comp) {
		return unapplyXML(comp, new HashMap<String, Boolean>());
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
	
	public static String unapplyXML(HtmlBasedComponent layout) {
		Element root = unapplyElement(layout);
		return new XMLFormatter(root.toXML()).getXML();
	}
}
