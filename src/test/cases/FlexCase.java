package test.cases;

import static test.cases.HFlexVFlexGenerator.fixWidthAndHeight;
import static test.cases.HFlexVFlexGenerator.generateCodeView;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Window;

import test.util.XMLConverter;

public class FlexCase {

	protected Window view;
	protected Window codeView;
	protected String xml;
	protected String rawHTML;

	protected void init(HtmlBasedComponent comp) {
		fixWidthAndHeight(comp);
		view.appendChild(comp);
		XMLConverter conv = new XMLConverter(comp);
		xml = conv.toXML();
		rawHTML = XMLConverter.toRawHTML(xml);
		codeView = generateCodeView(comp, rawHTML);
	}

	public Window getView() {
		return view;
	}

	public Window getCodeView() {
		return codeView;
	}

	public String getXml() {
		return xml;
	}

	public String getRawHTML() {
		return rawHTML;
	}

	public String getViewXML() {
		return new XMLConverter(view).toXML();
	}

}