package rush.comandos;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import rush.Main;
import rush.configuracoes.Mensagens;
import rush.entidades.Warp;
import rush.entidades.Warps;

public class ComandoWarpOLD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			s.sendMessage(Mensagens.Console_Nao_Pode); 
			return true;
		}
			     
		// Verificando se o sender digitou o n�mero de argumentos correto
		if (args.length != 1) {
			s.sendMessage(Mensagens.Warp_Comando_Incorreto);
			return true;
		}
			     
		// Pegando a warp e verificando se ela existe
		if (!Warps.contains(args[0])) {
			s.sendMessage(Mensagens.Warp_Nao_Existe.replace("%warp%", args[0]));
			ComandoWarps.ListWarps(s);
			return true;
		}
		
		// Pegando o player e a localiza��o
		Player p = (Player) s;
		Warp warp = Warps.get(args[0]);
		Location location = warp.getLocation();
		
		// Verificando se o player tem permiss�o para se teleportar a warp
		if (!s.hasPermission(warp.getPermissao())) {
			s.sendMessage(warp.getSemPermissao().replace('&', '�'));
			return true;
		} 
			    	
		// Verificando se o player tem permiss�o para se teleportar sem delay
		if (!s.hasPermission("system.semdelay") || warp.delayParaVips()) {
			s.sendMessage(warp.getMensagemInicio().replace('&', '�'));
			new BukkitRunnable() {
				@Override
				public void run() {
					s.sendMessage(warp.getMensagemFinal().replace('&', '�'));
					p.teleport(location);
				}
			}.runTaskLater(Main.get(), 20 * warp.getDelay());
			return true;
		}
			    	
		// Caso o player tiver permiss�o para se teleportar sem delay ent�o
		s.sendMessage(warp.getMensagemFinal().replace('&', '�'));
		p.teleport(location);
		return true;
	}
}