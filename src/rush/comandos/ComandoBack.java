package rush.comandos;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rush.configuracoes.Mensagens;
import rush.sistemas.comandos.BackListener;

public class ComandoBack implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("back")) {

			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode);
				return false;
			}

			// Obtendo o player e a lista de pessoas que teleportaram
			Player p = (Player) s;
			HashMap<String, Location> lista = BackListener.backList;

			// Verificando se o player possui um lugar para se voltar
			if (!lista.containsKey(p.getName())) {
				s.sendMessage(Mensagens.Nao_Possui_Back);
				return false;
			}

			// Obtendo a localiza��o para se teleportar e teleportando o player
			p.teleport(lista.get(p.getName()));
			s.sendMessage(Mensagens.Back_Teleportado_Sucesso);
		}
		return false;
	}
}