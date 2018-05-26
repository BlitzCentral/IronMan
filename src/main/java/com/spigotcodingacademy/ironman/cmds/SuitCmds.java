package com.spigotcodingacademy.ironman.cmds;

import com.spigotcodingacademy.ironman.Main;
import com.spigotcodingacademy.ironman.Menus;
import com.spigotcodingacademy.ironman.manager.Data;
import com.spigotcodingacademy.ironman.manager.SuitManager;
import com.spigotcodingacademy.ironman.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuitCmds implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        // /suit <Player> <SuitName>
        if (cmd.getName().equalsIgnoreCase("suits")) {
            if (!player.hasPermission("ironman.suits")) {
                Chat.msg(player, Chat.perm);
                return true;
            }

            if (args.length == 0) {
                Chat.msg(
                        player,
                        Chat.prefix + "&7Suit commands:",
                        "&8&l >> &7/suits menu",
                        "&8&l >> &7/suits set <Player> <SuitName>",
                        "&8&l >> &7/suits list",
                        "&8&l >> &7/suits eject"
                );
                return true;
            }

            if (args[0].equalsIgnoreCase("check")) {
                if (Data.buildingSuit.contains(player)) {
                    player.sendMessage(Chat.prefix + "Building suit: YES");
                    return true;
                }
                if (!Data.buildingSuit.contains(player)) {
                    player.sendMessage(Chat.prefix + "Building suit: NO");
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("menu")) {
                Menus.createTestMenu(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {
                Chat.msg(
                        player,
                        Chat.prefix + "&7Suit list:",
                        "&8&l >> &7Mk 1"
                );
                return true;
            }

            if (args[0].equalsIgnoreCase("eject")) {
                if (Data.Suit.contains(player)){
                    Main.getSuitManager().eject(player);
                    return true;
                } else{
                    Chat.msg(player, Chat.jarvis + "&4No suit to eject from!");
                }
            }

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("set")) {
                    Player target = Bukkit.getServer().getPlayer(args[1]);
                    String suit = args[2];
                    if (args[1] == null) {
                        Chat.msg(
                                player,
                                Chat.prefix + "&cError! Incorrect usage!",
                                "&8&l >> &7/suits set <Player> <SuitName>"
                        );
                        return true;
                    }

                    if (args[2] == null) {
                        Chat.msg(
                                player,
                                Chat.prefix + "&cError! Incorrect usage!",
                                "&8&l >> &7/suits set <Player> <SuitName>"
                        );
                        return true;
                    }

                    if (target == null) {
                        Chat.msg(
                                player,
                                Chat.prefix + "&cError! Player is not online or does not exist!"
                        );
                        return true;
                    }

                    if (Data.Suit.contains(target)) {
                        Chat.msg(player, Chat.prefix + "&6Suit set by: &a" + ((Player) sender).getDisplayName() + "&6!");
                        Main.getSuitManager().eject(target);
                        Main.getSuitManager().apply(target);
                        return true;
                    }

                    if (suit.equalsIgnoreCase("mark1")) {
                        Chat.msg(player, Chat.prefix + "&6Suit set by: &a" + ((Player) sender).getDisplayName() + "&6!");
                        Main.getSuitManager().apply(target);
                    }

                }
            }
        }
        return false;
    }
}