package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import rush.configuracoes.Mensagens;

public class ComandoLixo implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lixo")) {
			
			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode); 
				return false;
            }
			
			// Pegando o player, criando o inventario e abrindo o mesmo
			Player p = (Player)s;
			Inventory inv = Bukkit.getServer().createInventory(p, 36, Mensagens.Titulo_Da_Lixeira);
            p.openInventory(inv);
		}
		return false;
	}
}