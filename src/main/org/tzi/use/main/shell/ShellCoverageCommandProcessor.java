/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id$

package org.tzi.use.main.shell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tzi.use.analysis.coverage.AttributeAccessInfo;
import org.tzi.use.analysis.coverage.CoverageAnalyzer;
import org.tzi.use.analysis.coverage.CoverageData;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;

/**
 * Provides logic for the shell coverage command.
 * 
 * @author ms
 *
 */
public class ShellCoverageCommandProcessor {

	private final MModel model;
	private final String line;
	private boolean displaySum = false;

	private final String fSpace = " ";
	private final String fSpaceTwo = fSpace + fSpace;
	private final String fSpaceColon = ": ";
	private final String fCommaSpace = ", ";
	private final String fDot = ".";
	private final String fTab = "\t";

	public ShellCoverageCommandProcessor(MModel model, String line) {
		this.model = model;
		this.line = line;
	}

	public void run() {
		if (checkModelAvailable()) {
			dispatch();
		} else {
			Log.error("No model loaded");
			return;
		}
	}

	private boolean checkModelAvailable() {
		return model != null;
	}

	private void dispatch() {
		String[] args = line.split(" ");

		for (int i = 1; i < args.length; ++i) {

			if ("-sum".equals(args[i])) {
				displaySum = true;
			}

			else if ("-total".equals(args[i])) {
				_totalCoverage();
				return;
			}

			else if ("-invariants".equals(args[i])) {
				_invariantsCoverage();
				return;
			}

			else if ("-pre".equals(args[i])) {
				_preConditionsCoverage();
				return;
			}

			else if ("-post".equals(args[i])) {
				_postConditionsCoverage();
				return;
			}

			else if ("-contracts".equals(args[i])) {
				_contractsCoverage();
				return;

			} else {
				Log.error("Invalid argument " + args[i]);
				return;
			}
		}
	}

	private void _totalCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculateTotalCoverage(model, true);

		_displayCoverage(completeData, "total");
	}

	private void _invariantsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculateInvariantCoverage(model, true);

		_displayCoverage(completeData, "invariants");

		List<MClassInvariant> sortedInvariants = new ArrayList<MClassInvariant>(
				model.classInvariants());

		Collections.sort(sortedInvariants, new Comparator<MClassInvariant>() {
			@Override
			public int compare(MClassInvariant o1, MClassInvariant o2) {
				int clsCmp = o1.cls().compareTo(o2);
				if (clsCmp == 0) {
					return o1.name().compareTo(o2.name());
				} else {
					return clsCmp;
				}
			}
		});

		Log.println();
		Log.println("Coverage by Invariant");

		String total, classes, classesComplete, associations, attributes, properties;

		for (MClassInvariant invariant : sortedInvariants) {
			CoverageData data = completeData.get(invariant);

			if (displaySum) {
				total = fSpace
						+ Integer.toString(data.getCompleteCoveredClasses()
								.size()
								+ data.getAssociationCoverage().keySet().size()
								+ data.getAttributeAccessCoverage().keySet()
										.size()
								+ data.getPropertyCoverage().keySet().size());

				classes = Integer.toString(data.getCoveredClasses().size());
				classesComplete = Integer.toString(data
						.getCompleteCoveredClasses().size());
				associations = Integer.toString(data.getAssociationCoverage()
						.keySet().size());
				attributes = Integer.toString(data.getAttributeAccessCoverage()
						.keySet().size());
				properties = Integer.toString(data.getPropertyCoverage()
						.keySet().size());
			} else {
				total = new String();
				classes = StringUtil.fmtSeq(data.getCoveredClasses(),
						fCommaSpace);
				classesComplete = StringUtil.fmtSeq(
						data.getCompleteCoveredClasses(), fCommaSpace);
				associations = StringUtil.fmtSeq(data.getAssociationCoverage()
						.keySet(), fCommaSpace);
				attributes = StringUtil
						.fmtSeq(data.getAttributeAccessCoverage().keySet(),
								fCommaSpace,
								new StringUtil.IElementFormatter<AttributeAccessInfo>() {
									@Override
									public String format(
											AttributeAccessInfo element) {
										String inherited = new String();
										if (element.isInherited()) {
											inherited = fSpace + "(inherited)";
										}

										return element.getSourceClass().name()
												+ fDot
												+ element.getAttribute().name()
												+ inherited;
									}
								});
				properties = StringUtil.fmtSeq(data.getPropertyCoverage()
						.keySet(), fCommaSpace,
						new StringUtil.IElementFormatter<MModelElement>() {
							@Override
							public String format(MModelElement element) {
								return element.name();
							}
						});
			}

			Log.println();
			Log.print(fSpaceTwo);
			Log.print(invariant.cls().name());
			Log.print("::");
			Log.print(invariant.name());
			Log.print(":");
			Log.println(total);

			Log.print(fSpaceTwo + "-Classes:" + fTab + fTab);
			Log.println(classes);

			Log.print(fSpaceTwo + "-Classes (complete):" + fTab);
			Log.println(classesComplete);

			Log.print(fSpaceTwo + "-Associations:" + fTab);
			Log.println(associations);

			Log.print(fSpaceTwo + "-Attributes:" + fTab + fTab);
			Log.println(attributes);

			Log.print(fSpaceTwo + "-Properties:" + fTab + fTab);
			Log.println(properties);

		}

		Log.println();
	}

	private void _preConditionsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculatePreConditionCoverage(model, true);

		_displayCoverage(completeData, "pre-conditions");

		List<MPrePostCondition> preconditions = new ArrayList<MPrePostCondition>(
				model.preConditions());

		_displayPrePostDetails(completeData, preconditions, "Precondition");
	}

	private void _postConditionsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculatePostConditionCoverage(model, true);

		_displayCoverage(completeData, "post-conditions");

		List<MPrePostCondition> postconditions = new ArrayList<MPrePostCondition>(
				model.postConditions());

		_displayPrePostDetails(completeData, postconditions, "Postcondition");
	}

	private void _contractsCoverage() {
		Map<MModelElement, CoverageData> completeData = CoverageAnalyzer
				.calculateContractCoverage(model, true);

		_displayCoverage(completeData, "contracts");

		List<MPrePostCondition> conditions = new ArrayList<MPrePostCondition>(
				model.prePostConditions());

		_displayPrePostDetails(completeData, conditions, "Contract");
	}

	private void _displayCoverage(
			Map<MModelElement, CoverageData> completeData, String target) {

		CoverageData data = completeData.get(model);

		Log.println();
		Log.println("Classes covered by" + fSpace + target + fSpaceColon
				+ data.getCoveredClasses().size() + "/"
				+ model.classes().size());

		List<Map.Entry<MClass, Integer>> entries = new ArrayList<Map.Entry<MClass, Integer>>(
				data.getClassCoverage().entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<MClass, Integer>>() {
			@Override
			public int compare(Entry<MClass, Integer> o1,
					Entry<MClass, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (Map.Entry<MClass, Integer> entry : entries) {
			Log.println(fSpaceTwo + entry.getKey().name() + fSpaceColon
					+ entry.getValue().toString());
		}

		Set<MClass> notCovered = new HashSet<MClass>(model.classes());
		notCovered.removeAll(data.getClassCoverage().keySet());

		for (MClass entry : notCovered) {
			Log.println(fSpaceTwo + entry.name() + fSpaceColon + 0);
		}

		Log.println();
		Log.println("Classes covered completely by" + fSpace + target
				+ fSpaceColon + data.getCompleteCoveredClasses().size() + "/"
				+ model.classes().size());

		entries = new ArrayList<Map.Entry<MClass, Integer>>(data
				.getCompleteClassCoverage().entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<MClass, Integer>>() {
			@Override
			public int compare(Entry<MClass, Integer> o1,
					Entry<MClass, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		for (Map.Entry<MClass, Integer> entry : entries) {
			Log.println(fSpaceTwo + entry.getKey().name() + fSpaceColon
					+ entry.getValue().toString());
		}

		notCovered = new HashSet<MClass>(model.classes());
		notCovered.removeAll(data.getCompleteClassCoverage().keySet());

		for (MClass entry : notCovered) {
			Log.println(fSpaceTwo + entry.name() + fSpaceColon + 0);
		}

		Log.println();
		Log.println("Associations covered by" + fSpace + target + fSpaceColon
				+ data.getAssociationCoverage().size() + "/"
				+ model.associations().size());

		int attributeCount = 0;
		for (MClass cls : model.classes()) {
			attributeCount += cls.attributes().size();
		}

		Log.println();
		Log.println("Attributes covered by" + fSpace + target + fSpaceColon
				+ data.getAttributeAccessCoverage().size() + "/"
				+ attributeCount);
	}

	private void _displayPrePostDetails(
			Map<MModelElement, CoverageData> completeData,
			List<MPrePostCondition> conditions, String target) {

		Collections.sort(conditions, new Comparator<MPrePostCondition>() {
			@Override
			public int compare(MPrePostCondition o1, MPrePostCondition o2) {
				int clsCmp = o1.cls().compareTo(o2);
				if (clsCmp == 0) {
					return o1.name().compareTo(o2.name());
				} else {
					return clsCmp;
				}
			}
		});

		Log.println();
		Log.println("Coverage by" + fSpace + target);

		String total, classes, classesComplete, associations, attributes, properties;

		for (MPrePostCondition condition : conditions) {
			CoverageData data = completeData.get(condition);

			if (displaySum) {
				total = fSpace
						+ Integer.toString(data.getCompleteCoveredClasses()
								.size()
								+ data.getAssociationCoverage().keySet().size()
								+ data.getAttributeAccessCoverage().keySet()
										.size()
								+ data.getPropertyCoverage().keySet().size());

				classes = Integer.toString(data.getCoveredClasses().size());
				classesComplete = Integer.toString(data
						.getCompleteCoveredClasses().size());
				associations = Integer.toString(data.getAssociationCoverage()
						.keySet().size());
				attributes = Integer.toString(data.getAttributeAccessCoverage()
						.keySet().size());
				properties = Integer.toString(data.getPropertyCoverage()
						.keySet().size());
			} else {
				total = new String();
				classes = StringUtil.fmtSeq(data.getCoveredClasses(),
						fCommaSpace);
				classesComplete = StringUtil.fmtSeq(
						data.getCompleteCoveredClasses(), fCommaSpace);
				associations = StringUtil.fmtSeq(data.getAssociationCoverage()
						.keySet(), fCommaSpace);
				attributes = StringUtil
						.fmtSeq(data.getAttributeAccessCoverage().keySet(),
								fCommaSpace,
								new StringUtil.IElementFormatter<AttributeAccessInfo>() {
									@Override
									public String format(
											AttributeAccessInfo element) {
										String inherited = new String();
										if (element.isInherited()) {
											inherited = fSpace + "(inherited)";
										}

										return element.getSourceClass().name()
												+ fDot
												+ element.getAttribute().name()
												+ inherited;
									}
								});
				properties = StringUtil.fmtSeq(data.getPropertyCoverage()
						.keySet(), fCommaSpace,
						new StringUtil.IElementFormatter<MModelElement>() {
							@Override
							public String format(MModelElement element) {
								return element.name();
							}
						});
			}

			Log.println();
			Log.print(fSpaceTwo);
			Log.print(condition.cls().name());
			Log.print("::");
			Log.print(condition.operation().name());
			Log.print("#");
			Log.print(condition.name());
			Log.print(":");
			Log.println(total);

			Log.print(fSpaceTwo + "-Classes:" + fTab + fTab);
			Log.println(classes);

			Log.print(fSpaceTwo + "-Classes (complete):" + fTab);
			Log.println(classesComplete);

			Log.print(fSpaceTwo + "-Associations:" + fTab);
			Log.println(associations);

			Log.print(fSpaceTwo + "-Attributes:" + fTab + fTab);
			Log.println(attributes);

			Log.print(fSpaceTwo + "-Properties:" + fTab + fTab);
			Log.println(properties);

		}

		Log.println();
	}

		/*
		Map<MClass, Set<MClassInvariant>> classInvariantMap = new HashMap<MClass, Set<MClassInvariant>>();

		for (MClass mClass : model.classes()) {
			classInvariantMap.put(mClass, model.classInvariants(mClass));
		}

		Log.println("Classes covered by invariants:");

		String className;
		ArrayList<String> invariantNames;
		for (Map.Entry<MClass, Set<MClassInvariant>> e : classInvariantMap
				.entrySet()) {
			className = e.getKey().name();

			invariantNames = new ArrayList<String>();
			for (MClassInvariant invariant : e.getValue()) {
				invariantNames.add(invariant.name());
			}

			Log.println(className + " => " + invariantNames.toString() + " "
					+ "(" + invariantNames.size() + ")");
		}

		for (MPrePostCondition mPrePostCondition : model.prePostConditions()) {
			Log.println(mPrePostCondition.expression().toString());
			Log.println(mPrePostCondition.operation().toString());
			Log.println(mPrePostCondition.operation().cls().toString());
			classInvariantMap.put(mClass, model.classInvariants(mClass));
		}
		*/
}
