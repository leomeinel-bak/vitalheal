/*
 * File: VitalHealCmd.java
 * Author: Leopold Meinel (leo@meinel.dev)
 * -----
 * Copyright (c) 2022 Leopold Meinel & contributors
 * SPDX ID: GPL-3.0-or-later
 * URL: https://www.gnu.org/licenses/gpl-3.0-standalone.html
 * -----
 */

package dev.meinel.leo.vitalheal.commands;

import dev.meinel.leo.vitalheal.utils.Chat;
import dev.meinel.leo.vitalheal.utils.commands.Cmd;
import dev.meinel.leo.vitalheal.utils.commands.CmdSpec;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class VitalHealCmd
		implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
			@NotNull String[] args) {
		if (Cmd.isArgsLengthGreaterThan(sender, args, 1)) {
			return false;
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
			Chat.sendMessage(sender, Map.of("%player%", player.getName()), "player-healed");
			player.setHealth(20);
			return;
		}
		if (CmdSpec.isInvalidCmd(sender, "vitalheal.heal")) {
			return;
		}
		Chat.sendMessage(senderPlayer, "healed");
		senderPlayer.setHealth(20);
	}
}