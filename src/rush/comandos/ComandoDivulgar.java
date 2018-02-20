package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import rush.Main;

public class ComandoDivulgar implements Listener, CommandExecutor {
		
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] args)
	  {
        String nome = Sender.getName();
	    for (Player todos : Bukkit.getOnlinePlayers()) {
	    if (Cmd.getName().equalsIgnoreCase("divulgar"))
	    {
	        if (!Sender.hasPermission("system.divulgar"))
	        {
	        	Sender.sendMessage(Main.aqui.getMensagens().getString("Sem-Permissao"));
	          return true;
	        }
	        	        
	        if (args.length < 2 || args.length > 2)
	        { 
	        	Sender.sendMessage(Main.aqui.getMensagens().getString("Divulgar-Comando-Incorreto").replaceAll("&", "�"));
		          return true;
	        }
	        
	        if (args[0].equalsIgnoreCase("live") || args[0].equalsIgnoreCase("video") || args[0].equalsIgnoreCase("outro")) {
	        
	        if 
	        (args[1].contains("http") || 
	        (args[1].contains("www")  || 
	        (args[1].contains("com")) || 
	        (args[1].contains("br"))  || 
	        (args[1].contains("net")) ||
	        (args[1].contains("org")) ||
	        (args[1].contains("ly"))  ||
	        (args[1].contains("sc"))  ||
	        (args[1].contains("me"))  || 
	        (args[1].contains("tk")))) {
	        	
	        if (args[0].equalsIgnoreCase("live")) {
	              todos.sendTitle(
	            Main.aqui.getMensagens().getString("Divulgando-Title").replace("&", "�").replaceAll("%link%", args[1]).replace("_", " ").replaceAll("%player%", nome),
	            Main.aqui.getMensagens().getString("Divulgando-SubTitle").replace("&", "�").replaceAll("%link%", args[1]).replace("_", " ").replaceAll("%player%", nome));
	            Bukkit.broadcastMessage("");
		        Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("Divulgando-Live").replaceAll("&", "�").replaceAll("%player%", nome));
	            Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("Link").replaceAll("&", "�").replaceAll("%link%", args[1]).replaceFirst("live", ""));
	            Bukkit.broadcastMessage("");
	       		return false;
	            }
	        
	        if (args[0].equalsIgnoreCase("video")) {
	            todos.sendTitle(
	            Main.aqui.getMensagens().getString("Divulgando-Title").replace("&", "�").replaceAll("%link%", args[1]).replace("_", " ").replaceAll("%player%", nome),
	            Main.aqui.getMensagens().getString("Divulgando-SubTitle").replace("&", "�").replaceAll("%link%", args[1]).replace("_", " ").replaceAll("%player%", nome));
	            Bukkit.broadcastMessage("");
	            Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("Divulgando-Video").replaceAll("&", "�").replaceAll("%player%", nome));
	            Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("Link").replaceAll("&", "�").replaceAll("%link%", args[1]).replaceFirst("video", ""));
	            Bukkit.broadcastMessage("");
       			return false;
	          }
	        
	        if (args[0].equalsIgnoreCase("outro")) {
	            todos.sendTitle(
	            Main.aqui.getMensagens().getString("Divulgando-Title").replace("&", "�").replaceAll("%link%", args[1]).replace("_", " ").replaceAll("%player%", nome),
	            Main.aqui.getMensagens().getString("Divulgando-SubTitle").replace("&", "�").replaceAll("%link%", args[1]).replace("_", " ").replaceAll("%player%", nome));
	            Bukkit.broadcastMessage("");
	            Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("Divulgando-Outro").replaceAll("&", "�").replaceAll("%player%", nome));
	            Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("Link").replaceAll("&", "�").replaceAll("%link%", args[1]).replaceFirst("outro", ""));
	            Bukkit.broadcastMessage("");
       			return false;
	        		}
	       			}
	       			Sender.sendMessage(Main.aqui.getMensagens().getString("Link-Invalido").replaceAll("&", "�").replaceAll("%link%", args[1]));
	       			return false;
	          }
        	  Sender.sendMessage(Main.aqui.getMensagens().getString("Divulgar-Comando-Incorreto").replaceAll("&", "�"));
	          return false;
	    	}
	      }
		return false;
	  }
}
