package rush.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import rush.Main;
import rush.configuracoes.Locations;
import rush.configuracoes.Mensagens;
import rush.configuracoes.Settings;

public class ComandoMundoVip implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			s.sendMessage(Mensagens.Console_Nao_Pode);
			return true;
		}

		// Pegando o player e o delay para se teleportar
		Player p = (Player) s;
		int delay = Settings.Delay_Para_Teleportar_Comandos;

		// Verificando se o player tem permiss�o para se teleportar para areavip
		if (!s.hasPermission("system.vip")) {

			// Verificando se o camarote para os sem vip esta habilitado e teleportando o palyer
			if (Settings.Ativar_Camarote_Para_Os_Sem_Vip) {
				s.sendMessage(Mensagens.Iniciando_Teleporte_Vip);
				new BukkitRunnable() {
					@Override
					public void run() {
						s.sendMessage("�f ");
						s.sendMessage(Mensagens.Teleportado_Com_Sucesso_Sem_Vip);
						s.sendMessage("�f ");
						p.teleport(Locations.areaNaoVip);

					}
				}.runTaskLater(Main.get(), 20 * delay);
				return true;
			}

			// Caso o camarote para os sem vips n�o esteja habilitado ent�o um erro � exibido
			s.sendMessage(Mensagens.Sem_Permissao);
			return true;
		}

		// Caso o player possua a permiss�o 'system.vip' este c�digo sera executado
		s.sendMessage(Mensagens.Teleportado_Com_Sucesso_Vip);
		p.teleport(Locations.areaVip);
		return true;

	}
}
