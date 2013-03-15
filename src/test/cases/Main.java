package test.cases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
	private static int count = 0;
	private static final List<String> comps = Arrays.asList(new String[] { "Div",
			"Panel", "Window", "Groupbox", "Tabbox", "Hlayout", "Hbox",
			"Vlayout", "Vbox" });
	private static final List<String> inps =  Arrays.asList(new String[] {
			"Bandbox", "Calendar", "Chosenbox", "Colorbox", "Combobox",
			"Datebox", "Decimalbox", "Doublebox", "Intbox", "Longbox",
			"Textbox", "Timebox", "Doublespinner", "Spinner", "Slider" });
	private static final List<String> layouts = Arrays.asList(new String[] {
			"Hlayout", "Hbox", "Vlayout", "Vbox" });

	public static void main(String[] args) throws Exception {

		for (String comp : comps) {
			outputCase(new VFlexCase(comp, false));
			for (String layout : layouts) {
				outputCase(new LayoutCase(comp, layout, false));
				outputCase(new MinFlexCase(comp, layout, false));
			}
		}
		
		for (String inp : inps) {
			for (Boolean rounded : Arrays.asList(false, true)) {
				outputCase(new VFlexCase(inp, rounded));
				for (String layout : layouts) {
					outputCase(new LayoutCase(inp, layout, rounded));
					outputCase(new MinFlexCase(inp, layout, rounded));
				}
			}
		}

	}

	private static void save(String content, File file) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(content);
		} catch (IOException ex) {
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static File generateZTL() throws IOException {
		String path = new StringBuffer("WebContent/Z65-Flex-")
				.append(String.format("%03d", ++count)).append(".zul")
				.toString();
		File tmp = new File(path);
		tmp.createNewFile();
		return tmp;
	}
	
	private static void outputCase(FlexCase flexCase) throws Exception {
		String flexCaseXML = flexCase.getRawXML();
		File ztl = generateZTL();
		save(flexCaseXML, ztl);
		System.out.println("save to file " + ztl.getName());
		System.out.println("file contnet: " + flexCaseXML);
	}
}
