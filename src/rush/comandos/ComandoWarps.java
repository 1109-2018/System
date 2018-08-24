package rush.comandos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import rush.configuracoes.Mensagens;
import rush.entidades.Warp;
import rush.entidades.Warps;

public class ComandoWarps implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		ListWarps(s);
		return true;
	}
	
	// Se a lista de warps sera exibida para um player ent�o verificamos se ele tem permiss�o
	public static void ListWarps(CommandSender s) {
		
		// Pegando todas as warps
		Collection<Warp> warps = Warps.getAll();
		List<String> listWarps = new ArrayList<String>();
		
		// Criando um contador para saber o n�mero de warps e passando por todos os warps
		int cont = 0;
  	  	for (Warp warp : warps) {
  	  		String permissao = warp.getPermissao();
  	  		if(s.hasPermission(permissao)) {
  	  			listWarps.add(warp.getNome());
  	  			cont++;	
  	  		}
  	  	}
  	  	
  	  	// Se o contandor dor 0 ent�o nenhuma warp foi criada
  	  	if (cont == 0) {
  	  		s.sendMessage(Mensagens.Nenhuma_Warp_Definida);
  	  		return;
  	  	} 
  	  	
		// Exibindo a mensagem para o player
  	  	String warplist = listWarps.toString().replace(",", Mensagens.Separador_De_Listas);
  	  	s.sendMessage(Mensagens.Warps_Lista.replace("%warps%", warplist.substring(1,warplist.length() -1)).replace("%n%", String.valueOf(cont)));
	}
	
	// Se a lista de warps sera exibida para um player ent�o n�o � necessario verificarmos se ele tem permiss�o
	public static void ListWarpsForStaff(CommandSender s) {
		
		// Pegando todas as warps
		Collection<Warp> warps = Warps.getAll();
		List<String> listWarps = new ArrayList<String>();
		
		// Criando um contador para saber o n�mero de warps e passando por todos os warps
		int cont = 0;
  	  	for (Warp warp : warps) {
  	  		listWarps.add(warp.getNome());
  	  		cont++;	
  	  	}
  	  	
  	  	// Se o contandor dor 0 ent�o nenhuma warp foi criada
  	  	if (cont == 0) {
  	  		s.sendMessage(Mensagens.Nenhuma_Warp_Definida);
  	  		return;
  	  	} 
  	  	
		// Exibindo a mensagem para o player
  	  	String warplist = listWarps.toString().replace(",", Mensagens.Separador_De_Listas);
  	  	s.sendMessage(Mensagens.Warps_Lista.replace("%warps%", warplist.substring(1,warplist.length() -1)).replace("%n%", String.valueOf(cont)));
	}
}