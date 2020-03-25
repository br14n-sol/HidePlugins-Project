package io.github.complexcodegit.hidepluginsproject.managers;

import io.github.complexcodegit.hidepluginsproject.HidePluginsProject;

import java.io.File;

public class FileManager {
    public static void save(HidePluginsProject plugin){
        File groupd = new File(plugin.getDataFolder(), "groups.yml");
        File playerd = new File(plugin.getDataFolder(), "players.yml");
        File languagesd = new File(plugin.getDataFolder(), "languages.yml");
        File commandsd = new File(plugin.getDataFolder(), "commands.yml");
        if(!groupd.exists())
            plugin.saveResource("groups.yml", false);
        if(!playerd.exists())
            plugin.saveResource("players.yml", false);
        if(!languagesd.exists())
            plugin.saveResource("languages.yml", false);
        if(!commandsd.exists())
            plugin.saveResource("commands.yml", false);
    }
}
