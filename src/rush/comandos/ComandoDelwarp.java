package rush.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rush.configuracoes.Mensagens;
import rush.entidades.Warps;

public class ComandoDelwarp implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

		// Verificando se o player digitou o n�mero de argumentos corretos
		if (args.length != 1) {
			s.sendMessage(Mensagens.DelWarp_Comando_Incorreto);
			return true;
		}

		// Verificando se o file(warp) existe
		String warp = args[0].toLowerCase();
		if (!Warps.contains(warp)) {
			s.sendMessage(Mensagens.Warp_Nao_Existe.replace("%warp%", warp));
			ComandoWarps.ListWarpsForStaff(s);
			return true;
		}

		// Deletando a warp
		Warps.delete(warp);
		s.sendMessage(Mensagens.Warp_Deletada.replace("%warp%", warp));
		return true;
	}

}
