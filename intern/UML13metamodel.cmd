// -*- java -*-

\
!create cElement : Class;
set cElement.name = 'Element';
set cModelElement.isRoot = true;

create cModelElement : Class;
set cModelElement.name = 'ModelElement';
set cModelElement.isRoot = true;

create gModelElement_Element : Generalization;
set gModelElement_Element.name = 'ModelElement_Element';

insert (gModelElement_Element, cModelElement) into Generalization_GeneralizableElement1;
insert (gModelElement_Element, cElement) into Generalization_GeneralizableElement2;

// circular generalization: should trigger constraint violation
//  create gModelElement_Element2 : Generalization;
//  set gModelElement_Element2.name = 'ModelElement_Element2';

//  insert (gModelElement_Element2, cElement) into Generalization_GeneralizableElement1;
//  insert (gModelElement_Element2, cModelElement) into Generalization_GeneralizableElement2;

.

