 package rush.sistemas.gerais;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import rush.Main;
import rush.configuracoes.Settings;

@SuppressWarnings("all")
public class ScoreBoardOLD implements Listener {

	private static Scoreboard scoreboard;
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void aoEntrarScoreboard(PlayerJoinEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
		    	e.getPlayer().setScoreboard(scoreboard);
			}
		}.runTaskLater(Main.get(), 10);
	}
	
	public static void loadScoreBoard() {
		
		String titulo = Settings.ScoreBoard_Titulo;
	    List<String> linhas = Settings.ScoreBoard_Linhas;
		
	    scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
	    Objective o = scoreboard.registerNewObjective("system.score", "dummy");
        o.setDisplayName(titulo.length() > 32 ? titulo.substring(0, 32) : titulo);
	    o.setDisplaySlot(DisplaySlot.SIDEBAR);
	    
	    for (int i = 0; i < linhas.size(); i++) {
	    	String scr = linhas.get(i).length() > 16 ? linhas.get(i).substring(0, 16) : linhas.get(i);
			Score score = o.getScore(Bukkit.getOfflinePlayer(scr.replace('&', '�')));
	    	score.setScore(linhas.size() - i);
	    }
	}

}