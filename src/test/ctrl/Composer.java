package test.ctrl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import test.cases.FlexCase;
import test.cases.LayoutCase;
import test.cases.MinFlexCase;
import test.cases.VFlexCase;

public class Composer extends SelectorComposer {

	@Wire("#main")
	private Window main;
	@Wire("#testcase")
	private Combobox testcase;
	@Wire("#component")
	private Combobox component;
	@Wire("#layout")
	private Combobox layout;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		appendCase(new VFlexCase("Div"));
	}
	
	private void reset() {
		Component lastChild = main.getLastChild();
		if (!(lastChild instanceof Window)
				|| !((Window) lastChild).getTitle().startsWith("Grid")) {
			main.removeChild(lastChild);
		}
		
		Component anotherLastChild = main.getLastChild();
		if (!(anotherLastChild instanceof Window)
				|| !((Window) anotherLastChild).getTitle().startsWith("Grid")) {
			main.removeChild(anotherLastChild);
		}
	}
	
	private void appendCase(FlexCase flexcase) {
		main.appendChild(flexcase.getView());
		main.appendChild(flexcase.getCodeView());
	}

	@Listen("onChange= #testcase; onChange= #component; onChange= #layout")
	public void generate() throws Exception {
		String testcaseVal = testcase.getValue().toString();
		String componentVal = component.getValue().toString();
		String layoutVal = layout.getValue().toString();
		if (testcaseVal.equalsIgnoreCase("VFlexCase")) {
			reset();
			appendCase(new VFlexCase(componentVal));
		} else if (testcaseVal.equalsIgnoreCase("LayoutCase")) {
			reset();
			appendCase(new LayoutCase(componentVal, layoutVal));
		} else if (testcaseVal.equalsIgnoreCase("MinFlexCase")) {
			reset();
			appendCase(new MinFlexCase(componentVal, layoutVal));
		}
	}

}