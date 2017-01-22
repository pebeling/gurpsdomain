package org.gurpsdomain.adapters.input.yaml;

import org.gurpsdomain.adapters.input.SheetInput;
import org.gurpsdomain.adapters.input.yaml.domain.InputSheet;
import org.gurpsdomain.adapters.input.yaml.step.*;
import org.gurpsdomain.domain.Repository;
import org.gurpsdomain.domain.Sheet;
import org.gurpsdomain.domain.SheetBuilder;
import org.gurpsdomain.domain.description.AdvantageDescription;
import org.gurpsdomain.domain.description.EquipmentDescription;
import org.gurpsdomain.domain.description.SkillDescription;
import org.gurpsdomain.domain.description.SpellDescription;
import org.gurpsdomain.domain.repositories.InMemoryRepository;
import org.gurpsdomain.domain.repositories.xml.AdvantageDescriptions;
import org.gurpsdomain.domain.repositories.xml.EquipmentDescriptions;
import org.gurpsdomain.domain.repositories.xml.SkillDescriptions;
import org.gurpsdomain.domain.repositories.xml.SpellDescriptions;
import org.yaml.snakeyaml.Yaml;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

import static org.gurpsdomain.adapters.input.yaml.step.AddAdvantagesStep.addAdvantagesStep;
import static org.gurpsdomain.adapters.input.yaml.step.AddAdvantagesStep.addDisadvantagesStep;
import static org.gurpsdomain.domain.SheetBuilder.builder;

public class YamlSheetInput implements SheetInput {
    public static YamlSheetInput fromYaml(Reader reader) {
        Repository<AdvantageDescription> advantageRepository = InMemoryRepository.loadedWith(AdvantageDescriptions.class, "src/main/resources/data/advantages.basic-set.xml");
        Repository<SkillDescription> skillRepository = InMemoryRepository.loadedWith(SkillDescriptions.class, "src/main/resources/data/skills.basic-set.xml");
        Repository<SpellDescription> spellRepository = InMemoryRepository.loadedWith(SpellDescriptions.class, "src/main/resources/data/spells.magic-set.xml");
        Repository<EquipmentDescription> equipmentRepository = InMemoryRepository.loadedWith(EquipmentDescriptions.class, "src/main/resources/data/equipments.basic-set.xml");
        YamlSheetInput yamlSheetInput = new YamlSheetInput(reader);
        yamlSheetInput.addBuildStep(new AddSkillsStep(skillRepository));
        yamlSheetInput.addBuildStep(new AddSpellsStep(spellRepository));
        yamlSheetInput.addBuildStep(addAdvantagesStep(advantageRepository));
        yamlSheetInput.addBuildStep(addDisadvantagesStep(advantageRepository));
        yamlSheetInput.addBuildStep(new AddEquipmentsStep(equipmentRepository));
        return yamlSheetInput;
    }

    private Reader reader;
    private SheetBuilder sheetBuilder;
    private Collection<YamlBuildStep> buildSteps = new ArrayList<>();

    private YamlSheetInput(Reader reader) {
        this.reader = reader;
        populateBuildSteps();
    }

    private void populateBuildSteps() {
        addBuildStep(new IdentityStep());
        addBuildStep(new PlayerInformationStep());
        addBuildStep(new DescriptionStep());
        addBuildStep(new SetBasePointsStep());
        addBuildStep(new AwardRewardsStep());
        addBuildStep(new AddNotesStep());
    }

    private void addBuildStep(YamlBuildStep buildStep) {
        buildSteps.add(buildStep);
    }

    @Override
    public Sheet produce() {
        if (sheetBuilder == null) {
            Yaml yaml = new Yaml(InputSheet.constructor());
            InputSheet data = (InputSheet) yaml.load(reader);
            sheetBuilder = builder();
            for (YamlBuildStep buildStep: buildSteps) {
                buildStep.build(data, sheetBuilder);
            }
        }
        return sheetBuilder.build();
    }
}
