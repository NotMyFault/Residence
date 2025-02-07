package com.bekvon.bukkit.residence.commands;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Zrips.CMILib.FileHandler.ConfigReader;
import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.cmd;

public class removeworld implements cmd {

    @Override
    @CommandAnnotation(simple = false, priority = 5200)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {

	if (args.length != 1)
	    return false;
	if (sender instanceof Player || sender instanceof BlockCommandSender) {
	    sender.sendMessage(ChatColor.RED + "MUST be run from console.");
	    return false;
	}
	plugin.getResidenceManager().removeAllFromWorld(sender, args[0]);

	return true;
    }

    @Override
    public void getLocale() {
	ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
	c.get("Description", "Removes all residences from particular world");
	c.get("Info", Arrays.asList("&eUsage: &6/res removeworld [worldName]"));
	LocaleManager.addTabCompleteMain(this, "[worldname]");
    }
}
