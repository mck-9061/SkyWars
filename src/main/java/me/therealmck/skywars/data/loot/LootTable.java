package me.therealmck.skywars.data.loot;

import me.therealmck.skywars.data.loot.enums.BowLevel;
import me.therealmck.skywars.data.loot.enums.PearlLevel;
import me.therealmck.skywars.data.loot.enums.ProjectileLevel;
import me.therealmck.skywars.data.loot.enums.SwordLevel;

public class LootTable {
    private SwordLevel swordLevel;
    private BowLevel bowLevel;
    private ProjectileLevel projectileLevel;
    private PearlLevel pearlLevel;
    private int multiplier;

    public LootTable(SwordLevel swordLevel, BowLevel bowLevel, ProjectileLevel projectileLevel, PearlLevel pearlLevel, int multiplier) {
        this.swordLevel = swordLevel;
        this.bowLevel = bowLevel;
        this.projectileLevel = projectileLevel;
        this.pearlLevel = pearlLevel;
        this.multiplier = multiplier;
    }

    public LootTable() {
        this.swordLevel = SwordLevel.NO_SWORDS;
        this.bowLevel = BowLevel.NO_BOWS;
        this.pearlLevel = PearlLevel.NO_PEARLS;
        this.projectileLevel = ProjectileLevel.NO_PROJECTILES;
        this.multiplier = 1;
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
}
