package test.ctrl;

import static test.cases.HFlexVFlexGenerator.newLayoutCase;
import static test.cases.HFlexVFlexGenerator.newMinFlexCase;
import static test.cases.HFlexVFlexGenerator.newVFlexCase;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

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
		newVFlexCase(main, "panel");
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

	@Listen("onChange= #testcase; onChange= #component; onChange= #layout")
	public void generate() {
		String testcaseVal = testcase.getValue().toString();
		String componentVal = component.getValue().toString();
		String layoutVal = layout.getValue().toString();
		if (testcaseVal.equalsIgnoreCase("VFlexCase")) {
			reset();
			newVFlexCase(main, componentVal);
		} else if (testcaseVal.equalsIgnoreCase("LayoutCase")) {
			reset();
			newLayoutCase(main, componentVal, layoutVal);
		} else if (testcaseVal.equalsIgnoreCase("MinFlexCase")) {
			reset();
			newMinFlexCase(main, componentVal, layoutVal);
		}
	}

}