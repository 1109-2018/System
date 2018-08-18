package rush.comandos;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import rush.configuracoes.Locations;
import rush.configuracoes.Mensagens;
import rush.utils.DataManager;

public class ComandoSetmundovip implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setmundovip")) {
			
			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode); 
				return true;
			}
					 
			// Verificando se o player digitou o n�mero de argumentos corretos
			if (args.length != 1) {
				s.sendMessage(Mensagens.Setmundovip_Comando_Incorreto);
				return true;
			}
				     
			// Pegando o player, a sua localiza��o, o arquivo das locations e a config
			Player p = (Player)s;
			Location area = p.getLocation();
			File file = DataManager.getFile("locations");
			FileConfiguration config = DataManager.getConfiguration(file);

			// Verificando se o player quer definir a dos vips (areavip)
			if (args[0].equalsIgnoreCase("areavip")) {
				
				// Alterando a localiza��o no cache
				Locations.areaVip = area;

				// Serializando a localiza��o e salvando na config
				config.set("AreaVip.world", area.getWorld().getName());
				config.set("AreaVip.x", Double.valueOf(area.getX()));
				config.set("AreaVip.y", Double.valueOf(area.getY()));
				config.set("AreaVip.z", Double.valueOf(area.getZ()));
				config.set("AreaVip.yaw", Float.valueOf(area.getYaw()));
				config.set("AreaVip.pitch", Float.valueOf(area.getPitch()));
				try {
					config.save(file);
					s.sendMessage(Mensagens.Area_Vip_Definida);
				} catch (IOException e) {
					Bukkit.getConsoleSender().sendMessage(Mensagens.Falha_Ao_Salvar.replace("%arquivo%", "locations.yml"));
				}
				return true;
			}
			 
			// Verificando se o player quer definir a dos sem vips (areanaovip)
			if (args[0].equalsIgnoreCase("areanaovip")) {
				
				// Alterando a localiza��o no cache
				Locations.areaNaoVip = area;
				
				// Serializando a localiza��o e salvando na config
				config.set("AreaNaoVip", area);
				config.set("AreaNaoVip.world", area.getWorld().getName());
				config.set("AreaNaoVip.x", Double.valueOf(area.getX()));
				config.set("AreaNaoVip.y", Double.valueOf(area.getY()));
				config.set("AreaNaoVip.z", Double.valueOf(area.getZ()));
				config.set("AreaNaoVip.yaw", Float.valueOf(area.getYaw()));
				config.set("AreaNaoVip.pitch", Float.valueOf(area.getPitch()));
				try {
					config.save(file);
					s.sendMessage(Mensagens.Area_Nao_Vip_Definida);
				} catch (IOException e) {
					Bukkit.getConsoleSender().sendMessage(Mensagens.Falha_Ao_Salvar.replace("%arquivo%", "locations.yml"));
				}
				return true;
			}
			
			// Caso o argumento digitado n�o for 'areavip' ou 'areanaovip' sera dado como comando incorreto
			s.sendMessage(Mensagens.Setmundovip_Comando_Incorreto);
			return true;
		}
		return false;
	}
}