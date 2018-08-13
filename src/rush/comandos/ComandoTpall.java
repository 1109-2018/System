package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.configuracoes.Mensagens;

public class ComandoTpall implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpall")) {
			
			// Verificando se o player digitou o n�mero de argumentos corretos
			if (args.length != 1 && args.length != 4) {
				s.sendMessage(Mensagens.Tpall_Comando_Incorreto);
				return false;
			}
			
			// Se os argumentos forem 1 ent�o o sender quer teleportar todos at� um player
			if (args.length == 1) {
				
				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return false;
				}

				//Teleportando todos os players e informando
				for (Player target : Bukkit.getOnlinePlayers()) {
					target.teleport(p);
					target.sendMessage(Mensagens.Tphere_Puxado_Com_Sucesso.replace("%player%", s.getName()));
				}
				s.sendMessage(Mensagens.Tpall_Puxou_Com_Sucesso_Player);
				return false;
			}
			
			
			// Se os argumentos foram 4 ent�o o sender quer teleportar todos at� uma cordenada
			if (args.length == 4) {

				// Verificando se o mundo � 1 mundo valido
				World w = Bukkit.getWorld(args[0]);
				if (w == null) {
					s.sendMessage(Mensagens.Mundo_Nao_Existe.replace("%mundo%", args[1]));
					return false;
				}

				// Verificando se os n�meros digitados s�o validos
				double x, y, z;
				try {
					x = Double.parseDouble(args[1]);
					y = Double.parseDouble(args[2]);
					z = Double.parseDouble(args[3]);
				} catch (NumberFormatException e) {
					s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
					return false;
				}

				//Teleportando todos os players e informando
				Location l = new Location(w, x, y, z);
				for (Player target : Bukkit.getOnlinePlayers()) {
					target.teleport(l);
					target.sendMessage(Mensagens.Tphere_Puxado_Com_Sucesso.replace("%player%", s.getName()));
				}
				s.sendMessage(Mensagens.Tpall_Puxou_Com_Sucesso_Player);
				return false;
			}
		}
		return false;
	}
}