package rush.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
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

		// Pegando o player
		Player p = (Player) s;

		// Verificando se o player tem permiss�o para se teleportar para areavip
		if (!s.hasPermission("system.vip")) {

			// Verificando se o camarote para os sem vip esta habilitado e teleportando o palyer
			if (Settings.Ativar_Camarote_Para_Os_Sem_Vip) {
				s.sendMessage(Mensagens.Iniciando_Teleporte_Vip);
				new BukkitRunnable() {
					@Override
					public void run() {
						p.teleport(Locations.areaNaoVip, TeleportCause.COMMAND);
						s.sendMessage("�f ");
						s.sendMessage(Mensagens.Teleportado_Com_Sucesso_Sem_Vip);
						s.sendMessage("�f ");

					}
				}.runTaskLater(Main.get(), 20L * Settings.Delay_Para_Teleportar_Comandos);
				return true;
			}

			// Caso o camarote para os sem vips n�o esteja habilitado ent�o um erro � exibido
			s.sendMessage(Mensagens.Sem_Permissao);
			return true;
		}

		// Caso o player possua a permiss�o 'system.vip' este c�digo sera executado
		p.teleport(Locations.areaVip, TeleportCause.COMMAND);
		s.sendMessage(Mensagens.Teleportado_Com_Sucesso_Vip);
		return true;

	}
}
