package com.gmail.val59000mc.commands;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.players.UhcPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReadyCommandExecutor implements CommandExecutor {

    private final GameManager gameManager;

    public ReadyCommandExecutor(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        UhcPlayer player = gameManager.getPlayerManager().getUhcPlayer((Player) sender);
        UhcPlayer leader = player.getTeam().getLeader();
        if (player == leader){
            player.getTeam().changeReadyState();
        } else {
            player.sendMessage(ChatColor.DARK_RED + "Insufficient permissions!");
        }
        return true;
    }
}
