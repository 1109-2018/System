package rush.addons;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.nossr50.database.DatabaseManagerFactory;
import com.gmail.nossr50.datatypes.database.PlayerStat;
import com.gmail.nossr50.datatypes.player.PlayerProfile;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.gmail.nossr50.util.player.UserManager;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import rush.Main;
import rush.utils.ActionBar;

public class McMMO implements Listener {
	
	public static BukkitTask TTask;
	private static String playerTopOne;
	
	//
	// Verifica o TOP 1 do mcMMO e adiciona uma tag antes do nick {mctop}
	//
	   
	@EventHandler
	private void onChat(ChatMessageEvent event) {
	   if (playerTopOne != null && playerTopOne.equalsIgnoreCase(event.getSender().getName()) && event.getTags().contains("mctop")) {
	      event.setTagValue("mctop", Main.aqui.getConfig().getString("mcTopTag.Tag"));
	     }
	}

	public static void checkMCTop() {
    TTask = (new BukkitRunnable() {
	      public void run() {
	         List<PlayerStat> tops = DatabaseManagerFactory.getDatabaseManager().readLeaderboard((SkillType)null, 1, 1);
	         if (!tops.isEmpty()) {
	            playerTopOne = ((PlayerStat)tops.get(0)).name;
	         }
	      }
	   }).runTaskTimerAsynchronously((Plugin) Main.aqui, 60L, (long)Main.aqui.getConfig().getInt("mcTopTag.Tempo-De-Checagem") * 20L);
	}

	//
	// Envia um broadcast toda vez que alguem upa level 100 ou 200 ou 300 etc...
	//
	   
	@EventHandler
	public void onPlayerUp(McMMOPlayerLevelUpEvent e) {
      Player p = e.getPlayer();
      if (e.getSkillLevel() % 100 == 0){
        Bukkit.broadcastMessage(Main.aqui.getMensagens().getString("mcMMO-Upou-100-Niveis")
    	.replace("%skill%", e.getSkill().name())
        .replace("ACROBATICS", "Acrobacia")
        .replace("ALCHEMY", "Alquimia")
        .replace("ARCHERY", "Arqueiro")
        .replace("AXES", "Machados")
        .replace("EXCAVATION", "Escava��o")
        .replace("HERBALISM", "Herbalismo")
        .replace("MINING", "Minera��o")
        .replace("REPAIR", "Repara��o")
        .replace("SWORDS", "Espadas")
        .replace("TAMING", "Domador")
        .replace("UNARMED", "Desarmado")
        .replace("WOODCUTTING", "Lenhador")
        .replace("%player%", p.getName())
        .replace("%level%", String.valueOf(e.getSkillLevel()))
        .replaceAll("&", "�"));
    p.getWorld().strikeLightningEffect(p.getLocation());
    p.getWorld().strikeLightningEffect(p.getLocation());
    }
  }
	
	//
	// Envia mensagens na ActionBar informando o XP que ganho, o n�vel atual etc...
	//
	  
	@SuppressWarnings("deprecation")
	@EventHandler
	public static void aoGanharXp(McMMOPlayerXpGainEvent e) {
	   PlayerProfile pro = UserManager.getPlayer(e.getPlayer()).getProfile();
	    
	   Player p = e.getPlayer();
	    
	   String skill = e.getSkill().getName()
			    .replace("Acrobatics", "Acrobacia")
		        .replace("Alchemy", "Alquimia")
		        .replace("Archery", "Arqueiro")
		        .replace("Axes", "Machados")
		        .replace("Excavation", "Escava��o")
		        .replace("Herbalism", "Herbalismo")
		        .replace("Mining", "Minera��o")
		        .replace("Repair", "Repara��o")
		        .replace("Swords", "Espadas")
		        .replace("Taming", "Domador")
		        .replace("Unarmed", "Desarmado")
		        .replace("Woodcutting", "Lenhador");
	   int lvl = pro.getSkillLevel(e.getSkill());
	   int xp = pro.getSkillXpLevel(e.getSkill());
	   int dxp = pro.getXpToLevel(e.getSkill());
	   int gn = Math.round(e.getXpGained());
	    
	   ActionBar.sendActionbar(p, "�a" + skill + ": " + lvl + " (" + xp + "/" + dxp + ") +" + gn + "XP");
	  }
}