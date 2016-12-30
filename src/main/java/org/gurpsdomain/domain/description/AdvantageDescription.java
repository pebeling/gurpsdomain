package org.gurpsdomain.domain.description;

import documentation.Assign;
import documentation.Developer;
import org.gurpsdomain.domain.*;
import org.gurpsdomain.domain.description.predicate.ModifierDescriptionPredicate;
import org.gurpsdomain.domain.description.predicate.Name;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.gurpsdomain.domain.description.predicate.And.and;
import static org.gurpsdomain.domain.description.predicate.Note.note;
import static org.gurpsdomain.domain.description.predicate.Or.or;

@XmlRootElement(name = "advantage")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdvantageDescription implements Registerable<AdvantageDescription> {
    private String name;
    @XmlElement(name = "base_points")
    private int basePoints;
    @XmlElement(name = "levels", required = false)
    private Integer levels;
    @XmlElement(name = "points_per_level", required = false)
    private Integer pointsPerLevel;
    private String reference;
    @XmlElement(name = "modifier", required = false)
    private List<ModifierDescription> modifiers;
    @XmlElement(name = "attribute_bonus", required = false)
    private List<AttributeBonusDescription> attributeBonuses;

    private AdvantageDescription() {
        /* needed for JAXB */
    }

    public AdvantageDescription(String name, int basePoints, Integer levels, Integer pointsPerLevel, String pageReference, List<ModifierDescription> modifiers, List<AttributeBonusDescription> attributeBonuses) {
        this.name = name;
        this.basePoints = basePoints;
        this.levels = levels;
        this.pointsPerLevel = pointsPerLevel;
        this.reference = pageReference;
        this.modifiers = modifiers != null ? modifiers : Collections.emptyList();
        this.attributeBonuses = attributeBonuses != null ? attributeBonuses : Collections.emptyList();
    }

    @Assign(developer = Developer.PAUL, issues = {23})
    public Advantage createAdvantage(List<Map<String, String>> modifierIdentifiers, int levelAmount) {
        List<Modifier> modifiers = new ArrayList<>();
        for (Map<String, String> identifiers : modifierIdentifiers) {
            String modifierName = identifiers.getOrDefault("name", "");
            String modifierVariation = identifiers.getOrDefault("variation", "");
            ModifierDescriptionPredicate predicate;
            if (modifierVariation.equals("")) {
                predicate = Name.name(modifierName);
            } else {
                predicate = and(Name.name(modifierName), note(modifierVariation));
            }
            if (hasModifier(predicate)) {
                modifiers.add(createModifier(predicate));
            }
        }
        List<AttributeBonus> attributeBonuses = new ArrayList<>();

        if (pointsPerLevel != null) {
            return new LeveledAdvantage(name, basePoints, reference, modifiers, attributeBonuses, levelAmount, pointsPerLevel);
        } else {
            return new Advantage(name, basePoints, reference, modifiers, attributeBonuses);
        }
    }

    public void registerIn(Repository<AdvantageDescription> repository) {
        repository.register(name, this);
    }

    private boolean hasModifier(ModifierDescriptionPredicate predicate) {
        return modifiers.stream().anyMatch(predicate::isFulfilledBy);
    }

    private Modifier createModifier(ModifierDescriptionPredicate predicate) {
        return modifiers.stream().filter(predicate::isFulfilledBy).findAny().get().createModifier();
    }
}
