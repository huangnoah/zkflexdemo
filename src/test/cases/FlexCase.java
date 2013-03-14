package test.cases;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Window;

import test.util.XMLConverter;

public abstract class FlexCase {

	protected Window view;
	protected Window codeView;
	protected String rawXML;
	protected String rawHTMLCode;

	public Window getView() {
		return view;
	}

	public Window getCodeView() {
		return codeView;
	}

	public String getRawXML() {
		return rawXML;
	}

	public String getRawHTMLCode() {
		return rawHTMLCode;
	}

	protected void setCode(HtmlBasedComponent layout) {
		XMLConverter conv = new XMLConverter(layout);
		rawXML = conv.toXML();
		rawHTMLCode = conv.toHTML(rawXML);
	}

}