package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import rush.configuracoes.Mensagens;
import rush.sistemas.comandos.VanishListener;

@SuppressWarnings("all")
public class ComandoVanish implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			
			// Verificando se o sender digitou o n�mero de argumentos correto
			if (args.length > 2 || args.length < 1) {
				s.sendMessage(Mensagens.Vanish_Comando_Incorreto);
				return true;
			}
    		  
			// Pegando o player e verificando se ele esta online
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				s.sendMessage(Mensagens.Player_Offline);
				return true;
			}
    		  				
			// Verificando se o sender informou mais de 1 argumento /vanish <player> [on/off]
			if (args.length > 1) {
			
				// Verificando se esse argumento � a palavra 'on'
				if (args[1].equalsIgnoreCase("on")) {
					
					// Verificando se o player j� esta com vanish ligado, caso contrario o vanish � ligado
					if (getVanishMode(p)) {
						s.sendMessage(Mensagens.Vanish_Ja_Habilitado_Outro.replace("%player%", p.getName()));
					} else {
						s.sendMessage(Mensagens.Vanish_Habilitado_Outro.replace("%player%", p.getName()));
						setVanishMode(p, true);
					}
					return true;
				}
	    		  
				// Verificando se esse argumento � a palavra 'off'
				if (args[1].equalsIgnoreCase("off")) {
					
					// Verificando se o player esta esta com o vanish ligado, caso contrar � exibida uma mensagem
					if (getVanishMode(p)) {
						s.sendMessage(Mensagens.Vanish_Desabilitado_Outro.replace("%player%", p.getName()));
						setVanishMode(p, false);
					} else {
						s.sendMessage(Mensagens.Vanish_Ja_Desabilitado_Outro.replace("%player%", p.getName()));
					}
					return true;
				}
			}
    		  
			// Se o player n�o informar 2 argumentos, ou se o segundo argumento n�o for 'on' ou 'off'
			if (getVanishMode(p)) {
				s.sendMessage(Mensagens.Vanish_Desabilitado_Outro.replace("%player%", p.getName()));
				setVanishMode(p, false);
			} else {
				s.sendMessage(Mensagens.Vanish_Habilitado_Outro.replace("%player%", p.getName()));
				setVanishMode(p, true);
			}
			return true;
		}
					
		// Verificando se o player informou ao menos 1 argumento /vanish [on/off/player]
		if (args.length > 0) {
			
			// Verificando se esse argumento � a palavra 'on'
			if (args[0].equalsIgnoreCase("on")) {
				Player p = (Player)s;
				if (getVanishMode(p)) {
					s.sendMessage(Mensagens.Vanish_Ja_Habilitado_Voce);
				} else {
					s.sendMessage(Mensagens.Vanish_Habilitado_Voce);
					setVanishMode(p, true);
				}
				return true;
			}
    		  
			// Verificando se esse argumento � a palavra 'off'
			if (args[0].equalsIgnoreCase("off")) {
				Player p = (Player)s;
				if (getVanishMode(p)) {
					s.sendMessage(Mensagens.Vanish_Desabilitado_Voce);
					setVanishMode(p, false);
				} else {
					s.sendMessage(Mensagens.Vanish_Ja_Desabilitado_Voce);
				}
				return true;
			}
    		
			/** Caso o primeiro argumento n�o for a palavra 'on' ou 'off' entende-se que � o nome de um player */
			
			// Verificando se o player possui permiss�o para alterar o vanish de outros players
			if (s.hasPermission("system.vanish.outros")) {
	    		
				// Verificando se o sender digitou o n�mero de argumentos correto
				if (args.length > 2) {
					s.sendMessage(Mensagens.Vanish_Comando_Incorreto);
					return true;
				}
				
				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return true;
				}
				
				// Verificando se o player informou mais de 1 argumento /vanish <player> [on/off]
				if (args.length > 1) {
					
					// Verificando se esse argumento � a palavra 'on'
					if (args[1].equalsIgnoreCase("on")) {
						
						// Verificando se o player j� esta com vanish ligado, caso contrario o vanish � ligado
						if (getVanishMode(p)) {
							s.sendMessage(Mensagens.Vanish_Ja_Habilitado_Outro.replace("%player%", p.getName()));
						} else {
							s.sendMessage(Mensagens.Vanish_Habilitado_Outro.replace("%player%", p.getName()));
							setVanishMode(p, true);
						}
						return true;
					}
					
					// Verificando se esse argumento � a palavra 'off'
					if (args[1].equalsIgnoreCase("off")) {
						
						// Verificando se o player esta esta com o vanish ligado, caso contrar � exibida uma mensagem
						if (getVanishMode(p)) {
							s.sendMessage(Mensagens.Vanish_Desabilitado_Outro.replace("%player%", p.getName()));
							setVanishMode(p, false);
						} else {
							s.sendMessage(Mensagens.Vanish_Ja_Desabilitado_Outro.replace("%player%", p.getName()));
						}
						return true;
					}
				}
				  
				// Se o player n�o informar 2 argumentos, ou se o segundo argumento n�o for 'on' ou 'off'
				if (getVanishMode(p)) {
					s.sendMessage(Mensagens.Vanish_Desabilitado_Outro.replace("%player%", p.getName()));
					setVanishMode(p, false);
				} else {
					s.sendMessage(Mensagens.Vanish_Habilitado_Outro.replace("%player%", p.getName()));
					setVanishMode(p, true);
				}
				return true;
			}
			
			// Se o player n�o informar 2 argumentos, ou se o segundo argumento n�o for 'on' ou 'off'
			s.sendMessage(Mensagens.Vanish_Sem_Permissao_Outro);
			return true;
		}
		
		// Se o player n�o informar nenhum argumento ou se os argumentos informados pelo player
		// n�o se encaixar em nenhuma verifica��o feita no c�digo acima este c�digo sera executado
		Player p = (Player)s;
		if (getVanishMode(p)) {
			s.sendMessage(Mensagens.Vanish_Desabilitado_Voce);
			setVanishMode(p, false);
		} else {
			s.sendMessage(Mensagens.Vanish_Habilitado_Voce);
			setVanishMode(p, true);
		}
		return true;
	}
	
	// M�todo para setar o vanishMode
	private void setVanishMode(Player player, boolean enable) {
		if (enable) {
			VanishListener.VANISH.add(player);
			for (Player target : Bukkit.getOnlinePlayers()) {
				if (!target.hasPermission("system.vanish.bypass")) {
					target.hidePlayer(player);
				}
			}
		} else {
			VanishListener.VANISH.remove(player);
			for (Player target : Bukkit.getOnlinePlayers()) {
				target.showPlayer(player);
			}
		}
	}

	// M�todo para pegar o vanishMode
	private boolean getVanishMode(Player player) {
		if (VanishListener.VANISH.contains(player)) return true;
		else return false;
	}
}