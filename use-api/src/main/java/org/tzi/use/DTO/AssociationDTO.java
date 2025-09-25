package org.tzi.use.DTO;

import java.util.List;
import java.util.Set;

public class AssociationDTO {
    private String name;
    private List<AssociationEndDTO> associationEnds;
    private List<String> roleNames;
    private Set<String> associatedClassNames;
    private Set<String> subsets;
    private Set<String> subsettedBy;
    private Set<String> redefines;
    private Set<String> redefinedBy;
    private boolean isUnion;
    private boolean isReadOnly;
    private boolean isOrdered;
    private boolean isDerived;
    private boolean isRedefining;
    private boolean hasQualifiedEnds;
    private int aggregationKind;
    private int positionInModel;

    // Default constructor
    public AssociationDTO() {
    }

    // Constructor with essential fields
    public AssociationDTO(String name, List<AssociationEndDTO> associationEnds) {
        this.name = name;
        this.associationEnds = associationEnds;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AssociationEndDTO> getAssociationEnds() {
        return associationEnds;
    }

    public void setAssociationEnds(List<AssociationEndDTO> associationEnds) {
        this.associationEnds = associationEnds;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public Set<String> getAssociatedClassNames() {
        return associatedClassNames;
    }

    public void setAssociatedClassNames(Set<String> associatedClassNames) {
        this.associatedClassNames = associatedClassNames;
    }

    public Set<String> getSubsets() {
        return subsets;
    }

    public void setSubsets(Set<String> subsets) {
        this.subsets = subsets;
    }

    public Set<String> getSubsettedBy() {
        return subsettedBy;
    }

    public void setSubsettedBy(Set<String> subsettedBy) {
        this.subsettedBy = subsettedBy;
    }

    public Set<String> getRedefines() {
        return redefines;
    }

    public void setRedefines(Set<String> redefines) {
        this.redefines = redefines;
    }

    public Set<String> getRedefinedBy() {
        return redefinedBy;
    }

    public void setRedefinedBy(Set<String> redefinedBy) {
        this.redefinedBy = redefinedBy;
    }

    public boolean isUnion() {
        return isUnion;
    }

    public void setUnion(boolean union) {
        isUnion = union;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public boolean isDerived() {
        return isDerived;
    }

    public void setDerived(boolean derived) {
        isDerived = derived;
    }

    public boolean isRedefining() {
        return isRedefining;
    }

    public void setRedefining(boolean redefining) {
        isRedefining = redefining;
    }

    public boolean isHasQualifiedEnds() {
        return hasQualifiedEnds;
    }

    public void setHasQualifiedEnds(boolean hasQualifiedEnds) {
        this.hasQualifiedEnds = hasQualifiedEnds;
    }

    public int getAggregationKind() {
        return aggregationKind;
    }

    public void setAggregationKind(int aggregationKind) {
        this.aggregationKind = aggregationKind;
    }

    public int getPositionInModel() {
        return positionInModel;
    }

    public void setPositionInModel(int positionInModel) {
        this.positionInModel = positionInModel;
    }
}
