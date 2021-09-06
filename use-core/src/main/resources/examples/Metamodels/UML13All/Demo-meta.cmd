-- Opens the class diagram:
-- open examples/Metamodels/UML13All/UML13All.use

-- Creates the object diagram:
-- read examples/Metamodels/UML13All/Demo-meta.cmd

!create DepartmentClass : Class
!set DepartmentClass.name := 'Department'
!set DepartmentClass.isAbstract := false

!create Department_budgetAttribute : Attribute
!set Department_budgetAttribute.name := 'budget'
!insert (DepartmentClass, Department_budgetAttribute) into Classifier_Feature

!create Department_locationAttribute : Attribute
!set Department_locationAttribute.name := 'location'
!insert (DepartmentClass, Department_locationAttribute) into Classifier_Feature

!create Department_nameAttribute : Attribute
!set Department_nameAttribute.name := 'name'
!insert (DepartmentClass, Department_nameAttribute) into Classifier_Feature

!create EmployeeClass : Class
!set EmployeeClass.name := 'Employee'
!set EmployeeClass.isAbstract := false

!create Employee_salaryAttribute : Attribute
!set Employee_salaryAttribute.name := 'salary'
!insert (EmployeeClass, Employee_salaryAttribute) into Classifier_Feature

!create Employee_nameAttribute : Attribute
!set Employee_nameAttribute.name := 'name'
!insert (EmployeeClass, Employee_nameAttribute) into Classifier_Feature

!create ProjectClass : Class
!set ProjectClass.name := 'Project'
!set ProjectClass.isAbstract := false

!create Project_nameAttribute : Attribute
!set Project_nameAttribute.name := 'name'
!insert (ProjectClass, Project_nameAttribute) into Classifier_Feature

!create Project_budgetAttribute : Attribute
!set Project_budgetAttribute.name := 'budget'
!insert (ProjectClass, Project_budgetAttribute) into Classifier_Feature
