package rush.comandos;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import rush.configuracoes.Mensagens;
import rush.utils.DataManager;

public class ComandoSethome implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sethome")) {
			
			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode); 
				return false;
			}
					
			// Verificando se o player digitou o n�mero de argumentos corretos
			if (args.length != 1) {
				s.sendMessage(Mensagens.SetHome_Comando_Incorreto);
				return false;
			}
				     
			// Pegando o player, a home a ser deleta, o arquivo do player a config e alista de homes
			Player p = (Player) s;
			String home = args[0].replace(":", "");
			File file = DataManager.getFile(p.getName().toLowerCase(), "playerdata");
			FileConfiguration config = DataManager.getConfiguration(file);
			Set<String> HOMES = config.getConfigurationSection("Homes").getKeys(false);
			int homes = HOMES.size();
			
			// Verificando se o player j� atingiu o limite m�ximo de homes permitidas
			int limite = getHomesLimit(p);
			if (homes >= limite) {
				s.sendMessage(Mensagens.Limite_De_Homes_Atingido.replace("%limite%", String.valueOf(limite)));
				return false;
			} 
			
			// Pegando a localiza��o do player, serializando e salvando no arquivo
			Location location = p.getLocation();
			String locationSerialized = location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
			config.set("Homes." + home + ".Localizacao" , locationSerialized);
			config.set("Homes." + home + ".Publica" , false);
			try {
				config.save(file);
				s.sendMessage(Mensagens.Home_Definida.replace("%home%", home));
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(Mensagens.Falha_Ao_Salvar.replace("%arquivo%", file.getName()));
			}
		}
		return false;
	}
	
	/**
	 * Powered by kickpost;
	 */
	
    private int getHomesLimit(Player p) {
    	try {
    		return Integer.parseInt(p.getEffectivePermissions().stream()
    			   .filter(r -> r.getPermission().toLowerCase().startsWith("system.homes."))
    			   .findFirst().get().getPermission().replace("system.homes.", "").trim());
    	} catch (Exception e) {
    		return 1;
    	}
	}
}
