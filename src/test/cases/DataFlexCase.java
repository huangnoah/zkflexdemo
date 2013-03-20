package test.cases;

import static test.cases.HFlexVFlexGenerator.generateView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.json.JSONValue;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.impl.MeshElement;

public class DataFlexCase extends FlexCase {
	private MeshElement comp;

	public DataFlexCase(String compName, Map<String, String> props, boolean span) {
		boolean isSizedByContent = props.containsKey("sizedByContent");
		if (!isSizedByContent)
			generateDataComp(compName, props, span);
		else
			generateDataComp(compName, span);

		List<String> subtitle = new ArrayList<String>();
		subtitle.add(compName);
		if (span)
			subtitle.add("span");
		if (isSizedByContent)
			subtitle.add("sizedByContent");
		else
			for (Entry<String, String> tuple : props.entrySet()) {
				String key = tuple.getKey();
				String prop = (key.startsWith("hflex") ? "hflex" : key) + "="
						+ tuple.getValue();
				subtitle.add(prop);
			}
		view = generateView("data flex test: " + toString(subtitle));
		if (comp != null) {
			if (isSizedByContent)
				comp.setSizedByContent(true);
			comp.setSpan(span);
			init(comp);
		}
	}

	private static String toString(List<String> ls) {
		return JSONValue.toJSONString(ls).replaceAll("\"", "");
	}

	private void generateDataComp(String compName, boolean span) {
		if ("grid".equalsIgnoreCase(compName)) {
			comp = new Grid();

			// head
			Columns cols = new Columns();

			for (String label : Arrays.asList("item 1", "item 2", "item 3")) {
				cols.appendChild(new Column(label));
			}

			comp.appendChild(cols);

			// body
			Rows rows = new Rows();
			rows.appendChild(generateRow("item 1", span, true));
			rows.appendChild(generateRow("item 2", false, false));
			rows.appendChild(generateRow("item 3", span, false));
			comp.appendChild(rows);

		} else if ("listbox".equalsIgnoreCase(compName)) {
			comp = new Listbox();

			// head
			Listhead head = new Listhead();

			for (String label : Arrays.asList("item 1", "item 2", "item 3")) {
				head.appendChild(new Listheader(label));
			}

			comp.appendChild(head);

			// body
			comp.appendChild(generateListitem("item 1", span, true));
			comp.appendChild(generateListitem("item 2", false, false));
			comp.appendChild(generateListitem("item 3", span, false));

		} else if ("tree".equalsIgnoreCase(compName)) {
			comp = new Tree();

			// head
			Treecols head = new Treecols();

			for (String label : Arrays.asList("item 1", "item 2", "item 3")) {
				head.appendChild(new Treecol(label));
			}
			comp.appendChild(head);

			// body
			Treechildren tc = new Treechildren();
			tc.appendChild(generateTreeitem("item 1", span, true));
			tc.appendChild(generateTreeitem("item 2", false, false));

			// append Treechildren start
			Treeitem ti = (Treeitem) tc.getChildren().get(1);

			Treechildren subTreechildren = new Treechildren();

			Treeitem firstChild = new Treeitem();
			Treerow row1 = new Treerow();
			row1.appendChild(new Treecell("Item 2.1"));
			firstChild.appendChild(row1);

			Treeitem secondChild = new Treeitem();
			Treerow row2 = new Treerow();
			row2.appendChild(new Treecell("Item 2.2"));
			row2.appendChild(new Treecell("Item 2.2 desc"));
			secondChild.appendChild(row2);

			subTreechildren.appendChild(firstChild);
			subTreechildren.appendChild(secondChild);

			ti.appendChild(subTreechildren);
			// append Treechildren end

			tc.appendChild(generateTreeitem("item 3", span, false));
			comp.appendChild(tc);
		}
	}

	private void generateDataComp(String compName, Map<String, String> props,
			boolean span) {
		if ("grid".equalsIgnoreCase(compName)) {
			comp = new Grid();

			// head
			Columns cols = new Columns();

			for (Entry<String, String> tuple : props.entrySet()) {
				String key = tuple.getKey().startsWith("hflex") ? "hflex"
						: tuple.getKey();
				String val = tuple.getValue();
				String prop = key + "=" + val;
				Column col = new Column();
				if ("width".equalsIgnoreCase(key)) {
					col.setLabel(prop);
					col.setWidth(val);
				} else if (key.startsWith("hflex")) {
					col.setLabel(prop);
					col.setHflex(val);
				}
				cols.appendChild(col);
			}

			comp.appendChild(cols);

			// body
			Rows rows = new Rows();
			rows.appendChild(generateRow("item 1", span, true));
			rows.appendChild(generateRow("item 2", false, false));
			rows.appendChild(generateRow("item 3", span, false));
			comp.appendChild(rows);

		} else if ("listbox".equalsIgnoreCase(compName)) {
			comp = new Listbox();

			// head
			Listhead head = new Listhead();

			for (Entry<String, String> tuple : props.entrySet()) {
				String key = tuple.getKey().startsWith("hflex") ? "hflex"
						: tuple.getKey();
				String val = tuple.getValue();
				String prop = key + "=" + val;
				Listheader header = new Listheader();
				if ("width".equalsIgnoreCase(key)) {
					header.setLabel(prop);
					header.setWidth(val);
				} else if (key.startsWith("hflex")) {
					header.setLabel(prop);
					header.setHflex(val);
				}
				head.appendChild(header);
			}

			comp.appendChild(head);

			// body
			comp.appendChild(generateListitem("item 1", span, true));
			comp.appendChild(generateListitem("item 2", false, false));
			comp.appendChild(generateListitem("item 3", span, false));

		} else if ("tree".equalsIgnoreCase(compName)) {
			comp = new Tree();

			// head
			Treecols head = new Treecols();

			for (Entry<String, String> tuple : props.entrySet()) {
				String key = tuple.getKey().startsWith("hflex") ? "hflex"
						: tuple.getKey();
				String val = tuple.getValue();
				String prop = key + "=" + val;
				Treecol header = new Treecol();
				if ("width".equalsIgnoreCase(key)) {
					header.setLabel(prop);
					header.setWidth(val);
				} else if (key.startsWith("hflex")) {
					header.setLabel(prop);
					header.setHflex(val);
				}
				head.appendChild(header);
			}

			comp.appendChild(head);

			// body
			Treechildren tc = new Treechildren();
			tc.appendChild(generateTreeitem("item 1", span, true));
			tc.appendChild(generateTreeitem("item 2", false, false));

			// append Treechildren start
			Treeitem ti = (Treeitem) tc.getChildren().get(1);

			Treechildren subTreechildren = new Treechildren();

			Treeitem firstChild = new Treeitem();
			Treerow row1 = new Treerow();
			row1.appendChild(new Treecell("Item 2.1"));
			firstChild.appendChild(row1);

			Treeitem secondChild = new Treeitem();
			Treerow row2 = new Treerow();
			row2.appendChild(new Treecell("Item 2.2"));
			row2.appendChild(new Treecell("Item 2.2 desc"));
			secondChild.appendChild(row2);

			subTreechildren.appendChild(firstChild);
			subTreechildren.appendChild(secondChild);

			ti.appendChild(subTreechildren);
			// append Treechildren end

			tc.appendChild(generateTreeitem("item 3", span, false));
			comp.appendChild(tc);
		}
	}

	private Treeitem generateTreeitem(String label, boolean span, boolean first) {
		Treeitem treeitem = new Treeitem();
		Treerow treerow = new Treerow();
		treeitem.appendChild(treerow);
		if (!span) {
			treerow.appendChild(new Treecell(label));
			treerow.appendChild(new Treecell(label + " desc"));
			treerow.appendChild(new Treecell(label));
		} else if (span && first) {
			Treecell cell = new Treecell();
			cell.setStyle("background: cyan");
			cell.setSpan(2);
			cell.setLabel(label);
			treerow.appendChild(cell);
			treerow.appendChild(new Treecell(label));
		} else {
			Treecell cell = new Treecell(label + " desc");
			cell.setStyle("background: cyan");
			cell.setSpan(2);
			treerow.appendChild(new Treecell(label));
			treerow.appendChild(cell);
		}
		return treeitem;
	}

	private Listitem generateListitem(String label, boolean span, boolean first) {
		Listitem listitem = new Listitem();
		if (!span) {
			listitem.appendChild(new Listcell(label));
			listitem.appendChild(new Listcell(label + " desc"));
			listitem.appendChild(new Listcell(label));
		} else if (span && first) {
			Listcell cell = new Listcell();
			cell.setStyle("background: cyan");
			cell.setSpan(2);
			cell.setLabel(label);
			listitem.appendChild(cell);
			listitem.appendChild(new Listcell(label));
		} else {
			Listcell cell = new Listcell(label + " desc");
			cell.setStyle("background: cyan");
			cell.setSpan(2);
			listitem.appendChild(new Listcell(label));
			listitem.appendChild(cell);
		}
		return listitem;
	}

	private HtmlBasedComponent generateRow(String label, boolean span,
			boolean first) {
		Row row = new Row();
		if (!span) {
			row.appendChild(new Label(label));
			row.appendChild(new Label(label + " desc"));
			row.appendChild(new Label(label));
		} else if (span && first) {
			Cell cell = new Cell();
			cell.setStyle("background: cyan");
			cell.setColspan(2);
			cell.appendChild(new Label(label));
			row.appendChild(cell);
			row.appendChild(new Label(label));
		} else {
			Cell cell = new Cell();
			cell.setStyle("background: cyan");
			cell.setColspan(2);
			cell.appendChild(new Label(label + " desc"));
			row.appendChild(new Label(label));
			row.appendChild(cell);
		}
		return row;
	}
}
