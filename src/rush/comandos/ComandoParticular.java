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

import rush.configuracoes.Mensagens;
import rush.utils.DataManager;

public class ComandoParticular implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("particular")) {
			
			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode); 
				return false;
			}
			
			// Verificando se o player digitou o n�mero de argumentos corretos
			if (args.length != 1) {
				s.sendMessage(Mensagens.Particular_Comando_Incorreto);
				return false;
			}
		     
			// Pegando a home, o player o arquivo do player e as homes do player
		    String home = args[0];
	  		String player = s.getName().toLowerCase();
	        File file = DataManager.getFile(player.toLowerCase(), "playerdata");
	        FileConfiguration config = DataManager.getConfiguration(file);
	        Set<String> HOMES = config.getConfigurationSection("Homes").getKeys(false);
		   		
	        // Verificando se a home existe
	        if (!HOMES.contains(home)) {
	        	s.sendMessage(Mensagens.Home_Nao_Existe.replace("%home%", home));
	   			ComandoHomes.ListHomes(s, player);
	        	return false;
	   		}
	   		
	        // Verificando se a home j� � particular
		    boolean isPublic = config.getBoolean("Homes." + home + ".Publica");
		    if (!isPublic) {
		    	s.sendMessage(Mensagens.Home_Ja_Particular.replace("%home%", home));
		    	return false;
		    }
		    
		    // Setando a home como particular salvando no arquivo
	    	config.set("Homes." + home + ".Publica" , false);
	    	try {
	    		config.save(file);
	    		s.sendMessage(Mensagens.Tornou_Home_Particular.replace("%home%", home));
	    	} catch (IOException e) {
	    		Bukkit.getConsoleSender().sendMessage(Mensagens.Falha_Ao_Salvar.replace("%arquivo%", file.getName()));
	    	}
		}
		return false;
	}
}