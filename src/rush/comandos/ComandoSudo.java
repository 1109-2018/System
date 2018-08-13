package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.configuracoes.Mensagens;

public class ComandoSudo implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sudo")) {

			// Verificando se o player informou menos de 2 argumentos
			if (args.length < 2) {
				s.sendMessage(Mensagens.Sudo_Comando_Incorreto);
				return false;
			}

			// Pegando o player e verificando se ele esta online
			Player p = Bukkit.getPlayer(args[0]);
			if (p == null) {
				s.sendMessage(Mensagens.Player_Offline);
				return false;
			}
			
			// Pegando o comando e executando para o player especificado
			String comando = "";
			for (int i = 1; i < args.length; i++) {comando += args[i] + " ";}
			p.chat(comando);
			s.sendMessage(Mensagens.Sudo_Executado_Com_Sucesso.replace("%player%", args[0]).replace("%comando%", comando));
		}
		return false;
	}
}