package org.gurpsdomain.domain;

import java.util.*;

import static org.gurpsdomain.domain.tables.DamageTable.swingingDamageForStrength;
import static org.gurpsdomain.domain.tables.DamageTable.thrustingDamageForStrength;

public enum Attribute {
    STRENGTH("ST", "st") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addStrengthBonus(Integer.parseInt(bonus));
        }
    },
    DEXTERITY("DX", "dx") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addDexterityBonus(Integer.parseInt(bonus));
        }
    },
    HEALTH("HT", "ht") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addHealthBonus(Integer.parseInt(bonus));
        }
    },
    INTELLIGENCE("IQ", "iq") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addIntelligenceBonus(Integer.parseInt(bonus));
        }
    },
    SIZE_MODIFIER("SM", "sm") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addSizeModifierBonus(Integer.parseInt(bonus));
        }

        @Override
        protected int defaultValue(){return 0;}
    },
    WILL("Will", "will") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addWillBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(INTELLIGENCE) + (int) bonus;
        }
    },
    MAGERY("Magery", "magery") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addMageryBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return bonus;
        }
    },
    FRIGHT_CHECK("Fright Check", "fright check") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addFrightCheckBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(WILL) + (int) bonus;
        }
    },
    PERCEPTION("Per", "perception") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addPerceptionBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(INTELLIGENCE) + (int) bonus;
        }
    },
    VISION("Vision", "vision") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addVisionBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(PERCEPTION) + (int) bonus;
        }
    },
    HEARING("Hearing", "hearing") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addHearingBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(PERCEPTION) + (int) bonus;
        }
    },
    TASTE_AND_SMELL("Taste & Smell", "taste smell") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addTasteAndSmellBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(PERCEPTION) + (int) bonus;
        }
    },
    TOUCH("Touch", "touch") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addTouchBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(PERCEPTION) + (int) bonus;
        }
    },
    HIT_POINTS("HP", "hp") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addHitPointsBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(STRENGTH) + (int) bonus;
        }
    },
    FATIGUE_POINTS("FP", "fp") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addFatiguePointsBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            return (int) attributes.value(HEALTH) + (int) bonus;
        }
    },
    BASIC_LIFT("BL") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addBasicLiftBonus(Weight.parseWeightInPounds(bonus));
        }

        @Override
        public Object defaultBonus() {
            return new Weight(0);
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            double strength = ((Integer) attributes.value(STRENGTH)).doubleValue();
            double basicLift = (strength * strength) / 5.0 + ((Weight) bonus).poundsInImperialSystem();
            if (basicLift > 10) {
                basicLift = Math.round(basicLift);
            }
            return new Weight(basicLift);
        }
    },
    BASIC_SPEED("Basic Speed", "speed") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addBasicSpeedBonus(Double.parseDouble(bonus));
        }

        @Override
        public Object defaultBonus() {
            return 0.0;
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            int health = (int) attributes.value(HEALTH);
            int dexterity = (int) attributes.value(DEXTERITY);
            double speed = (health + dexterity) / 4.0;
            return speed + (double) bonus;
        }
    },
    BASIC_MOVE("Basic Move", "move") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addBasicMoveBonus(Integer.parseInt(bonus));
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            int health = (int) attributes.value(HEALTH);
            int dexterity = (int) attributes.value(DEXTERITY);
            int move = (health + dexterity) / 4;
            return move + (int) bonus;
        }
    },
    DODGE("Dodge", "dodge") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addDodgeBonus(Integer.parseInt(bonus));
        }


        @Override
        public Object value(Attributes attributes, Object bonus) {
            double speed = (double) attributes.value(BASIC_SPEED);
            int dodge = (int) (speed + 3);

            return dodge + (int) bonus;
        }
    },
    DAMAGE_THRUSTING("Dmg. Thrusting") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addDamageThrustingBonus(Dice.parseDice(bonus));
        }

        @Override
        public Object defaultBonus() {
            return new Dice(0, 0);
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            int strength = (int) attributes.value(STRENGTH);
            Dice damage = thrustingDamageForStrength(strength);
            return Dice.sumDice(damage, (Dice) bonus);
        }
    },
    DAMAGE_SWINGING("Dmg. Swinging") {
        @Override
        public void addBonusTo(Attributes attributes, String bonus) {
            attributes.addDamageSwingingBonus(Dice.parseDice(bonus));
        }

        @Override
        public Object defaultBonus() {
            return new Dice(0, 0);
        }

        @Override
        public Object value(Attributes attributes, Object bonus) {
            int strength = (int) attributes.value(STRENGTH);
            Dice damage = swingingDamageForStrength(strength);
            return Dice.sumDice(damage, (Dice) bonus);
        }
    };

    static final private Map<String, Attribute> descriptionToAttribute = new HashMap<>();
    private final String shorthand;
    private final List<String> alternatives;

    static {
        for (Attribute attribute : Attribute.values()) {
            descriptionToAttribute.put(attribute.shorthand(), attribute);

            for (String alternativeDescription : attribute.alternativeDescriptions()) {
                descriptionToAttribute.put(alternativeDescription, attribute);
            }
        }
    }

    public static Attribute fromDescription(String description) {
        if (!descriptionToAttribute.containsKey(description)) {
            throw new IllegalArgumentException(String.format("\"%s\" is not a Attribute key", description));
        }
        return descriptionToAttribute.get(description);
    }

    Attribute(String shorthand, String... alternatives) {
        this.shorthand = shorthand;
        this.alternatives = Arrays.asList(alternatives);
    }

    public String shorthand() {
        return shorthand;
    }

    private List<String> alternativeDescriptions() {
        return alternatives;
    }

    public Object defaultBonus() {
        return 0;
    }

    public Object value(Attributes attributes, Object bonus) {
        return defaultValue() + (int) bonus;
    }

    protected int defaultValue(){return 10;}

    public abstract void addBonusTo(Attributes attributes, String bonus);
}

