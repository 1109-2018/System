package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.Main;
import rush.apis.TitleAPI;
import rush.configuracoes.Mensagens;

public class ComandoDivulgar implements CommandExecutor {
		
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
	        	
		// Verificando se o player digitou o n�mero de argumentos corretos
		if (args.length != 2) { 
			s.sendMessage(Mensagens.Divulgar_Comando_Incorreto);
			return true;
		}
	        
		// Verificando se o player esta divulgando algo valido
		if (!args[0].equalsIgnoreCase("live") && !args[0].equalsIgnoreCase("video") && !args[0].equalsIgnoreCase("outro")) {
			s.sendMessage(Mensagens.Divulgar_Comando_Incorreto);
			return true;
		}
	        
		// Verificando se o link � valido
		if (!isValidLink(args[1])) {
			s.sendMessage(Mensagens.Link_Invalido.replace("%link%", args[1]));
			return true;
		}
		
		// Verificando a vers�o e enviando um title para de aviso para todos os players do server
		if (!Main.isOldVersion()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				TitleAPI.sendTitle(p, 20, 60, 20,
		        Mensagens.Divulgando_Title.replace("%link%", args[1]).replace("%player%", s.getName()),
				Mensagens.Divulgando_SubTitle.replace("%link%", args[1]).replace("%player%", s.getName()));
			}
		}
		        	
		// Caso a divulga��o seja de uma live
		if (args[0].equalsIgnoreCase("live")) {
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(Mensagens.Divulgando_Live.replace("%player%", s.getName()));
			Bukkit.broadcastMessage(Mensagens.Link.replace("%link%", args[1]));
			Bukkit.broadcastMessage("");
			return true;
		}
			 
		// Caso a divulga��o seja de um v�deo
		if (args[0].equalsIgnoreCase("video")) {
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(Mensagens.Divulgando_Video.replace("%player%", s.getName()));
			Bukkit.broadcastMessage(Mensagens.Link.replace("%link%", args[1]));
			Bukkit.broadcastMessage("");
			return true;
		}
			        
		// Caso a divulga��o seja de um outro link
		if (args[0].equalsIgnoreCase("outro")) {
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage(Mensagens.Divulgando_Outro.replace("%player%", s.getName()));
			Bukkit.broadcastMessage(Mensagens.Link.replace("%link%", args[1]));
			Bukkit.broadcastMessage("");
			return true;
		}
	    return true;
	}
	
	// M�todo para verificar se o link � valido
	private boolean isValidLink(String link) {
		if (link.contains("http") || 
		   (link.contains("www")  || 
       	   (link.contains(".com")) || 
	       (link.contains(".br"))  || 
	       (link.contains(".net")) ||
	       (link.contains(".org")) ||
	       (link.contains(".ly"))  ||
	       (link.contains(".sc"))  ||
	       (link.contains(".me"))  || 
	       (link.contains(".tk"))))
		   return true;
		   else return false;
	}
}	
