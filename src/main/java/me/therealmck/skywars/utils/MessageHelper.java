package me.therealmck.skywars.utils;

import me.therealmck.skywars.Main;

import java.util.List;
import java.util.Random;

public class MessageHelper {
    private String unauthorized;
    private String died;
    private String win;
    private String joinedGame;
    private String leftGame;
    private String teamPicked;
    private String teamGui;
    private String roomDeleted;
    private String customGameMainGui;
    private String customGameEventGui;
    private String customGameIslandLootGui;
    private String customGameMidLootGui;
    private String customGameModifierGui;
    private String guiBack;
    private String anvilEvent;
    private String blockEvent;
    private String mountEvent;
    private String zombieEvent;
    private String customGameMainGuiIslandLoot;
    private String customGameMainGuiMidLoot;
    private String customGameMainGuiEvents;
    private String customGameMainGuiModifiers;
    private String customGameMainGuiSelectTeams;
    private String customGameMainGuiDeleteRoom;
    private String swordLoot;
    private String bowLoot;
    private String pearlLoot;
    private String projectileLoot;
    private String armorLoot;
    private String miscLoot;
    private String jumpModifier;
    private String speedModifier;
    private String healthModifier;
    private String adminLocationAddFail;
    private String adminChestAdded;
    private String adminChestFailed;
    private String adminSpawnAdded;
    private String adminMapAlreadyExists;
    private String adminMapAdded;
    private String adminLobbySet;
    private String commandFailed;
    private String gameFull;
    private String customGameJoinPlayerUnspecified;
    private String kitAlreadyExists;
    private String invalidSyntax;
    private String kitCreated;
    private String forcestartFailed;
    private String leaveFailed;
    private String returnToLobby;
    private String noGamesAvailable;
    private String noTeleport;

    private List<String> killMessages;
    private List<String> deathMessages;
    private List<String> helpMessages;
    private List<String> adminHelpMessages;

    public MessageHelper() {
        this.unauthorized = Main.messageConfig.getString("unauthorized");
        this.died = Main.messageConfig.getString("died");
        this.win = Main.messageConfig.getString("win");
        this.joinedGame = Main.messageConfig.getString("joined-game");
        this.leftGame = Main.messageConfig.getString("left-game");
        this.teamPicked = Main.messageConfig.getString("team-picked");
        this.teamGui = Main.messageConfig.getString("team-gui");
        this.roomDeleted = Main.messageConfig.getString("room-deleted");
        this.customGameMainGui = Main.messageConfig.getString("custom-game-main-gui");
        this.customGameEventGui = Main.messageConfig.getString("custom-game-event-gui");
        this.customGameIslandLootGui = Main.messageConfig.getString("custom-game-island-loot-gui");
        this.customGameMidLootGui = Main.messageConfig.getString("custom-game-mid-loot-gui");
        this.customGameModifierGui = Main.messageConfig.getString("custom-game-modifier-gui");
        this.guiBack = Main.messageConfig.getString("gui-back");
        this.anvilEvent = Main.messageConfig.getString("anvil-event");
        this.blockEvent = Main.messageConfig.getString("block-event");
        this.mountEvent = Main.messageConfig.getString("mount-event");
        this.zombieEvent = Main.messageConfig.getString("zombie-event");
        this.customGameMainGuiIslandLoot = Main.messageConfig.getString("custom-game-main-gui-island-loot");
        this.customGameMainGuiMidLoot = Main.messageConfig.getString("custom-game-main-gui-mid-loot");
        this.customGameMainGuiEvents = Main.messageConfig.getString("custom-game-main-gui-events");
        this.customGameMainGuiModifiers = Main.messageConfig.getString("custom-game-main-gui-modifiers");
        this.customGameMainGuiSelectTeams = Main.messageConfig.getString("custom-game-main-gui-select-teams");
        this.customGameMainGuiDeleteRoom = Main.messageConfig.getString("custom-game-main-gui-delete-room");
        this.swordLoot = Main.messageConfig.getString("sword-loot");
        this.bowLoot = Main.messageConfig.getString("bow-loot");
        this.pearlLoot = Main.messageConfig.getString("pearl-loot");
        this.projectileLoot = Main.messageConfig.getString("projectile-loot");
        this.armorLoot = Main.messageConfig.getString("armor-loot");
        this.miscLoot = Main.messageConfig.getString("misc-loot");
        this.jumpModifier = Main.messageConfig.getString("jump-modifier");
        this.speedModifier = Main.messageConfig.getString("speed-modifier");
        this.healthModifier = Main.messageConfig.getString("health-modifier");
        this.adminLocationAddFail = Main.messageConfig.getString("admin-location-add-fail");
        this.adminChestAdded = Main.messageConfig.getString("admin-chest-added");
        this.adminChestFailed = Main.messageConfig.getString("admin-chest-failed");
        this.adminSpawnAdded = Main.messageConfig.getString("admin-spawn-added");
        this.adminMapAlreadyExists = Main.messageConfig.getString("admin-map-already-exists");
        this.adminMapAdded = Main.messageConfig.getString("admin-map-added");
        this.adminLobbySet = Main.messageConfig.getString("admin-lobby-set");
        this.commandFailed = Main.messageConfig.getString("command-failed");
        this.gameFull = Main.messageConfig.getString("game-full");
        this.customGameJoinPlayerUnspecified = Main.messageConfig.getString("custom-game-join-player-unspecified");
        this.kitAlreadyExists = Main.messageConfig.getString("kit-already-exists");
        this.invalidSyntax = Main.messageConfig.getString("invalid-syntax");
        this.kitCreated = Main.messageConfig.getString("kit-created");
        this.forcestartFailed = Main.messageConfig.getString("forcestart-failed");
        this.leaveFailed = Main.messageConfig.getString("leave-failed");
        this.returnToLobby = Main.messageConfig.getString("return-to-lobby");
        this.noGamesAvailable = Main.messageConfig.getString("no-games-available");
        this.noTeleport = Main.messageConfig.getString("no-teleport");

        this.killMessages = Main.messageConfig.getStringList("kill-messages");
        this.deathMessages = Main.messageConfig.getStringList("solo-death-messages");
        this.helpMessages = Main.messageConfig.getStringList("help-messages");
        this.adminHelpMessages = Main.messageConfig.getStringList("admin-help-messages");
    }


    public String getUnauthorized() {
        return unauthorized;
    }

    public String getDied() {
        return died;
    }

    public String getWin() {
        return win;
    }

    public String getJoinedGame(String playername) {
        return joinedGame.replace("{player}", playername);
    }

    public String getLeftGame(String playername) {
        return leftGame.replace("{player}", playername);
    }

    public String getTeamPicked() {
        return teamPicked;
    }

    public String getTeamGui() {
        return teamGui;
    }

    public String getRoomDeleted() {
        return roomDeleted;
    }

    public String getCustomGameMainGui() {
        return customGameMainGui;
    }

    public String getCustomGameEventGui() {
        return customGameEventGui;
    }

    public String getCustomGameIslandLootGui() {
        return customGameIslandLootGui;
    }

    public String getCustomGameMidLootGui() {
        return customGameMidLootGui;
    }

    public String getCustomGameModifierGui() {
        return customGameModifierGui;
    }

    public String getGuiBack() {
        return guiBack;
    }

    public String getAnvilEvent() {
        return anvilEvent;
    }

    public String getBlockEvent() {
        return blockEvent;
    }

    public String getMountEvent() {
        return mountEvent;
    }

    public String getZombieEvent() {
        return zombieEvent;
    }

    public String getCustomGameMainGuiIslandLoot() {
        return customGameMainGuiIslandLoot;
    }

    public String getCustomGameMainGuiMidLoot() {
        return customGameMainGuiMidLoot;
    }

    public String getCustomGameMainGuiEvents() {
        return customGameMainGuiEvents;
    }

    public String getCustomGameMainGuiModifiers() {
        return customGameMainGuiModifiers;
    }

    public String getCustomGameMainGuiSelectTeams() {
        return customGameMainGuiSelectTeams;
    }

    public String getCustomGameMainGuiDeleteRoom() {
        return customGameMainGuiDeleteRoom;
    }

    public String getSwordLoot() {
        return swordLoot;
    }

    public String getBowLoot() {
        return bowLoot;
    }

    public String getPearlLoot() {
        return pearlLoot;
    }

    public String getProjectileLoot() {
        return projectileLoot;
    }

    public String getArmorLoot() {
        return armorLoot;
    }

    public String getMiscLoot() {
        return miscLoot;
    }

    public String getJumpModifier() {
        return jumpModifier;
    }

    public String getSpeedModifier() {
        return speedModifier;
    }

    public String getHealthModifier() {
        return healthModifier;
    }

    public String getAdminLocationAddFail() {
        return adminLocationAddFail;
    }

    public String getAdminChestAdded() {
        return adminChestAdded;
    }

    public String getAdminChestFailed() {
        return adminChestFailed;
    }

    public String getAdminSpawnAdded() {
        return adminSpawnAdded;
    }

    public String getAdminMapAlreadyExists() {
        return adminMapAlreadyExists;
    }

    public String getAdminMapAdded() {
        return adminMapAdded;
    }

    public String getAdminLobbySet() {
        return adminLobbySet;
    }

    public String getCommandFailed() {
        return commandFailed;
    }

    public String getGameFull() {
        return gameFull;
    }

    public String getCustomGameJoinPlayerUnspecified() {
        return customGameJoinPlayerUnspecified;
    }

    public String getKitAlreadyExists() {
        return kitAlreadyExists;
    }

    public String getInvalidSyntax() {
        return invalidSyntax;
    }

    public String getKitCreated() {
        return kitCreated;
    }

    public String getForcestartFailed() {
        return forcestartFailed;
    }

    public String getLeaveFailed() {
        return leaveFailed;
    }

    public String getReturnToLobby() {
        return returnToLobby;
    }

    public String getNoGamesAvailable() {
        return noGamesAvailable;
    }

    public String getNoTeleport() {
        return noTeleport;
    }

    public String getJoinedGame() {
        return joinedGame;
    }

    public String getLeftGame() {
        return leftGame;
    }

    public List<String> getHelpMessages() {
        return helpMessages;
    }

    public List<String> getAdminHelpMessages() {
        return adminHelpMessages;
    }

    public String getRandomKillMessage(String killer, String player) {
        Random r = new Random();
        String message = killMessages.get(r.nextInt(killMessages.size()));
        message = message.replace("{killer}", killer).replace("{player}", player);
        return message;
    }

    public String getRandomSoloDeathMessage(String player) {
        Random r = new Random();
        String message = deathMessages.get(r.nextInt(deathMessages.size()));
        message = message.replace("{player}", player);
        return message;
    }
}
