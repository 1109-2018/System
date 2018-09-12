package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.apis.GodModeAPI;
import rush.configuracoes.Mensagens;

@SuppressWarnings("all")
public class ComandoGod implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			
			// Verificando se o sender digitou o n�mero de argumentos correto
			if (args.length > 2 || args.length < 1) {
				s.sendMessage(Mensagens.God_Comando_Incorreto);
				return true;
			}
    		  
			// Pegando o player e verificando se ele esta online
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				s.sendMessage(Mensagens.Player_Offline);
				return true;
			}
    		  				
			// Verificando se o sender informou mais de 1 argumento /god <player> [on/off]
			if (args.length > 1) {
				
				// Verificando se esse argumento � a palavra 'on'
				if (args[1].equalsIgnoreCase("on")) {
					
					// Verificando se o player j� esta com god ligado, caso contrario o god � ligado
					if (GodModeAPI.getGodMode(p)) {
						s.sendMessage(Mensagens.God_Ja_Habilitado_Outro.replace("%player%", p.getName()));
					} else {
						s.sendMessage(Mensagens.God_Habilitado_Outro.replace("%player%", p.getName()));
						GodModeAPI.setGodMode(p, true);
					}
					return true;
				}
	    		  
				// Verificando se esse argumento � a palavra 'off'
				if (args[1].equalsIgnoreCase("off")) {
					
					// Verificando se o player esta esta com o god ligado, caso contrar � exibida uma mensagem
					if (GodModeAPI.getGodMode(p)) {
						s.sendMessage(Mensagens.God_Desabilitado_Outro.replace("%player%", p.getName()));
						GodModeAPI.setGodMode(p, false);
					} else {
						s.sendMessage(Mensagens.God_Ja_Desabilitado_Outro.replace("%player%", p.getName()));
					}
					return true;
				}
			}
    		  
			// Se o player n�o informar 2 argumentos, ou se o segundo argumento n�o for 'on' ou 'off'
			if (GodModeAPI.getGodMode(p)) {
				s.sendMessage(Mensagens.God_Desabilitado_Outro.replace("%player%", p.getName()));
				GodModeAPI.setGodMode(p, false);
			} else {
				s.sendMessage(Mensagens.God_Habilitado_Outro.replace("%player%", p.getName()));
				GodModeAPI.setGodMode(p, true);
			}
			return true;
		}
					
		// Verificando se o player informou ao menos 1 argumento /god [on/off/player]
		if (args.length > 0) {
			
			// Verificando se esse argumento � a palavra 'on'
			if (args[0].equalsIgnoreCase("on")) {
				Player p = (Player)s;
				if (GodModeAPI.getGodMode(p)) {
					s.sendMessage(Mensagens.God_Ja_Habilitado_Voce);
				} else {
					s.sendMessage(Mensagens.God_Habilitado_Voce);
					GodModeAPI.setGodMode(p, true);
				}
				return true;
			}
    		  
			// Verificando se esse argumento � a palavra 'off'
			if (args[0].equalsIgnoreCase("off")) {
				Player p = (Player)s;
				if (GodModeAPI.getGodMode(p)) {
					s.sendMessage(Mensagens.God_Desabilitado_Voce);
					GodModeAPI.setGodMode(p, false);
				} else {
					s.sendMessage(Mensagens.God_Ja_Desabilitado_Voce);
				}
				return true;
			}
    		
			/** Caso o primeiro argumento n�o for a palavra 'on' ou 'off' entende-se que � o nome de um player */
			
			// Verificando se o player possui permiss�o para alterar o god de outros players
			if (s.hasPermission("system.god.outros")) {
	    		
				// Verificando se o sender digitou o n�mero de argumentos correto
				if (args.length > 2) {
					s.sendMessage(Mensagens.God_Comando_Incorreto);
					return true;
				}
				
				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return true;
				}
				
				// Verificando se o player informou mais de 1 argumento /god <player> [on/off]
				if (args.length > 1) {
					
					// Verificando se esse argumento � a palavra 'on'
					if (args[1].equalsIgnoreCase("on")) {
						
						// Verificando se o player j� esta com god ligado, caso contrario o god � ligado
						if (GodModeAPI.getGodMode(p)) {
							s.sendMessage(Mensagens.God_Ja_Habilitado_Outro.replace("%player%", p.getName()));
						} else {
							s.sendMessage(Mensagens.God_Habilitado_Outro.replace("%player%", p.getName()));
							GodModeAPI.setGodMode(p, true);
						}
						return true;
					}
					
					// Verificando se esse argumento � a palavra 'off'
					if (args[1].equalsIgnoreCase("off")) {
						
						// Verificando se o player esta esta com o god ligado, caso contrar � exibida uma mensagem
						if (GodModeAPI.getGodMode(p)) {
							s.sendMessage(Mensagens.God_Desabilitado_Outro.replace("%player%", p.getName()));
							GodModeAPI.setGodMode(p, false);
						} else {
							s.sendMessage(Mensagens.God_Ja_Desabilitado_Outro.replace("%player%", p.getName()));
						}
						return true;
					}
				}
				  
				// Se o player n�o informar 2 argumentos, ou se o segundo argumento n�o for 'on' ou 'off'
				if (GodModeAPI.getGodMode(p)) {
					s.sendMessage(Mensagens.God_Desabilitado_Outro.replace("%player%", p.getName()));
					GodModeAPI.setGodMode(p, false);
				} else {
					s.sendMessage(Mensagens.God_Habilitado_Outro.replace("%player%", p.getName()));
					GodModeAPI.setGodMode(p, true);
				}
				return true;
			}
			
			// Se o player n�o informar 2 argumentos, ou se o segundo argumento n�o for 'on' ou 'off'
			s.sendMessage(Mensagens.God_Sem_Permissao_Outro);
			return true;
		}
		
		// Se o player n�o informar nenhum argumento ou se os argumentos informados pelo player
		// n�o se encaixar em nenhuma verifica��o feita no c�digo acima este c�digo sera executado
		Player p = (Player)s;
		if (GodModeAPI.getGodMode(p)) {
			s.sendMessage(Mensagens.God_Desabilitado_Voce);
			GodModeAPI.setGodMode(p, false);
		} else {
			s.sendMessage(Mensagens.God_Habilitado_Voce);
			GodModeAPI.setGodMode(p, true);
		}
		return true;
	}
}