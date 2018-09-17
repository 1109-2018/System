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

@SuppressWarnings("all")
public class ComandoTpa extends Tpa implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			s.sendMessage(Mensagens.Console_Nao_Pode); 
			return true;
        }
		
		// Verificando se o player digitou o n�mero de argumentos corretos
		if (args.length != 1) {
			s.sendMessage(Mensagens.Tpa_Comando_Incorreto);
			return true;
		}
		
		// Definindo o sender que esta enviando o tpa e o target que esta recebendo
		final String sender = s.getName();
		
		// Verificando se o sender e o player alvo s�o os mesmos
		if (s.getName().equals(args[0])) {
			s.sendMessage(Mensagens.Tp_Erro_Voce_Mesmo);
			return true;
		}
		
        // Pegando o player alvo e verificando se ele esta online
        Player pTarget = Bukkit.getPlayer(args[0]);
		if (pTarget == null) {
			s.sendMessage(Mensagens.Player_Offline);
			return true;
		}
		final String target = pTarget.getName();
		
		// Verificando se o player j� enviou algum TPA
		if (TP_ENVIADOS.containsKey(sender)) {
			
			// Verificando se o player j� possui um TPA pendente com o alvo
			if (TP_ENVIADOS.get(sender).contains(target)) {
				s.sendMessage(Mensagens.Tpa_Ja_Possui_Solicitacao.replace("%player%", target));
				return true;
			}
			
		// Caso ele n�o tenha enviado nenhum TPA ent�o ele adicionado na lista
		} else {
			TP_ENVIADOS.put(sender, new LinkedHashSet<>());
		}
		
		// Verificando se o player precisa esperar o cooldown
		if (COOLDOWN.containsKey(sender)) {
			if (System.currentTimeMillis() < COOLDOWN.get(sender)) {
				s.sendMessage(Mensagens.Tpa_Aguarde_Cooldown);
				return true;
			}
		}
		
		// Verificando se o alvo esta com o TPA desativado
		if (TOGGLE.contains(target)) {
			s.sendMessage(Mensagens.Tpa_Desligado_Tptoggle.replace("%player%", target));
			return true;
		}
		
		// Adicionando o TPA na HashMap e informando o sender e o target
		if (!TP_RECEBIDOS.containsKey(target)) TP_RECEBIDOS.put(target, new LinkedHashSet<>());
		TP_RECEBIDOS.get(target).add(sender);
		TP_ENVIADOS.get(sender).add(target);
		
		// Adicionando o player na lista de cooldown
		COOLDOWN.put(sender, (System.currentTimeMillis() + (1000 * Settings.Tempo_Para_Poder_Enviar_Outra_Solicitacao_Tpa)));
		s.sendMessage(Mensagens.Tpa_Solicitacao_Enviada_Sucesso.replace("%player%", target));
		pTarget.sendMessage(Mensagens.Tpa_Solicitacao_Recebida.replace("%player%", sender));
		
		// Iniciando a runnable que expira o teleporte depois de tantos segundos
		new BukkitRunnable() {
			@Override
			public void run() {
				// Caso o TPA ainda n�o tenha sido aceito ent�o ele � expirado
				if (TP_ENVIADOS.get(sender).contains(target)) {
					// Removendo o TPA da HashMap
					TP_ENVIADOS.get(sender).remove(target);
					TP_RECEBIDOS.get(target).remove(sender);
					// Verificando se nenhum dos players deslogou do servidor e informando
					if (s != null && pTarget != null) {
						s.sendMessage(Mensagens.Tpa_Solicitcao_Expirada_Player.replace("%player%", target));
						pTarget.sendMessage(Mensagens.Tpa_Solicitcao_Expirada_Alvo.replace("%player%", sender));
					}
				}
			}
		}.runTaskLater(Main.get(), 20 * Settings.Tempo_Para_Expirar_Solicitacao_Tpa);	
		return true;
	}
}