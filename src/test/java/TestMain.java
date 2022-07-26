import io.github.moulberry.repo.NEURepository;
import io.github.moulberry.repo.NEURepositoryException;
import io.github.moulberry.repo.NEURepositoryVersion;

import java.nio.file.Paths;

public class TestMain {
    public static void main(String[] args) throws NEURepositoryException {
        System.out.printf("Parser v%s%n", NEURepositoryVersion.REPOSITORY_PARSER_VERSION);
        System.out.printf("Schema %d.%d%n", NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MAJOR, NEURepositoryVersion.REPOSITORY_SCHEMA_VERSION_MINOR);

        NEURepository repository = NEURepository.of(Paths.get("NotEnoughUpdates-REPO"));
        repository.reload();
        System.out.println("pet mf (115): " + repository.getConstants().getBonuses().getPetRewards(115));
        System.out.println("skill reward (combat 60): " + repository.getConstants().getBonuses().getAccumulativeLevelingRewards("skill_combat", 60));
        System.out.println("parent of FLAWED_AMETHYST_GEM: " + repository.getConstants().getParents().getParent("FLAWED_AMETHYST_GEM"));
        System.out.println("enchants for a sword: " + repository.getConstants().getEnchants().getAvailableEnchants("SWORD"));
        System.out.println("conflicting enchants with sharpness: " + repository.getConstants().getEnchants().getConflictingEnchants("sharpness"));
        System.out.println("upgrade cost for HOT_CRIMSON_HELMET: " + repository.getConstants().getEssenceCost().getCosts().get("HOT_CRIMSON_HELMET"));
        System.out.println("first fairy soul in the hub: " + repository.getConstants().getFairySouls().getSoulLocations().get("hub").get(0));
        System.out.println("soul total: " + repository.getConstants().getFairySouls().getMaxSouls());
        System.out.println("minecraft item of ASPECT_OF_THE_END: "+repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").getMinecraftItemId());
        System.out.println("is vanilla ASPECT_OF_THE_END: "+repository.getItems().getItemBySkyblockId("ASPECT_OF_THE_END").isVanilla());
        System.out.println("is vanilla DIAMOND: "+repository.getItems().getItemBySkyblockId("DIAMOND").isVanilla());
    }
}
