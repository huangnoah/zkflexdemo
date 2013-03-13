package test.cases;

import org.zkoss.zul.Window;

public abstract class FlexCase {

	protected Window view;
	protected Window codeView;

	public Window getView() {
		return view;
	}

	public Window getCodeView() {
		return codeView;
	}

}