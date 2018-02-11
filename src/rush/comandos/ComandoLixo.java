package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import rush.Main;

public class ComandoLixo implements Listener, CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args) {
	      Inventory inv;
	      if (cmd.getName().equalsIgnoreCase("lixo")) 
                {
	          if ((sender instanceof Player))
	            {
	        	  Player p = (Player) sender;
                {
                    inv = Bukkit.getServer().createInventory(p, 36, Main.aqui.getMensagens().getString("Titulo-Da-Lixeira").replaceAll("&", "�"));
	            p.openInventory(inv);
                }
                } else {
   	             sender.sendMessage(Main.aqui.getMensagens().getString("Console-Nao-Pode").replaceAll("&", "�")); 
                }
                }
	      return false;
	}
}