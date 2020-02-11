package io.github.complexcodegit.hidepluginsproject.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import io.github.complexcodegit.hidepluginsproject.HidePluginsProject;

public class GroupManager {
    public static List<String> groupsPermissions = new ArrayList<>();
    public static List<String> playerPermissions = new ArrayList<>();
    public static List<String> groupsList = new ArrayList<>();
    public static String permission = "";
    public static String group = "";

    public static List<String> getGroups(HidePluginsProject plugin) {
        for(String groups : plugin.getGroups().getConfigurationSection("groups").getKeys(false)) {
            if(!groupsList.contains(groups)) {
                groupsList.add(groups);
            }
        }
        return groupsList;
    }

    public static List<String> getGroupsPermissions(HidePluginsProject plugin) {
        for(String groups : plugin.getGroups().getConfigurationSection("groups").getKeys(false)) {
            if(!groupsPermissions.contains("hidepluginsproject.group."+groups)) {
                groupsPermissions.add("hidepluginsproject.group."+groups);
            }
        }
        return groupsPermissions;
    }

    public static String getPlayerGroupPermission(Player player, HidePluginsProject plugin) {
        for(PermissionAttachmentInfo pio : player.getEffectivePermissions()) {
            String perm = pio.getPermission();
            playerPermissions.add(perm);
        }
        for(int i=0; i < getGroupsPermissions(plugin).size(); i++) {
            if(playerPermissions.contains(getGroupsPermissions(plugin).get(i))) {
                permission = getGroupsPermissions(plugin).get(i);
            }
        }
        if(permission.isEmpty() || permission == null) {
            permission = "hidepluginsproject.group.default";
        }
        return permission;
    }

    public static String getPlayerGroup(Player player, HidePluginsProject plugin) {
        String groupPerm = getPlayerGroupPermission(player, plugin);
        group = groupPerm.substring(25, permission.length());
        return group;
    }

    public static List<String> getCommandsList(Player player, HidePluginsProject plugin){
        List<String> commandsList = new ArrayList<>();
        for(String commands : plugin.getGroups().getStringList("groups."+getPlayerGroup(player, plugin)+".commands")) {
            if(!commandsList.contains("/"+commands)) {
                commandsList.add("/"+commands);
            }
        }
        return commandsList;
    }
}
