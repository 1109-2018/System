package rush.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rush.configuracoes.Mensagens;
import rush.entidades.Tpa;

public class ComandoTptoggle extends Tpa implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

		// Verificando se o player digitou o n�mero de argumentos corretos
		if (args.length > 1) {
			s.sendMessage(Mensagens.Tptoggle_Comando_Incorreto);
			return true;
		}

		// Caso o n�mero de argumentos for 0 ent�o n�s ligamos e desligamos automatico
		if (args.length == 0) {
			boolean toggle = false;
			if (TOGGLE.contains(s.getName()))
				toggle = true;

			// Caso estiver ligado n�s desligamos e vice-versa
			if (toggle) {
				TOGGLE.remove(s.getName());
				s.sendMessage(Mensagens.Tptoggle_Desativado_Com_Sucesso);
			} else {
				TOGGLE.add(s.getName());
				s.sendMessage(Mensagens.Tptoggle_Ativado_Com_Sucesso);
			}
			return true;
		}

		// Caso o n�mero de argumentos for 0 ent�o significa que ele quer ligar ou desligar
		if (args.length == 1) {
			boolean toggle = false;
			if (TOGGLE.contains(s.getName()))
				toggle = true;

			// Caso o argumento for 'on' ent�o verificamos se j� esta ligado, caso contrario ligamos
			if (args[0].equalsIgnoreCase("on")) {
				if (toggle) {
					s.sendMessage(Mensagens.Tptoggle_Ja_Ativado);
				} else {
					TOGGLE.add(s.getName());
					s.sendMessage(Mensagens.Tptoggle_Ativado_Com_Sucesso);
				}
				return true;
			}

			// Caso o argumento for 'off' ent�o verificamos se j� esta desligado, caso contrario desligamos
			if (args[0].equalsIgnoreCase("off")) {
				if (toggle) {
					TOGGLE.remove(s.getName());
					s.sendMessage(Mensagens.Tptoggle_Desativado_Com_Sucesso);
				} else {
					s.sendMessage(Mensagens.Tptoggle_Ja_Desativado);
				}
				return true;
			}

			// Caso o argumento n�o for 'on' nem 'off' ent�o � dado como comando incorreto
			s.sendMessage(Mensagens.Tptoggle_Comando_Incorreto);
			return true;
		}
		return true;
	}
}