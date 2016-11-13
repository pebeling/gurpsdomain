package org.gurpsdomain.domain.description;

import org.gurpsdomain.domain.Modifier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "modifier")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModifierDescription {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "cost", required=false)
    private Cost cost;

    public ModifierDescription(){
        /* needed by JAXB */
    }

    public ModifierDescription(String name) {
        this.name = name;
    }

    public boolean matchesName(String wantedName) {
        return this.name.equals(wantedName);
    }

    public Modifier createModifier() {
        return new Modifier(name);
    }
}
