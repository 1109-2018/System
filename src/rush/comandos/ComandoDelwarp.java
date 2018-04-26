package rush.comandos;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import rush.utils.ConfigManager;
import rush.utils.DataManager;

public class ComandoDelwarp implements Listener, CommandExecutor {
	
	@Override
	 public boolean onCommand(final CommandSender s, Command cmd, String lbl, String[] args) {
		 if (cmd.getName().equalsIgnoreCase("delwarp")) {
			 if (s.hasPermission("system.delwarp")) {					 					 
			     if (args.length == 0) {
			          s.sendMessage(ConfigManager.getConfig("mensagens").getString("DelWarp-Comando-Incorreto").replaceAll("&", "�"));
			          return false;
			     } 
				     
			     if (args.length > 1) {
			          s.sendMessage(ConfigManager.getConfig("mensagens").getString("DelWarp-Comando-Incorreto").replaceAll("&", "�"));
			          return false;
			     } 
				     
			     else {
			    	 String warp = args[0];
			    	 File file = DataManager.getFile(warp, "warps");
			    	 if (file.exists()) {
			    		 DataManager.deleteFile(file);
			    		 s.sendMessage(ConfigManager.getConfig("mensagens").getString("Warp-Deletada").replace("&", "�").replace("%warp%", warp));
			    	 } 
				        
			    	 else {
			    		 s.sendMessage(ConfigManager.getConfig("mensagens").getString("Warp-Nao-Existe").replace("&", "�").replace("%warp%", warp));
					     ComandoWarps.ListWarps(s);
			    	 }
			     }
				 return false;
			 }
			 s.sendMessage(ConfigManager.getConfig("mensagens").getString("Sem-Permissao").replace("&", "�"));
			 return false;
		 }
		return false;
	}
}
