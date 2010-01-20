package org.tzi.use.main.shell;

import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

import org.tzi.use.config.Options;
import org.tzi.use.gui.util.TextComponentWriter;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.objectselection.SelectionOCLView;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.TeeWriter;

/**
 * responsibly for new functions in console
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class ShowHideExec {
	String line;

	/**
	 * Constructor for ShowHideExec.
	 */
	ShowHideExec(String line) {
		this.line = line;
	}

	/**
	 * Method exec analyze input instruction of user
	 */
	public String exec() {

		if (line.trim().equals("show all")) { 
			NewObjectDiagram.ffHideAdmin.showAllHiddenElements();
			
			NewObjectDiagram.aaaParent.repaint();
			return "";
		} else if (line.trim().equalsIgnoreCase("hide all")) {
			hideAll();
			return "";
		} else if (line.trim().startsWith("show")) {
			if (line.contains("from") && line.contains("(")) {
				return showLinks();
			} else {
				return showObjects();
			}
		} else if (line.trim().startsWith("hide")) {
			if (line.contains("from") && line.contains("(")) {
				return hideLinks();
			} else {
				return hideObjects();
			}
		} else if (line.trim().startsWith("crop")) {
			if (line.contains("crop") && line.contains("(")) {
				return cropLinks();
			} else {
				return cropObjects();
			}
		}
		return "";
	}

	/**
	 * Method hideAll hide all objects.
	 */
	void hideAll() {
		Iterator itt = NewObjectDiagram.ffGraph.iterator();
		final HashSet toHideObjects = new HashSet(); 
		
		while (itt.hasNext()) { 
			Object node = itt.next();
			if (node instanceof ObjectNode) {
				MObject mobj = ((ObjectNode) node).object();
				toHideObjects.add(mobj);
			}
		}
		NewObjectDiagram.ffHideAdmin.setValues("", toHideObjects)
				.actionPerformed(null);
	}

	/**
	 * Method isObjectsInSystem examines whether the given objects are in the system.
	 */
	String isObjectsInSystem() {
		String msg = "";
		if (!isObjectFormatOk())
			return "forma error";

		List objectNames = getObjectNames();
		for (Iterator it2 = objectNames.iterator(); it2.hasNext();) {
			String oname = (String) it2.next();
			boolean have = false;
			if (NewObjectDiagram.ffHiddenNodes != null) {
				Iterator it = NewObjectDiagram.ffHiddenNodes.iterator();
				while (it.hasNext()) {
					Object node = it.next();
					if (node instanceof MObject) {
						MObject mobj = (MObject) node;
						if (oname.equals(mobj.name())) {
							have = true;
							break;
						}
					}
				}
			}
			if (!have)
				if (NewObjectDiagram.ffGraph != null) {
					Iterator it = NewObjectDiagram.ffGraph.iterator();
					boolean haveObject = false;
					while (it.hasNext()) {
						Object node = it.next();
						if (node instanceof ObjectNode) {
							MObject mobj = ((ObjectNode) node).object();
							if (oname.equals(mobj.name())) {
								have = true;
								break;
							}
						}
					}
				}
			if (!have)
				return  "";
		}
		return msg;
	}

	/**
	 * Method cropObjects show only the appropriate given objects.
	 */
	String cropObjects() {
		String msg = "";
		if (!line.contains(":")) {
			return "error command format";
		}

		List objectNames = getObjectNames();
		String cname = getClassName();
		msg = isObjectsInSystem(objectNames, cname);
		if (msg.length() > 0) {
			return msg;
		}
		HashSet objects = new HashSet();
		if (NewObjectDiagram.ffHiddenNodes != null) {
			Iterator it = NewObjectDiagram.ffHiddenNodes.iterator();
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof MObject) {
					MObject mobj = (MObject) node;
					if (isContainName(objectNames, mobj.name())) {
						objects.add(mobj);
					}
				}
			}
		}
		if (NewObjectDiagram.ffGraph != null) {
			Iterator it = NewObjectDiagram.ffGraph.iterator();
			boolean haveObject = false;
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (isContainName(objectNames, mobj.name())) {
						objects.add(mobj);
						haveObject = true;
					}
				}
			}
		}

		if (getHideObjects(objects, true).size() > 0) {
			NewObjectDiagram.ffHideAdmin.setValues("Hide",
					getHideObjects(objects, true)).actionPerformed(null);
		}
		if (getShowObjects(objects).size() > 0) {
			NewObjectDiagram.ffHideAdmin
					.showHiddenElements(getShowObjects(objects));
		}
		return msg;
	}

	public HashSet getHideObjects(HashSet objects, boolean isCrop) {
		HashSet hideobjects = new HashSet();
		Iterator ithide = NewObjectDiagram.ffGraph.iterator(); 
	
		while (ithide.hasNext()) {
			Object node = ithide.next();
			if (node instanceof ObjectNode) {
				MObject mo = ((ObjectNode) node).object();
				if (isCrop) {
					if (!objects.contains(mo)) {
						hideobjects.add(mo);
					}
				} else {
					if (objects.contains(mo)) {
						hideobjects.add(mo);
					}
				}
			}
		}
		return hideobjects;
	}

	public HashSet getShowObjects(HashSet objects) {
		HashSet showobjects = new HashSet();
		Iterator itshow = NewObjectDiagram.ffHiddenNodes.iterator(); // hidenode
		
		while (itshow.hasNext()) {
			Object node = itshow.next();
			if (node instanceof MObject) {
				MObject mo = (MObject) node;
				if (objects.contains(mo)) {
					showobjects.add(mo);
				}
			}
		}
		return showobjects;
	}

	/**
	 * Method showObjects show the appropriate given objects.
	 */
	String showObjects() {
		String err = "";
		if (!line.contains(":")) {
			return "error command format";
		}
		List objectNames = getObjectNames();
		String className = getClassName();
		Set toShowObjects = new HashSet();
	
		String msg = isObjectsInSystem(objectNames, className);
		if (msg.length() > 0) {
			return msg;
		}
		if (NewObjectDiagram.ffHiddenNodes != null) {
			Iterator it = NewObjectDiagram.ffHiddenNodes.iterator();
			boolean haveClass = false;
			boolean haveObject = false;
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof MObject) {
					MObject mobj = (MObject) node;
					if (className.equals(mobj.cls().name())) {
						haveClass = true;
						if (isContainName(objectNames, mobj.name())) {
							toShowObjects.add(mobj);
							haveObject = true;
						}
					}
				}
			}
			if (haveClass) {
				if (haveObject)
					NewObjectDiagram.ffHideAdmin
							.showHiddenElements(toShowObjects);
				else
					err = "have no this object in hide";
			} else
				err = "have no this class in hide";
		}
		return err;
	}

	/**
	 * Method hideObjects hide the appropriate given objects.
	 */
	String hideObjects() {
		String err = "";
		if (!line.contains(":")) {
			return "error command format";
		}
		List objectNames = getObjectNames();
		String className = getClassName();
		Set toHideObjects = new HashSet();
		String msg = isObjectsInSystem(objectNames, className);
		if (msg.length() > 0) {
			return msg;
		}

		if (NewObjectDiagram.ffGraph != null) {
			Iterator it = NewObjectDiagram.ffGraph.iterator();
			boolean haveClass = false;
			boolean haveObject = false;
			while (it.hasNext()) {
				Object node = it.next();

				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (className.equals(mobj.cls().name())) {
						haveClass = true;
						if (isContainName(objectNames, mobj.name())) {
							toHideObjects.add(mobj);
							haveObject = true;
						}
					}
				}
			}
			if (haveClass) {
				if (haveObject)
					NewObjectDiagram.ffHideAdmin.setValues("", toHideObjects)
							.actionPerformed(null);
				else
					err = "have no this object in hide";
			} else
				err = "have no this class in hide";
		}
		return err;
	}

	/**
	 * Method showLinks show the appropriate given links.
	 */
	String showLinks() {
		String message = isLinksInSystem();
		if (message.length() > 0) {
			return message;
		}
		List objectNames = getAssoObjectNames();
		Set toShowObjects = new HashSet();
		if (NewObjectDiagram.ffHiddenNodes != null) {
			Iterator it = NewObjectDiagram.ffHiddenNodes.iterator();
			while (it.hasNext()) {
				Object node = it.next();

				if (node instanceof MObject) {
					MObject mobj = (MObject) node;
					if (isContainName(objectNames, mobj.name())) {
						toShowObjects.add(mobj);
					}
				}
			}
		}
		NewObjectDiagram.ffHideAdmin.showHiddenElements(toShowObjects);
		return message;
	}

	/**
	 * Method hideLinks hide the appropriate given links.
	 */
	String hideLinks() {
		String message = isLinksInSystem();
		if (message.length() > 0) {
			return message;
		}
		List objectNames = getAssoObjectNames();
		Set toHideObjects = new HashSet();
		if (NewObjectDiagram.ffGraph != null) {
			Iterator it = NewObjectDiagram.ffGraph.iterator();
			boolean haveObject = false;
			while (it.hasNext()) {
				Object node = it.next();

				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (isContainName(objectNames, mobj.name())) {
						toHideObjects.add(mobj);
						haveObject = true;
					}
				}
			}
			if (haveObject)
				NewObjectDiagram.ffHideAdmin.setValues("", toHideObjects)
						.actionPerformed(null);
			else
				message = "have no this object to hide";
		}

		return message;
	}

	/**
	 * Method cropLinks shows only the appropriate given links.
	 */
	String cropLinks() {
		String message = isLinksInSystem();
		if (message.length() > 0) {
			System.out.println(message);
			return message;
		}
		List objectNames = getAssoObjectNames();
		HashSet objects = new HashSet();
		if (NewObjectDiagram.ffHiddenNodes != null) {
			Iterator it = NewObjectDiagram.ffHiddenNodes.iterator();
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof MObject) {
					MObject mobj = (MObject) node;
					if (isContainName(objectNames, mobj.name())) {
						objects.add(mobj);
					}
				}
			}
		}
		if (NewObjectDiagram.ffGraph != null) {
			Iterator it = NewObjectDiagram.ffGraph.iterator();
			boolean haveObject = false;
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (isContainName(objectNames, mobj.name())) {
						objects.add(mobj);
						haveObject = true;
					}
				}
			}
		}

		if (getHideObjects(objects, true).size() > 0) {
			NewObjectDiagram.ffHideAdmin.setValues("Hide",
					getHideObjects(objects, true)).actionPerformed(null);
		}
		if (getShowObjects(objects).size() > 0) {
			NewObjectDiagram.ffHideAdmin
					.showHiddenElements(getShowObjects(objects));
		}
		return message;
	}

	/**
	 * Method getAssociationName return name of association.
	 * @return String
	 */
	private String getAssociationName() {
		return line.substring(line.indexOf("from") + 5).trim();
	}

	/**
	 * Method getAssoObjectNames return all objectnames as list.
	 * @return List
	 */
	private List getAssoObjectNames() {
		String objects = line.substring(line.indexOf("(") + 1, line
				.indexOf(")"));
		StringTokenizer ob = new StringTokenizer(objects, ",");
		List objectNames = new ArrayList();
		String associationName = getAssociationName();
		while (ob.hasMoreTokens()) {
			objectNames.add(ob.nextToken().trim());
		}
		return objectNames;
	}

	/**
	 * Method isLinksInSystem examines whether the given links are in the system.
	 */
	String isLinksInSystem() {
		String msg = "";
		if (!isLinkFormatOk())
			return "forma error";
		Object ff = NewObjectDiagram.aaaParent;
		if (NewObjectDiagram.aaaParent == null)
			return "bitte zuerst ObjectDiagramm oeffnen!";
		MAssociation assoc = null;
		boolean haveAssoc = false;

		List assocs = new ArrayList();
		for (Iterator it = NewObjectDiagram.aaaParent.system().model()
				.associations().iterator(); it.hasNext();) {
			assoc = (MAssociation) it.next();
			if (assoc.name().equals(getAssociationName())) {
				haveAssoc = true;
				break;
			}
		}
		if (!haveAssoc) {
			return "have no this association " + getAssociationName()
					+ " in system";
		}

		List objectNames = getAssoObjectNames();

		if (objectNames.size() != assoc.associatedClasses().size())
			return "anzahl stimmt nicht!";
	
		for (Iterator it = objectNames.iterator(); it.hasNext();) {
			boolean have = false;
			String oname = (String) (it.next());
			for (Iterator it2 = assoc.associatedClasses().iterator(); it2
					.hasNext();) {
				MClass mc = (MClass) (it2.next());
				String cname = mc.name();
				List onames = new ArrayList();
				onames.add(oname);
				String msgg = isObjectsInSystem(onames, cname);
				if (msgg.length() == 0) {
					have = true;
					break;
				}
			}
			if (!have) {
				return "have no this object " + oname;
			}
		}
		for (Iterator it = assoc.associatedClasses().iterator(); it.hasNext();) {
			boolean have = false;
			MClass mc = (MClass) (it.next());
			String cname = mc.name();

			for (Iterator it2 = objectNames.iterator(); it2.hasNext();) {
				String oname = (String) (it2.next());
				List onames = new ArrayList();
				onames.add(oname);

				if (!(isObjectsInSystem(onames, cname).length() > 0)) {
					have = true;
					break;
				}
			}
			if (!have) {
				return "have no object von class " + cname;
			}
		}

		return msg;
	}

	/**
	 * Method isLinkFormatOk examined whether the given format is correct.
	 */
	boolean isLinkFormatOk() {
		if (line.indexOf("(") < line.indexOf(")")
				&& line.indexOf(")") < line.indexOf("from"))
			if (line.trim().length() > line.trim().indexOf("from") + 4)
				if (line.substring(line.trim().indexOf("from") + 4,
						line.trim().indexOf("from") + 5).equals(" "))
					if (line
							.substring(line.indexOf("(") + 1, line.indexOf(")"))
							.trim().length() > 0)
						if (line.substring(line.indexOf(")") + 1,
								line.indexOf("from")).trim().length() == 0)
							if (line.substring(line.indexOf("show") + 4,
									line.indexOf("(")).trim().length() == 0)
								return true;
							else if (line.substring(line.indexOf("hide") + 4,
									line.indexOf("(")).trim().length() == 0)
								return true;
							else if (line.substring(line.indexOf("crop") + 4,
									line.indexOf("(")).trim().length() == 0)
								return true;

		return false;
	}

	/**
	 * Method isObjectFormatOk examined whether the given format is correct.
	 */
	boolean isObjectFormatOk() {
		if (line.trim().length() > line.trim().indexOf(":") + 1)
			if (line.trim().indexOf("crop") < line.trim().indexOf(":"))
				if (line.trim().substring(line.trim().indexOf("crop") + 4,
						line.trim().indexOf(":")).length() > 0)
					return true;
		return false;
	}

	/**
	 * Method isObjectsInSystem examines whether there are the given objects in the system.
	 */
	String isObjectsInSystem(List objectNames, String className) {
		String message = "";
		Iterator it;
		List objects = new ArrayList();
		if (NewObjectDiagram.ffHiddenNodes != null) {
			it = NewObjectDiagram.ffHiddenNodes.iterator();
		
			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof MObject) {
					MObject mobj = (MObject) node;
					if (className.equals(mobj.cls().name())) {
						objects.add(mobj);
					}
				}
			}
		}

		if (NewObjectDiagram.ffGraph != null) {
			it = NewObjectDiagram.ffGraph.iterator();

			while (it.hasNext()) {
				Object node = it.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					if (className.equals(mobj.cls().name())) {
						objects.add(mobj);
					}
				}
			}
		}

		if (objects.size() == 0)
			return "no this class: " + className;
		else {
			for (int i = 0; i < objectNames.size(); i++) {
				boolean have = false;
				for (int j = 0; j < objects.size(); j++) {
					if (((String) objectNames.get(i)).equals(((MObject) objects
							.get(j)).name())) {
						have = true;
						break;
					}
				}
				if (!have) {
					return "no this object: " + objectNames.get(i);
				}
			}
		}
		return message;
	}

	private boolean isContainName(List names, String name) {
		for (int i = 0; i < names.size(); i++) {
			if (((String) (names.get(i))).equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method getClassName returns name of class
	 */
	private String getClassName() {
		return line.substring(line.indexOf(":") + 1).trim();
	}

	/**
	 * Method getObjectNames returns all names as list
	 */
	private List getObjectNames() {

		String objectsLine = line.substring(4, line.indexOf(":"));
		StringTokenizer ob = new StringTokenizer(objectsLine, ",");
		List objectNames = new ArrayList();
		while (ob.hasMoreTokens()) {
			objectNames.add(ob.nextToken().trim());
		}
		return objectNames;
	}
}
