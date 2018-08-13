package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.configuracoes.Mensagens;

public class ComandoSpeed implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("speed")) {

			// Verificando se o sender � um player
			if (!(s instanceof Player)) {

				// Verificando se o sender digitou o n�mero de argumentos correto
				if (args.length != 2) {
					s.sendMessage(Mensagens.Speed_Comando_Incorreto);
					return false;
				}
				
				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[1]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return false;
				}

				// Verificando se o n�mero � um n�mero valido
				float speed;
				try {
					speed = Float.parseFloat(args[0]);
				} catch (NumberFormatException e) {
					s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
					return false;
				}

				// Verificando se a velocidade � valida (necessario bukkit)
				if (speed > 1.0f || speed < -1.0f) {
					s.sendMessage(Mensagens.Speed_Valor_Invalido);
					return false;
				}
				
				// Setando o speed no player e informando
				p.setFlySpeed(speed);
				p.setWalkSpeed(speed);
				p.sendMessage(Mensagens.Speed_Alterado_Outro.replace("%speed%", args[0]).replace("%player%", args[1]));
			
			}

			// Verificando se o player digitou o n�mero de argumentos correto
			if (args.length < 1 || args.length > 2) {
				s.sendMessage(Mensagens.Speed_Comando_Incorreto);
				return false;
			}

			// Se o n�mero de argumentos � 0 ent�o a velocidade do sender � alterada
			if (args.length == 1) {

				// Pegando o player e verificando se o n�mero � um n�mero valido
				Player p = (Player) s;
				float speed;
				try {
					speed = Float.parseFloat(args[0]);
				} catch (NumberFormatException e) {
					s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
					return false;
				}

				// Verificando se a velocidade � valida (necessario bukkit)
				if (speed > 1.0f || speed < -1.0f) {
					s.sendMessage(Mensagens.Speed_Valor_Invalido);
					return false;
				}
				
				// Setando o speed no player e informando
				p.setFlySpeed(speed);
				p.setWalkSpeed(speed);
				p.sendMessage(Mensagens.Speed_Alterado_Voce.replace("%speed%", args[0]));
			}

			// Se o n�mero de argumentos � 0 ent�o a velocidade do sender � alterada
			if (args.length == 2) {

				// Verificando se o sender tem permisss�o
				if (!s.hasPermission("system.speed.outros")) {
					s.sendMessage(Mensagens.Speed_Outro_Sem_Permissao);
					return false;
				}
				
				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[1]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return false;
				}

				// Verificando se o n�mero � um n�mero valido
				float speed;
				try {
					speed = Float.parseFloat(args[0]);
				} catch (NumberFormatException e) {
					s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
					return false;
				}

				// Verificando se a velocidade � valida (necessario bukkit)
				if (speed > 1.0f || speed < -1.0f) {
					s.sendMessage(Mensagens.Speed_Valor_Invalido);
					return false;
				}
				
				// Setando o speed no player e informando
				p.setFlySpeed(speed);
				p.setWalkSpeed(speed);
				p.sendMessage(Mensagens.Speed_Alterado_Outro.replace("%speed%", args[0]).replace("%player%", args[1]));
			}
		}
		return false;
	}
}