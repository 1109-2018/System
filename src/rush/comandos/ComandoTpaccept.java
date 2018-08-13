package rush.comandos;

import java.util.LinkedHashSet;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import rush.Main;
import rush.configuracoes.Mensagens;
import rush.configuracoes.Settings;
import rush.entidades.Tpa;

public class ComandoTpaccept extends Tpa implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpaccept")) {

			// Verificando se o player digitou o n�mero de argumentos corretos
			if (args.length > 1) {
				s.sendMessage(Mensagens.Tpaccept_Comando_Incorreto);
				return false;
			}
			
			// Verificando se o player recebeu algum tpa para poder aceita-lo
			if (!TPs_recebidos.containsKey(s.getName())) {
				s.sendMessage(Mensagens.Tpa_Pendente_Nao_Possui);
				return false;
			}
			
			// Pegando a lista de tpas que o player recebeu
			LinkedHashSet<String> tpas = TPs_recebidos.get(s.getName());
		
			// Verificando se o player possui tpas para aceita-los
			if (tpas.size() == 0) {
				s.sendMessage(Mensagens.Tpa_Pendente_Nao_Possui);
				return false;
			}
			
			// Caso o player n�o informe argumentos ent�o significa que ele quer aceitar o ultimo TPA recebido
			if (args.length == 0) {
				
				// Pegando o ultimo player que enviou TPA
				String ultimoTpa = "";
				for (String str : tpas) { ultimoTpa = str; }
				
				// Removendo o TPA da HashMap
				TPs_enviados.get(ultimoTpa).remove(s.getName());
				TPs_recebidos.get(s.getName()).remove(ultimoTpa);
				
				// Verificando se o ultimo player que enviou TPA esta online
				Player p = Bukkit.getPlayer(ultimoTpa);
				if (p == null) {
					s.sendMessage(Mensagens.Tpaccept_Player_Offline.replace("%player%", ultimoTpa));
					return false;
				}
				
				// Pegando o player sender (alvo) e informando que o TPA foi aceito
				Player target = (Player) s;
				s.sendMessage(Mensagens.Tpaccept_Solicitacao_Aceita_Sucesso.replace("%player%", ultimoTpa));
				
				// Verificando se o player possui permiss�o para se teleportar sem delay
				if (!p.hasPermission("system.semdelay")) {
					p.sendMessage(Mensagens.Tpaccept_Iniciando_Teleporte.replace("%player%", s.getName()));
					new BukkitRunnable() {
						@Override
						public void run() {
							// Teleportando e informando
							p.teleport(target);
							p.sendMessage(Mensagens.Tpaccept_Teleportado_Com_sucesso.replace("%player%", s.getName()));
						}
					}.runTaskLater(Main.get(), 20 * Settings.Delay_Para_Teleportar_Comandos);
					return false;
				}
				
				// Caso o player possui a permiss�o para se teleportar sem delay o c�digo acima � ignorado
				p.teleport(target);
				p.sendMessage(Mensagens.Tpaccept_Teleportado_Com_sucesso.replace("%player%", s.getName()));
				return false;
			}
			
			// Caso o player informe um argumento ent�o significa que ele quer aceitar um TPA especifico
			if (args.length == 1) {
				
				// Verificando se o sender e o player target s�o a mesma pessoa
				if (s.getName().equals(args[0])) {
					s.sendMessage(Mensagens.Tpaccept_Erro_Voce_Mesmo);
					return false;
				}
					
				// Verificando se o player target informado realmente enviou TPA para o sender
				if (!tpas.contains(args[0])) {
					s.sendMessage(Mensagens.Tpa_Pendente_Player_Nao_Possui.replace("%player%", args[0]));
					return false;
				}
				
				// Removendo o TPA da HashMap
				TPs_enviados.get(args[0]).remove(s.getName());
				TPs_recebidos.get(s.getName()).remove(args[0]);
				
				// Pegando o player que enviou TPA e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					s.sendMessage(Mensagens.Tpaccept_Player_Offline.replace("%player%", args[0]));
					return false;
				}
				
				// Pegando o player sender (alvo) e informando que o TPA foi aceito
				Player target = (Player) s;
				s.sendMessage(Mensagens.Tpaccept_Solicitacao_Aceita_Sucesso.replace("%player%", args[0]));
				
				// Verificando se o player possui permiss�o para se teleportar sem delay
				if (!p.hasPermission("system.semdelay")) {
					p.sendMessage(Mensagens.Tpaccept_Iniciando_Teleporte.replace("%player%", s.getName()));
					new BukkitRunnable() {
						@Override
						public void run() {
							// Teleportando e informando
							p.teleport(target);
							p.sendMessage(Mensagens.Tpaccept_Teleportado_Com_sucesso.replace("%player%", s.getName()));	
						}
					}.runTaskLater(Main.get(), 20 * Settings.Delay_Para_Teleportar_Comandos);
					return false;
				}
				
				// Caso o player possui a permiss�o para se teleportar sem delay o c�digo acima � ignorado
				p.teleport(target);
				p.sendMessage(Mensagens.Tpaccept_Teleportado_Com_sucesso.replace("%player%", s.getName()));
				return false;
			}
		}
		return false;
	}
}