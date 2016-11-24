package org.gurpsdomain.domain.description;

import org.gurpsdomain.domain.Advantage;
import org.gurpsdomain.domain.Modifier;
import org.gurpsdomain.domain.Repository;
import org.gurpsdomain.domain.description.predicate.ModifierDescriptionPredicate;
import org.gurpsdomain.domain.description.predicate.Name;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name="advantage")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdvantageDescription implements Registerable<AdvantageDescription> {
    private String name;
    @XmlElement(name="base_points")
    private int basePoints;
    @XmlElement(name="points_per_level", required=false)
    private Integer pointsPerLevel;
    private String reference;
    @XmlElement(name="modifier", required=false)
    private List <ModifierDescription> modifiers;

    private AdvantageDescription() {
        /* needed for JAXB */
    }

    public AdvantageDescription(String name, int basePoints, String pageReference, List<ModifierDescription> modifiers) {
        this.name = name;
        this.basePoints = basePoints;
        this.reference = pageReference;
        this.modifiers = modifiers != null ? modifiers : Collections.emptyList();
    }

    public Advantage createAdvantage(List<String> modifierNames) {
        List<Modifier> modifiers = new ArrayList<Modifier>();
        for(String modifierName: modifierNames) {
            ModifierDescriptionPredicate predicate = Name.name(modifierName);
            if (hasModifier(predicate)) {
                modifiers.add(createModifier(predicate));
            }
        }
        Advantage advantage = new Advantage(name, basePoints, reference, modifiers);
        return advantage;
    }

    public void registerIn(Repository<AdvantageDescription> repository) {
        repository.register(name, this);
    }

    private boolean hasModifier(ModifierDescriptionPredicate predicate) {
        return modifiers.stream().anyMatch(m -> predicate.isFullfilledBy(m));
    }

    private Modifier createModifier(ModifierDescriptionPredicate predicate) {
        return modifiers.stream().filter(m -> predicate.isFullfilledBy(m)).findAny().get().createModifier();
    }
}
