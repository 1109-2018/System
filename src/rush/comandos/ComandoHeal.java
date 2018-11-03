package rush.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.apis.HealthAPI;
import rush.configuracoes.Mensagens;

public class ComandoHeal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			s.sendMessage(Mensagens.Console_Nao_Pode);
			return true;
		}

		// Pegando o player e verificando se ele ja esta com a vida cheia
		Player p = (Player) s;
		if (HealthAPI.getHealth(p) >= 20) {
			p.sendMessage(Mensagens.Vida_Level_Maximo);
			return true;
		}
		
		// Regerando a vida do player e informando
		p.sendMessage(Mensagens.Vida_Regenerada_Com_Sucesso);
		HealthAPI.setHealth(p, 20);
		return true;
	}

}