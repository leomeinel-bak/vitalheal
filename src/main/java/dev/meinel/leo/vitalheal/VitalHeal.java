/*
 * File: VitalHeal.java
 * Author: Leopold Meinel (leo@meinel.dev)
 * -----
 * Copyright (c) 2023 Leopold Meinel & contributors
 * SPDX ID: GPL-3.0-or-later
 * URL: https://www.gnu.org/licenses/gpl-3.0-standalone.html
 * -----
 */

package dev.meinel.leo.vitalheal;

import dev.meinel.leo.vitalheal.commands.VitalHealCmd;
import dev.meinel.leo.vitalheal.files.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class VitalHeal
        extends JavaPlugin {

    private Messages messages;

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("heal"))
                .setExecutor(new VitalHealCmd());
        saveDefaultConfig();
        messages = new Messages();
        Bukkit.getLogger()
                .info("VitalHeal v" + this.getPluginMeta().getVersion() + " enabled");
        Bukkit.getLogger()
                .info("Copyright (C) 2022 Leopold Meinel");
        Bukkit.getLogger()
                .info("This program comes with ABSOLUTELY NO WARRANTY!");
        Bukkit.getLogger()
                .info("This is free software, and you are welcome to redistribute it under certain conditions.");
        Bukkit.getLogger()
                .info("See https://www.gnu.org/licenses/gpl-3.0-standalone.html for more details.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger()
                .info("VitalHeal v" + this.getPluginMeta().getVersion() + " disabled");
    }

    public Messages getMessages() {
        return messages;
    }
}
