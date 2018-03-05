package rush.recursos.gerais;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import rush.Main;

public class BloquearTeleportPorPortal implements Listener {
	
    @EventHandler
    public static void aoTeleportaPorPortal(final PlayerPortalEvent e) {
	    if (Main.aqui.getConfig().getBoolean("Bloquear-Teleport-Por-Portal.NetherPortal")) {
	    	if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
	    		if (!(e.getPlayer().hasPermission("system.bypass.teleportarporportal"))) {
	    			e.setCancelled(true);
	    		}
	    	}
	    }
	    
	    if (Main.aqui.getConfig().getBoolean("Bloquear-Teleport-Por-Portal.EndPortal")) {
	    	if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
	    		if (!(e.getPlayer().hasPermission("system.bypass.teleportarporportal"))) {
	    			e.setCancelled(true);
	    		}
	    	}
	    }
    }
}
