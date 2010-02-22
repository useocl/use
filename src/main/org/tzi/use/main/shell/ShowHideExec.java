package org.tzi.use.main.shell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagramView;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.main.Session;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.StringUtil;

/**
 * responsibly for new functions in console
 * @author   Jun Zhang 
 * @author   Jie Xu
 */
public class ShowHideExec {
	
	protected Session session;
	protected String line;

	/**
	 * Constructor for ShowHideExec.
	 */
	ShowHideExec(String line) {
		this.line = line;
	}

	/**
	 * Method exec analyze input instruction of user
	 */
	public String exec(Session session) {
		this.session = session;
		
		if (line.trim().equals("show all")) {
			for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
				view.getDiagram().getHideAdmin().showAllHiddenElements();
				view.repaint();
			}
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
	 * Method hideAll hides all objects.
	 */
	void hideAll() {
		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
			Iterator<?> itt = view.getDiagram().getGraph().iterator();
			final HashSet<MObject> toHideObjects = new HashSet<MObject>();
		
			while (itt.hasNext()) { 
				Object node = itt.next();
				if (node instanceof ObjectNode) {
					MObject mobj = ((ObjectNode) node).object();
					toHideObjects.add(mobj);
				}
			}
			
			view.getDiagram().getHideAdmin().setValues("", toHideObjects).actionPerformed(null);
		}
	}

	/**
	 * Method cropObjects show only the appropriate given objects.
	 */
	String cropObjects() {
		String msg = "";
		if (!line.contains(":")) {
			return "error command format";
		}

		List<String> objectNames = getObjectNames();		
		msg = checkObjectNames(objectNames);
		
		if (msg.length() > 0) {
			return msg;
		}
		
		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
			NewObjectDiagram diag = view.getDiagram();
			HashSet<MObject> objects = new HashSet<MObject>();
			
			if (diag.getHiddenNodes() != null) {
				Iterator<?> it = diag.getHiddenNodes().iterator();
				
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
			if (diag.getGraph() != null) {
				Iterator<?> it = diag.getGraph().iterator();

				while (it.hasNext()) {
					Object node = it.next();
					if (node instanceof ObjectNode) {
						MObject mobj = ((ObjectNode) node).object();
						if (isContainName(objectNames, mobj.name())) {
							objects.add(mobj);
						}
					}
				}
			}
	
			Set<MObject> objectsToHide = getObjectsToHide(diag, objects, true);
			
			if (objectsToHide.size() > 0) {
				diag.getHideAdmin().setValues("Hide", objectsToHide).actionPerformed(null);
			}
			
			Set<MObject> objectsToShow = getObjectsToShow(diag, objects);
			
			if (objectsToShow.size() > 0) {
				diag.getHideAdmin().showHiddenElements(objectsToShow);
			}
		}
		
		return msg;
	}

	public Set<MObject> getObjectsToHide(NewObjectDiagram diag, Set<MObject> objects, boolean isCrop) {
		HashSet<MObject> objectsToHide = new HashSet<MObject>();
		Iterator<?> ithide = diag.getGraph().iterator(); 
	
		while (ithide.hasNext()) {
			Object node = ithide.next();
			if (node instanceof ObjectNode) {
				MObject mo = ((ObjectNode) node).object();
				if (isCrop) {
					if (!objects.contains(mo)) {
						objectsToHide.add(mo);
					}
				} else {
					if (objects.contains(mo)) {
						objectsToHide.add(mo);
					}
				}
			}
		}
		
		return objectsToHide;
	}

	public Set<MObject> getObjectsToShow(NewObjectDiagram diag, Set<MObject> objects) {
		Set<MObject> objectsToShow = new HashSet<MObject>();
		Iterator<?> itshow = diag.getHiddenNodes().iterator(); // hidenode
		
		while (itshow.hasNext()) {
			Object node = itshow.next();
			if (node instanceof MObject) {
				MObject mo = (MObject) node;
				if (objects.contains(mo)) {
					objectsToShow.add(mo);
				}
			}
		}
		
		return objectsToShow;
	}

	/**
	 * Method showObjects show the appropriate given objects.
	 */
	String showObjects() {
		String err = "";
		if (!line.contains(":")) {
			return "error command format";
		}
		
		List<String> objectNames = getObjectNames();
		String className = getClassName();
		
		// Check class and object names
		if (session.system().model().getClass(className) == null)
			return "Class '" + className + "' does not exist";

		String msg = checkObjectNames(objectNames);
		if (msg.length() > 0) {
			return msg;
		}
				
		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) { 
			NewObjectDiagram diag = view.getDiagram();
			Set<MObject> objectsToShow = new HashSet<MObject>();
			
			if (diag.getHiddenNodes() != null) {
				Iterator<?> it = diag.getHiddenNodes().iterator();
							
				while (it.hasNext()) {
					Object node = it.next();
					if (node instanceof MObject) {
						MObject mobj = (MObject) node;
						if (className.equals(mobj.cls().name())) {
							if (isContainName(objectNames, mobj.name())) {
								objectsToShow.add(mobj);
							}
						}
					}
				}
				
				if (!objectsToShow.isEmpty()) {
					diag.getHideAdmin().showHiddenElements(objectsToShow);
				}
			}
		}

		return err;
	}

	private List<String> getUndefinedObjects(List<String> objectNames) {
		List<String> objectsNotDefined = new ArrayList<String>();
		for (String objName : objectNames) {
			if (session.system().state().objectByName(objName) == null)
				objectsNotDefined.add(objName);
		}
		return objectsNotDefined;
	}

	private String checkObjectNames(List<String> objectNames) {
		List<String> objectsNotDefined = getUndefinedObjects(objectNames);
		
		if (!objectsNotDefined.isEmpty()) {
			return "The following objects do not exist: " + StringUtil.fmtSeq(objectsNotDefined, ",");
		} else {
			return "";
		}
	}
	
	/**
	 * Method hideObjects hide the appropriate given objects.
	 */
	String hideObjects() {
		if (!line.contains(":")) {
			return "error command format";
		}
		
		List<String> objectNames = getObjectNames();
		String className = getClassName();
		
		if (session.system().model().getClass(className) == null)
			return "Class '" + className + "' does not exist";
		
		String msg = checkObjectNames(objectNames);
		
		if (msg.length() > 0) {
			return msg;
		}

		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
			NewObjectDiagram diag = view.getDiagram();
			Set<MObject> objectsToHide = new HashSet<MObject>();
			
			if (diag.getGraph() != null) {
				Iterator<?> it = diag.getGraph().iterator();
				
				while (it.hasNext()) {
					Object node = it.next();
	
					if (node instanceof ObjectNode) {
						MObject mobj = ((ObjectNode) node).object();

						if (isContainName(objectNames, mobj.name())) {
							objectsToHide.add(mobj);
						}
					}
				}
			
				if (!objectsToHide.isEmpty()) {
					diag.getHideAdmin().setValues("", objectsToHide).actionPerformed(null);
				}
			}
		}
		
		return "";
	}

	/**
	 * Method showLinks show the given links.
	 */
	String showLinks() {
		String message = isLinksInSystem();
		if (message.length() > 0) {
			return message;
		}
		List<String> objectNames = getAssoObjectNames();
		
		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
			NewObjectDiagram diag = view.getDiagram();
			Set<MObject> toShowObjects = new HashSet<MObject>();
			
			if (diag.getHiddenNodes() != null) {
				Iterator<?> it = diag.getHiddenNodes().iterator();
				
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
			
			if (!toShowObjects.isEmpty()) {
				diag.getHideAdmin().showHiddenElements(toShowObjects);
			}
		}
				
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
		
		List<String> objectNames = getAssoObjectNames();
		
		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
			NewObjectDiagram diag = view.getDiagram();
			Set<MObject> objectsToHide = new HashSet<MObject>();
			
			if (diag.getGraph() != null) {
				Iterator<?> it = diag.getGraph().iterator();
				
				while (it.hasNext()) {
					Object node = it.next();

					if (node instanceof ObjectNode) {
						MObject mobj = ((ObjectNode) node).object();
						if (isContainName(objectNames, mobj.name())) {
							objectsToHide.add(mobj);
						}
					}
				}
				
				if (!objectsToHide.isEmpty())
					diag.getHideAdmin().setValues("", objectsToHide).actionPerformed(null);
			}
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
		
		List<String> objectNames = getAssoObjectNames();

		for (NewObjectDiagramView view : MainWindow.instance().getObjectDiagrams()) {
			NewObjectDiagram diag = view.getDiagram();
			HashSet<MObject> objects = new HashSet<MObject>();
			
			if (diag.getHiddenNodes() != null) {
				Iterator<?> it = diag.getHiddenNodes().iterator();
				
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
			
			if (diag.getGraph() != null) {
				Iterator<?> it = diag.getGraph().iterator();

				while (it.hasNext()) {
					Object node = it.next();
					if (node instanceof ObjectNode) {
						MObject mobj = ((ObjectNode) node).object();
						if (isContainName(objectNames, mobj.name())) {
							objects.add(mobj);
						}
					}
				}
			}

			Set<MObject> objectsToHide = getObjectsToHide(diag, objects, true);
			if (!objectsToHide.isEmpty()) {
				diag.getHideAdmin().setValues("Hide", objectsToHide).actionPerformed(null);
			}
			
			Set<MObject> objectsToShow = getObjectsToShow(diag, objects);
			if (!objectsToShow.isEmpty()) {
				diag.getHideAdmin().showHiddenElements(objectsToShow);
			}
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
	 * Method getAssoObjectNames returns all object names as list.
	 * @return List
	 */
	private List<String> getAssoObjectNames() {
		String objects = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
		
		StringTokenizer ob = new StringTokenizer(objects, ",");
		List<String> objectNames = new ArrayList<String>();

		while (ob.hasMoreTokens()) {
			objectNames.add(ob.nextToken().trim());
		}
		return objectNames;
	}

	/**
	 * Method isLinksInSystem examines whether the given links are in the system.
	 */
	String isLinksInSystem() {
		if (!isLinkFormatOk())
			return "format error";

		MAssociation assoc = session.system().model().getAssociation(getAssociationName());
		
		if (assoc == null) {
			return "The association " + getAssociationName() + " does not exist in system";
		}

		List<String> objectNames = getAssoObjectNames();

		if (objectNames.size() != assoc.associatedClasses().size())
			return "The given number of objects is invalid for the association!";
	
		List<MObject> objects = new ArrayList<MObject>();
		for (String oname : objectNames) {
			MObject obj = session.system().state().objectByName(oname);
			objects.add(obj);
		}
		
		try {
			if (!session.system().state().hasLink(assoc, objects))
				return "Link does not exist in current system state";
		} catch (MSystemException e) {
			return "Link does not exist in current system state";
		}
		
		return "";
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

	private boolean isContainName(List<String> names, String name) {
		for (String itemName : names) {
			if (itemName.equalsIgnoreCase(name)) {
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
	private List<String> getObjectNames() {
		String objectsLine = line.substring(4, line.indexOf(":"));
		StringTokenizer ob = new StringTokenizer(objectsLine, ",");
		List<String> objectNames = new ArrayList<String>();
		
		while (ob.hasMoreTokens()) {
			objectNames.add(ob.nextToken().trim());
		}
		
		return objectNames;
	}
}
