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
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalHeal/blob/main/LICENSE
 */

package com.tamrielnetwork.vitalheal.commands;

import com.tamrielnetwork.vitalheal.utils.commands.Cmd;
import com.tamrielnetwork.vitalheal.utils.commands.CmdSpec;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VitalHealCmd implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (Cmd.isArgsLengthGreaterThan(sender, args, 1)) {
			return true;
		}
		doHeal(sender, args);
		return true;

	}

	private void doHeal(@NotNull CommandSender sender, @NotNull String[] args) {

		if (Cmd.isInvalidSender(sender)) {
			return;
		}
		Player senderPlayer = (Player) sender;

		if (args.length == 1) {
			Player player = Bukkit.getPlayer(args[0]);

			if (CmdSpec.isInvalidCmd(sender, player, "vitalheal.heal.others")) {
				return;
			}

			assert player != null;

			CmdSpec.doHeal(senderPlayer, player);
			return;
		}
		if (CmdSpec.isInvalidCmd(sender, "vitalheal.heal")) {
			return;
		}
		CmdSpec.doHeal(senderPlayer);
	}

}