package rush.comandos;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import rush.utils.ConfigManager;
import rush.utils.DataManager;

public class ComandoParticular implements Listener, CommandExecutor {
	
	@Override
	public boolean onCommand(final CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("particular")) {
			
			 if (!(s instanceof Player)) {
				 s.sendMessage(ConfigManager.getConfig("mensagens").getString("Console-Nao-Pode").replace("&", "�"));
				 return false;
			 }
			
		     if (args.length == 0) {
		          s.sendMessage(ConfigManager.getConfig("mensagens").getString("Particular-Comando-Incorreto").replaceAll("&", "�"));
		          return false;
		     } 
		     
		     if (args.length > 1) {
		          s.sendMessage(ConfigManager.getConfig("mensagens").getString("Particular-Comando-Incorreto").replaceAll("&", "�"));
		          return false;
		     }
		     
		    String home = args[0];
	  		String player = s.getName().toLowerCase();
	        File file = DataManager.getFile(player.toLowerCase(), "playerdata");
	        FileConfiguration config = DataManager.getConfiguration(file);
	        Set<String> KEYS = config.getConfigurationSection("Homes").getKeys(false);
		   		
	        if (!KEYS.contains(home)) {
	        	s.sendMessage(ConfigManager.getConfig("mensagens").getString("Home-Nao-Existe").replaceAll("&", "�").replace("%home%", home));
	   			ComandoHomes.ListHomes(s, player);
	        	return false;
	   		}
	   		
	   		else {
			    boolean isPublic = config.getBoolean("Homes." + home + ".Publica");
			    if (isPublic == true) {
					config.set("Homes." + home + ".Publica" , false);
					try {
						config.save(file);
						s.sendMessage(ConfigManager.getConfig("mensagens").getString("Tornou-Home-Particular").replace("&", "�").replace("%home%", home));
					} catch (IOException e) {
						Bukkit.getConsoleSender().sendMessage(ConfigManager.getConfig("mensagens").getString("Falha-Ao-Salvar").replace("&", "�").replace("%arquivo%", file.getName()));
					}
			    } else {
					s.sendMessage(ConfigManager.getConfig("mensagens").getString("Home-Ja-Particular").replace("&", "�").replace("%home%", home));
			    }
	   		}
		}
		return false;
	}
}