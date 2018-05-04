package rush.comandos;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import rush.Main;
import rush.sistemas.comandos.BackListener;
import rush.utils.ConfigManager;

public class ComandoBack implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender s, Command cmd, String commandlabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("back")) {
			
			if (!(s instanceof Player)) {
			    s.sendMessage(ConfigManager.getConfig("mensagens").getString("Console-Nao-Pode").replace("&", "�"));
			    return false;
			}
			 
			if (!s.hasPermission("system.back")) {
		        s.sendMessage(ConfigManager.getConfig("mensagens").getString("Sem-Permissao").replaceAll("&", "�"));
		        return false;
		    }
			 
			Player p = (Player)s;
			ConcurrentHashMap<Player, Location> lista = BackListener.backList;
			
			if (!lista.containsKey(p)) {
		        s.sendMessage(ConfigManager.getConfig("mensagens").getString("Nao-Possui-Back").replaceAll("&", "�"));
		        return false;
			} else {
				Location l = lista.get(p);
				new BukkitRunnable() {
					@Override
				       public void run() {
						lista.remove(p);
					}
				}.runTaskLater(Main.aqui, 30);
		        s.sendMessage(ConfigManager.getConfig("mensagens").getString("Back-Teleportado-Sucesso").replaceAll("&", "�"));
				p.teleport(l);
				return false;
			}
		}
		return false;
	}
}