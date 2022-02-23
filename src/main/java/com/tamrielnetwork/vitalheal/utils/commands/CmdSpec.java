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

package com.tamrielnetwork.vitalheal.utils.commands;

import com.google.common.collect.ImmutableMap;
import com.tamrielnetwork.vitalheal.utils.Chat;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CmdSpec {

	public static boolean isInvalidCmd(@NotNull CommandSender sender, Player player, @NotNull String perm) {

		if (Cmd.isNotPermitted(sender, perm)) {
			return true;
		}
		return Cmd.isInvalidPlayer(sender, player);
	}

	public static void doHeal(@NotNull CommandSender sender, @NotNull Player player) {

		Chat.sendMessage(sender, ImmutableMap.of("%player%", player.getName()), "player-healed");
		player.setHealth(20);
	}

	public static void doHeal(@NotNull Player senderPlayer) {

		Chat.sendMessage(senderPlayer, "healed");
		senderPlayer.setHealth(20);
	}

}
