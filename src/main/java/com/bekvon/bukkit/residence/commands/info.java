package com.bekvon.bukkit.residence.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.Zrips.CMILib.FileHandler.ConfigReader;
import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.cmd;
import com.bekvon.bukkit.residence.containers.lm;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;

public class info implements cmd {

    @Override
    @CommandAnnotation(simple = true, priority = 600)
    public Boolean perform(Residence plugin, CommandSender sender, String[] args, boolean resadmin) {

	if (args.length == 0 && sender instanceof Player) {
	    Player player = (Player) sender;
	    ClaimedResidence res = plugin.getResidenceManager().getByLoc(player.getLocation());

	    Set<ClaimedResidence> nearby = new HashSet<ClaimedResidence>();

	    Location loc = player.getLocation();
	    for (int x = -9; x <= 9; x = x + 3) {
		for (int z = -9; z <= 9; z = z + 3) {
		    for (int y = -9; y <= 9; y = y + 3) {
			if (x == 0 && z == 0 && y == 0)
			    continue;
			Location l = loc.clone().add(x, y, z);
			ClaimedResidence nr = plugin.getResidenceManager().getByLoc(l);
			if (nr != null)
			    nearby.add(nr);
		    }
		}
	    }
	    nearby.remove(res);

	    if (res != null) {
		plugin.getResidenceManager().printAreaInfo(res.getName(), sender, resadmin);
	    } else {
		if (nearby.isEmpty())
		    plugin.msg(sender, lm.Invalid_Residence);
	    }

	    StringBuilder list = new StringBuilder();

	    if (!nearby.isEmpty()) {
		for (ClaimedResidence one : nearby) {
		    if (!list.toString().isEmpty())
			list.append(", ");
		    list.append(one.getName());
		}
		plugin.msg(sender, lm.Residence_Near, list.toString());
	    }

	    return true;
	} else if (args.length == 1) {
	    plugin.getResidenceManager().printAreaInfo(args[0], sender, resadmin);
	    return true;
	}
	return false;
    }

    @Override
    public void getLocale() {
	ConfigReader c = Residence.getInstance().getLocaleManager().getLocaleConfig();
	c.get("Description", "Show info on a residence.");
	c.get("Info", Arrays.asList("&eUsage: &6/res info <residence>", "Leave off <residence> to display info for the residence your currently in."));
	LocaleManager.addTabCompleteMain(this, "[residence]");
    }
}
