package rush.comandos;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import rush.Main;
import rush.configuracoes.Mensagens;
import rush.utils.DataManager;

public class ComandoWarpOLD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("warp")) {
			
			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode); 
				return true;
			}
				     
			// Verificando se o sender digitou o n�mero de argumentos correto
			if (args.length != 1) {
				s.sendMessage(Mensagens.Warp_Comando_Incorreto);
				return true;
			}
				     
			// Pegando a warp e verificando se ela existe
			String warp = args[0];
			File file = DataManager.getFile(warp, "warps");
			if (!file.exists()) {
				s.sendMessage(Mensagens.Warp_Nao_Existe.replace("%warp%", warp));
				ComandoWarps.ListWarps(s);
				return true;
			}
			
			// Pegando o player e a localiza��o
			Player p = (Player) s;
			FileConfiguration config = DataManager.getConfiguration(file);
			String locationSplitted = config.getString("Localizacao");
			Location location = deserializeLocation(locationSplitted);
			
			// Verificando se o player tem permiss�o para se teleportar a warp
			if (!s.hasPermission(config.getString("Permissao"))) {
				s.sendMessage(config.getString("MensagemSemPermissao").replace('&', '�'));
				return true;
			} 
				    	
			// Verificando se o player tem permiss�o para se teleportar sem delay
			if (!s.hasPermission("system.semdelay") || config.getBoolean("DelayParaVips") == true) {
				s.sendMessage(config.getString("MensagemInicio").replace('&', '�'));
				new BukkitRunnable() {
					@Override
					public void run() {
						s.sendMessage(config.getString("MensagemFinal").replace('&', '�'));
						p.teleport(location);
					}
				}.runTaskLater(Main.get(), 20 * config.getInt("Delay"));
				return true;
			}
				    	
			// Caso o player tiver permiss�o para se teleportar sem delay ent�o
			s.sendMessage(config.getString("MensagemFinal").replace('&', '�'));
			p.teleport(location);
			return true;
		}
		return false;
	}
    
    private Location deserializeLocation(String s) {
    	String[] locationSplitted = s.split(",");
		return new Location(
			   Bukkit.getWorld(locationSplitted[0]),
			   Double.parseDouble(locationSplitted[1]),
			   Double.parseDouble(locationSplitted[2]),
			   Double.parseDouble(locationSplitted[3]),
			   Float.parseFloat(locationSplitted[4]),
			   Float.parseFloat(locationSplitted[5]));
    }
}