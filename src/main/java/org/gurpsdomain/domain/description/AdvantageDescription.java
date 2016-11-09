package org.gurpsdomain.domain.description;

import org.gurpsdomain.domain.Advantage;
import org.gurpsdomain.domain.Modifier;
import org.gurpsdomain.domain.PageReference;
import org.gurpsdomain.repositories.xml.ModifierDescription;

import java.util.ArrayList;
import java.util.List;

public class AdvantageDescription implements PageReference {
    public String name;
    public int basePoints;
    public Integer pointsPerLevel;
    public String reference;
    public List <ModifierDescription> modifiers;

    public AdvantageDescription(String name, int basePoints, String pageReference) {
        this(name, basePoints, pageReference, new ArrayList<ModifierDescription>());
    }

    public AdvantageDescription(String name, int basePoints, String pageReference, List<ModifierDescription> modifiers) {
        this.name = name;
        this.basePoints = basePoints;
        this.reference = pageReference;
        this.modifiers = modifiers != null ? modifiers : new ArrayList<ModifierDescription>();
    }

    public Advantage createAdvantage() {
        return new Advantage(name, basePoints, reference);
    }

    @Override
    public String getPageReference() {
        return reference;
    }
}
