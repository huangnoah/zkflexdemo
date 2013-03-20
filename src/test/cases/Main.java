package test.cases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Hbox;

import test.util.XMLConverter;

public class Main {
	private static int count = 0;
	private static final List<String> comps = Arrays.asList("Div", "Panel",
			"Window", "Groupbox", "Tabbox", "Hlayout", "Hbox", "Vlayout",
			"Vbox");
	private static final List<String> inps = Arrays.asList("Bandbox",
			"Calendar", "Chosenbox", "Colorbox", "Combobox", "Datebox",
			"Decimalbox", "Doublebox", "Intbox", "Longbox", "Textbox",
			"Timebox", "Doublespinner", "Spinner", "Slider");
	private static final List<String> dataComps = Arrays.asList("grid", "listbox",
			"tree");
	private static final Map<String, String> w_m_h1 = new HashMap<String, String>();
	private static final Map<String, String> w_h1_h2 = new HashMap<String, String>();
	private static final Map<String, String> m_m_h1 = new HashMap<String, String>();
	private static final Map<String, String> h1_h1_h1 = new HashMap<String, String>();
	private static final Map<String, String> m_m_m = new HashMap<String, String>();
	private static final Map<String, String> sizedByContent = new HashMap<String, String>();
	
	static {
		w_m_h1.put("width", "120px");
		w_m_h1.put("hflex0", "min");
		w_m_h1.put("hflex1", "1");
		
		w_h1_h2.put("width", "120px");
		w_h1_h2.put("hflex0", "1");
		w_h1_h2.put("hflex1", "2");
		
		m_m_h1.put("hflex0", "min");
		m_m_h1.put("hflex1", "min");
		m_m_h1.put("hflex2", "1");
		
		h1_h1_h1.put("hflex0", "1");
		h1_h1_h1.put("hflex1", "1");
		h1_h1_h1.put("hflex2", "1");
		
		m_m_m.put("hflex0", "min");
		m_m_m.put("hflex1", "min");
		m_m_m.put("hflex2", "min");
		
		sizedByContent.put("sizedByContent", "true");
	}
	
	public static void main(String[] args) throws Exception {

		for (String comp : comps) {
			outputCase(new VFlexCase(comp, false));

			outputCase(new LayoutCase(comp, "Hlayout", false), 
					   new LayoutCase(comp, "Hbox", false));
			outputCase(new LayoutCase(comp, "Vlayout", false), 
					   new LayoutCase(comp, "Vbox", false));

			outputCase(new MinFlexCase(comp, "Hlayout", false),
					   new MinFlexCase(comp, "Hbox", false));
			outputCase(new MinFlexCase(comp, "Vlayout", false),
					   new MinFlexCase(comp, "Vbox", false));
		}

		for (String inp : inps) {

			outputCase(new VFlexCase(inp, true), 
					   new VFlexCase(inp, false));

			outputCase(new LayoutCase(inp, "Hlayout", false), 
					   new LayoutCase(inp, "Hbox", false), 
					   new LayoutCase(inp, "Hlayout", true),
					   new LayoutCase(inp, "Hbox", true));
			
			outputCase(new LayoutCase(inp, "Vlayout", false), 
					   new LayoutCase(inp, "Vbox", false), 
					   new LayoutCase(inp, "Vlayout", true),
					   new LayoutCase(inp, "Vbox", true));

			outputCase(new MinFlexCase(inp, "Hlayout", false), 
					   new MinFlexCase(inp, "Hbox", false), 
					   new MinFlexCase(inp, "Hlayout", true),
					   new MinFlexCase(inp, "Hbox", true));
			
			outputCase(new MinFlexCase(inp, "Vlayout", false), 
					   new MinFlexCase(inp, "Vbox", false), 
					   new MinFlexCase(inp, "Vlayout", true),
					   new MinFlexCase(inp, "Vbox", true));

			outputCase(new InputHFlexCase(inp, false), 
					   new InputHFlexCase(inp, true));
		}
		
		count = 137;
		
		for (String compName : dataComps) {
			outputCase(new DataFlexCase(compName, w_m_h1, true),
					   new DataFlexCase(compName, w_m_h1, false),
					   new DataFlexCase(compName, w_h1_h2, true),
					   new DataFlexCase(compName, w_h1_h2, false));
			
			outputCase(new DataFlexCase(compName, m_m_h1, true),
					   new DataFlexCase(compName, m_m_h1, false),
					   new DataFlexCase(compName, h1_h1_h1, true),
					   new DataFlexCase(compName, h1_h1_h1, false));
			
			outputCase(new DataFlexCase(compName, m_m_m, true),
					   new DataFlexCase(compName, m_m_m, false),
					   new DataFlexCase(compName, sizedByContent, true),
					   new DataFlexCase(compName, sizedByContent, false));
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

	private static void outputCase(FlexCase... flexCases) throws Exception {
		StringBuffer flexCaseXML = new StringBuffer("<zk>");
		Hbox hbox1 = new Hbox();
		Hbox hbox2 = new Hbox();
		for (int i = 0; i < flexCases.length; i++) {
			FlexCase flexCase = flexCases[i];

			switch (flexCases.length) {
			case 1:
				flexCaseXML.append(flexCase.getViewXML());
				break;
			case 2:
				hbox1.appendChild(flexCase.getView());
				if (i == 1)
					flexCaseXML.append(new XMLConverter(hbox1).toXML());
				break;
			case 4:
				if (i < 2)
					hbox1.appendChild(flexCase.getView());
				else if (i > 1)
					hbox2.appendChild(flexCase.getView());
				if (i == 1)
					flexCaseXML.append(new XMLConverter(hbox1).toXML());
				if (i == 3)
					flexCaseXML.append(new XMLConverter(hbox2).toXML());
				break;
			}

		}
		flexCaseXML.append("</zk>");

		File ztl = generateZTL();
		save(flexCaseXML.toString(), ztl);
		System.out.println("save to file " + ztl.getName());
		System.out.println("file contnet: \n" + flexCaseXML + "\n");
	}
}
