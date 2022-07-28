/*
 * VitalHeal is a Spigot Plugin that gives players the ability to heal.
 * Copyright © 2022 Leopold Meinel & contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://github.com/LeoMeinel/VitalHeal/blob/main/LICENSE
 */

package dev.meinel.leo.vitalheal.utils.commands;

import dev.meinel.leo.vitalheal.VitalHeal;
import dev.meinel.leo.vitalheal.utils.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CmdSpec {

	private static final VitalHeal main = JavaPlugin.getPlugin(VitalHeal.class);
	private static final HashMap<UUID, Long> cooldownMap = new HashMap<>();

	private CmdSpec() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isInvalidCmd(@NotNull CommandSender sender, Player player, @NotNull String perm) {
		return Cmd.isNotPermitted(sender, perm) || Cmd.isInvalidPlayer(sender, player) || isOnCooldown(sender);
	}

	public static boolean isInvalidCmd(@NotNull CommandSender sender, @NotNull String perm) {
		return Cmd.isNotPermitted(sender, perm) || isOnCooldown(sender);
	}

	private static void clearMap(@NotNull CommandSender sender) {
		Player senderPlayer = (Player) sender;
		cooldownMap.remove(senderPlayer.getUniqueId());
	}

	private static void doTiming(@NotNull CommandSender sender) {
		new BukkitRunnable() {

			@Override
			public void run() {
				clearMap(sender);
			}
		}.runTaskLaterAsynchronously(main, (main.getConfig()
		                                        .getLong("cooldown.time") * 20L));
	}

	private static boolean isOnCooldown(@NotNull CommandSender sender) {
		Player senderPlayer = (Player) sender;
		boolean isOnCooldown = main.getConfig()
		                           .getBoolean("cooldown.enabled") && !sender.hasPermission("vitalheal.cooldown.bypass")
		                       && cooldownMap.containsKey(senderPlayer.getUniqueId());
		if (isOnCooldown) {
			String timeRemaining = String.valueOf(
					cooldownMap.get(senderPlayer.getUniqueId()) - System.currentTimeMillis() / 1000);
			Chat.sendMessage(sender, Map.of("%time-left%", timeRemaining), "cooldown-active");
			return true;
		}
		cooldownMap.put(senderPlayer.getUniqueId(), main.getConfig()
		                                                .getLong("cooldown.time") + System.currentTimeMillis() / 1000);
		doTiming(sender);
		return false;
	}
}
