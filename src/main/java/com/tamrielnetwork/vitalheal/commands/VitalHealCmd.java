/*
 * VitalHeal is a Spigot Plugin that gives players the ability to heal.
 * Copyright © 2022 Leopold Meinel
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
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalHeal/blob/main/LICENSE
 */

package com.tamrielnetwork.vitalheal.commands;

import com.google.common.collect.ImmutableMap;
import com.tamrielnetwork.vitalheal.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VitalHealCmd implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		// Check args length
		if (args.length > 1) {
			Utils.sendMessage(sender, "invalid-option");
			return true;
		}
		// Toggle Crafting Interface
		healPlayer(sender, args);
		return true;

	}

	private void healPlayer(CommandSender sender, String[] args) {
		// Check if command sender is a player
		if (!(sender instanceof Player)) {
			Utils.sendMessage(sender, "player-only");
			return;
		}
		// Check perms
		if (!sender.hasPermission("vitalheal.heal")) {
			Utils.sendMessage(sender, "no-perms");
			return;
		}

		// Check args length
		if (args.length == 1) {
			// Check perms
			if (!sender.hasPermission("vitalheal.heal.others")) {
				Utils.sendMessage(sender, "no-perms");
				return;
			}
			if (Bukkit.getPlayer(args[0]) == null) {
				Utils.sendMessage(sender, "invalid-player");
				return;
			}
			Player player = Bukkit.getPlayer(args[0]);
			boolean isOnline = Objects.requireNonNull(player).isOnline();
			if (!isOnline) {
				Utils.sendMessage(sender, "not-online");
				return;
			}
			Utils.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "player-healed");
			player.setHealth(20);
			return;
		}
		Utils.sendMessage(sender,"healed");
		((Player) sender).setHealth(20);
	}
}