package me.therealmck.skywars.data.loot;

import me.therealmck.skywars.Main;
import me.therealmck.skywars.data.loot.enums.*;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class LootTable {
    private SwordLevel swordLevel;
    private BowLevel bowLevel;
    private ProjectileLevel projectileLevel;
    private PearlLevel pearlLevel;
    private ArmorLevel armorLevel;
    private MiscLevel miscLevel;
    private int multiplier;

    public LootTable(SwordLevel swordLevel, BowLevel bowLevel, ProjectileLevel projectileLevel, PearlLevel pearlLevel, ArmorLevel armorLevel, MiscLevel miscLevel, int multiplier) {
        this.swordLevel = swordLevel;
        this.bowLevel = bowLevel;
        this.projectileLevel = projectileLevel;
        this.pearlLevel = pearlLevel;
        this.armorLevel = armorLevel;
        this.miscLevel = miscLevel;
        this.multiplier = multiplier;
    }

    public LootTable() {
        this.swordLevel = SwordLevel.NO_SWORDS;
        this.bowLevel = BowLevel.NO_BOWS;
        this.pearlLevel = PearlLevel.NO_PEARLS;
        this.projectileLevel = ProjectileLevel.NO_PROJECTILES;
        this.armorLevel = ArmorLevel.NO_ARMOR;
        this.miscLevel = MiscLevel.NO_MISC;
        this.multiplier = 1;
    }

    public LootTable(boolean island) {
        // true: return default island loot
        // false: return default mid loot

        if (island) {
            this.swordLevel = SwordLevel.valueOf(Main.skyWarsConfig.getString("DefaultIslandSwordLevel"));
            this.bowLevel = BowLevel.valueOf(Main.skyWarsConfig.getString("DefaultIslandBowLevel"));
            this.pearlLevel = PearlLevel.valueOf(Main.skyWarsConfig.getString("DefaultIslandPearlLevel"));
            this.projectileLevel = ProjectileLevel.valueOf(Main.skyWarsConfig.getString("DefaultIslandProjectileLevel"));
            this.armorLevel = ArmorLevel.valueOf(Main.skyWarsConfig.getString("DefaultIslandArmorLevel"));
            this.miscLevel = MiscLevel.valueOf(Main.skyWarsConfig.getString("DefaultIslandMiscLevel"));
            this.multiplier = Main.skyWarsConfig.getInt("DefaultIslandMultiplier");
        } else {
            this.swordLevel = SwordLevel.valueOf(Main.skyWarsConfig.getString("DefaultMidSwordLevel"));
            this.bowLevel = BowLevel.valueOf(Main.skyWarsConfig.getString("DefaultMidBowLevel"));
            this.pearlLevel = PearlLevel.valueOf(Main.skyWarsConfig.getString("DefaultMidPearlLevel"));
            this.projectileLevel = ProjectileLevel.valueOf(Main.skyWarsConfig.getString("DefaultMidProjectileLevel"));
            this.armorLevel = ArmorLevel.valueOf(Main.skyWarsConfig.getString("DefaultMidArmorLevel"));
            this.miscLevel = MiscLevel.valueOf(Main.skyWarsConfig.getString("DefaultMidMiscLevel"));
            this.multiplier = Main.skyWarsConfig.getInt("DefaultMidMultiplier");
        }
    }

    public SwordLevel getSwordLevel() {
        return swordLevel;
    }

    public void setSwordLevel(SwordLevel swordLevel) {
        this.swordLevel = swordLevel;
    }

    public BowLevel getBowLevel() {
        return bowLevel;
    }

    public void setBowLevel(BowLevel bowLevel) {
        this.bowLevel = bowLevel;
    }

    public ProjectileLevel getProjectileLevel() {
        return projectileLevel;
    }

    public void setProjectileLevel(ProjectileLevel projectileLevel) {
        this.projectileLevel = projectileLevel;
    }

    public PearlLevel getPearlLevel() {
        return pearlLevel;
    }

    public void setPearlLevel(PearlLevel pearlLevel) {
        this.pearlLevel = pearlLevel;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public ArmorLevel getArmorLevel() {
        return armorLevel;
    }

    public void setArmorLevel(ArmorLevel armorLevel) {
        this.armorLevel = armorLevel;
    }

    public MiscLevel getMiscLevel() {
        return miscLevel;
    }

    public void setMiscLevel(MiscLevel miscLevel) {
        this.miscLevel = miscLevel;
    }

    public Loot getSwordLoot() {
        if (swordLevel.equals(SwordLevel.NO_SWORDS)) return new Loot(0, 0, new ArrayList<>());
        String level = swordLevel.name().split("_")[1];
        ConfigurationSection section = Main.skyWarsConfig.getConfigurationSection("SwordLevels."+level);
        return new Loot(section.getInt("chance"), section.getInt("rolls"), section.getList("items"));
    }

    public Loot getBowLoot() {
        if (bowLevel.equals(BowLevel.NO_BOWS)) return new Loot(0, 0, new ArrayList<>());
        String level = bowLevel.name().split("_")[1];
        ConfigurationSection section = Main.skyWarsConfig.getConfigurationSection("BowLevels."+level);
        return new Loot(section.getInt("chance"), section.getInt("rolls"), section.getList("items"));
    }

    public Loot getPearlLoot() {
        if (pearlLevel.equals(PearlLevel.NO_PEARLS)) return new Loot(0, 0, new ArrayList<>());
        String level = pearlLevel.name().split("_")[1];
        ConfigurationSection section = Main.skyWarsConfig.getConfigurationSection("PearlLevels."+level);
        return new Loot(section.getInt("chance"), section.getInt("rolls"), section.getList("items"));
    }

    public Loot getProjectileLoot() {
        if (projectileLevel.equals(ProjectileLevel.NO_PROJECTILES)) return new Loot(0, 0, new ArrayList<>());
        String level = projectileLevel.name().split("_")[1];
        ConfigurationSection section = Main.skyWarsConfig.getConfigurationSection("ProjectileLevels."+level);
        return new Loot(section.getInt("chance"), section.getInt("rolls"), section.getList("items"));
    }

    public Loot getArmorLoot() {
        if (armorLevel.equals(ArmorLevel.NO_ARMOR)) return new Loot(0, 0, new ArrayList<>());
        String level = armorLevel.name().split("_")[1];
        ConfigurationSection section = Main.skyWarsConfig.getConfigurationSection("ArmorLevels."+level);
        return new Loot(section.getInt("chance"), section.getInt("rolls"), section.getList("items"));
    }

    public Loot getMiscLoot() {
        if (miscLevel.equals(MiscLevel.NO_MISC)) return new Loot(0, 0, new ArrayList<>());
        String level = miscLevel.name().split("_")[1];
        ConfigurationSection section = Main.skyWarsConfig.getConfigurationSection("MiscLevels."+level);
        return new Loot(section.getInt("chance"), section.getInt("rolls"), section.getList("items"));
    }
}
