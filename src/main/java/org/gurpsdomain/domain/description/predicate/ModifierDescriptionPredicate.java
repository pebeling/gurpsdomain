package org.gurpsdomain.domain.description.predicate;

import org.gurpsdomain.domain.description.ModifierDescription;

public interface ModifierDescriptionPredicate {
    boolean isFulfilledBy(ModifierDescription modifier);
}
