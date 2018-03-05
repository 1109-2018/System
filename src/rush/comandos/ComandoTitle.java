package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import rush.Main;

public class ComandoTitle implements Listener, CommandExecutor {
	
	@SuppressWarnings({ "deprecation", "unused" })
	public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] args)
	  {
        String nome = Sender.getName();
	    for (Player todos : Bukkit.getOnlinePlayers()) {
	    if (Cmd.getName().equalsIgnoreCase("title")) {
		    {
		        if (!Sender.hasPermission("system.title"))
		        {
		          Sender.sendMessage(Main.aqui.getMensagens().getString("Sem-Permissao"));
		          return true;
		        }
		        if (args.length < 1)
		        { 
		        	Sender.sendMessage(Main.aqui.getMensagens().getString("Title-Comando-Incorreto").replaceAll("&", "�"));
		        }
		        else {
		        	String msg = "";
		            String msg2 = "";
		            String[] msg3 = null;
		            for (int i = 0; i < args.length; i++) {
		            msg = msg + args[i] + " ";
		            
		            msg2 = (msg).replaceAll(" ", "_");
		            
		            msg3 = msg2.split("<nl>");
		            
		            }
		            
		            if (!(msg2.contains("<nl>"))) {
			            todos.sendTitle(msg.replaceAll("&", "�"), "�r");
			            Sender.sendMessage(Main.aqui.getMensagens().getString("Tile-Enviado").replaceAll("&", "�"));
		            	return true;
		            }
		            else {
		             todos.sendTitle(msg3[0].replaceAll("&", "�").replaceAll("_", " "), msg3[1].replaceAll("&", "�").replaceAll("_", " "));
		            }
		            }
		        	}
		    	 }
		      }
			return false;
		  }
}
