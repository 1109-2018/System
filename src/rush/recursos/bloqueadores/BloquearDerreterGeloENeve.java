package rush.recursos.bloqueadores;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BloquearDerreterGeloENeve implements Listener {

	@EventHandler
	public void aoDerreterBlock(BlockFadeEvent e) {
		if (e.getBlock().getType() == Material.ICE || e.getBlock().getType() == Material.SNOW) {
			e.setCancelled(true);
		}
	}
}