package io.github.complexcodegit.hidepluginsproject;

import io.github.complexcodegit.hidepluginsproject.commands.ChiefCommand;
import io.github.complexcodegit.hidepluginsproject.events.LockedCommands;
import io.github.complexcodegit.hidepluginsproject.events.PlayerChangeWorld;
import io.github.complexcodegit.hidepluginsproject.events.PlayerJoinData;
import io.github.complexcodegit.hidepluginsproject.events.PlayerQuitData;
import io.github.complexcodegit.hidepluginsproject.events.TabCompletes;
import io.github.complexcodegit.hidepluginsproject.managers.FileManager;
import io.github.complexcodegit.hidepluginsproject.managers.GroupManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

@SuppressWarnings({"ConstantConditions", "CharsetObjectCanBeUsed"})
public class HidePluginsProject extends JavaPlugin implements Listener {
    private FileConfiguration groups;
    private File groupsFile;
    private FileConfiguration languages;
    private File languagesFile;
    private FileConfiguration players;
    private File playersFile;

    public void onEnable(){
        registerConfig();
        FileManager.save(this);
        registerEvents();
        registerCommands();
    }
    public void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new LockedCommands(this, new GroupManager(this)), this);
        pm.registerEvents(new TabCompletes(this, new GroupManager(this)), this);
        pm.registerEvents(new PlayerChangeWorld(), this);
        pm.registerEvents(new PlayerJoinData(this, new GroupManager(this)), this);
        pm.registerEvents(new PlayerQuitData(this, new GroupManager(this)), this);
    }
    public void registerCommands(){
        getCommand("hproject").setExecutor(new ChiefCommand(this, new GroupManager(this)));
    }
    public String colors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public void registerConfig() {
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
    }
    public void reloadGroups() {
        if(groups == null) {
            groupsFile = new File(getDataFolder(), "groups.yml");
        }

        groups = YamlConfiguration.loadConfiguration(groupsFile);
        try {
            Reader defConfigStream = new InputStreamReader(getResource("groups.yml"), "UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                groups.setDefaults(defConfig);
            }
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void saveGroups() {
        try {
            groups.save(groupsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getGroups() {
        if(groups == null) {
            reloadGroups();
        }
        return groups;
    }
    public void reloadLanguages() {
        if(languages == null) {
            languagesFile = new File(getDataFolder(), "languages.yml");
        }

        languages = YamlConfiguration.loadConfiguration(languagesFile);
        try {
            Reader defConfigStream = new InputStreamReader(getResource("languages.yml"), "UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                languages.setDefaults(defConfig);
            }
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void saveLanguages() {
        try {
            languages.save(languagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public FileConfiguration getLanguages() {
        if(languages == null) {
            reloadLanguages();
        }
        return languages;
    }
    public FileConfiguration getPlayers() {
        if(players == null) {
            reloadPlayers();
        }
        return players;
    }
    public void reloadPlayers() {
        if(players == null) {
            playersFile = new File(getDataFolder(), "players.yml");
        }

        players = YamlConfiguration.loadConfiguration(playersFile);
        try {
            Reader defConfigStream = new InputStreamReader(getResource("players.yml"), "UTF8");
            if(defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                players.setDefaults(defConfig);
            }
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void savePlayers() {
        try {
            players.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
