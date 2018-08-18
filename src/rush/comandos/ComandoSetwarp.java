package rush.comandos;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import rush.configuracoes.Mensagens;
import rush.utils.DataManager;

public class ComandoSetwarp implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("setwarp")) {
			
			// Verificando se o sender � um player
			if (!(s instanceof Player)) {
				s.sendMessage(Mensagens.Console_Nao_Pode); 
				return true;
			}	 
				
			// Verificando se o player digitou o n�mero de argumentos corretos
			if (args.length != 1) {
				s.sendMessage(Mensagens.SetWarp_Comando_Incorreto);
				return true;
			}
				     
			// Pegando o argumento, o file e a config
			String warp = args[0];
			File file = DataManager.getFile(warp, "warps");
			FileConfiguration config = DataManager.getConfiguration(file);
			
			// Verificando se a j� warp existe
			if (file.exists()) {
				s.sendMessage(Mensagens.Warp_Ja_Existe.replace("%warp%", warp));
				return true;
			}
			
			Player p = (Player) s;
			DataManager.createFile(file);
			Location location = p.getLocation();
			String locationSerialized = serializeLocation(location);
			config.set("Localizacao" , locationSerialized);
			config.set("Permissao" , "system.warp." + warp.toLowerCase());
			config.set("MensagemSemPermissao", "&cVoc� n�o tem permiss�o para se teleportar para a warp " + warp + "!");
			config.set("Delay", 5);
			config.set("DelayParaVips", false);
			config.set("MensagemInicio" , "&aVoc� sera teleportado para a warp " + warp + " em 5 segundos!");
			config.set("MensagemFinal" , "&aTeleportado com sucesso para a warp " + warp + "!");
			config.set("EnviarTitle" , true);
			config.set("Title" , "&aVOC� FOI TELEPORTADO");
			config.set("SubTitle" , "&ePARA A WARP " + warp.toUpperCase());
			try {
				config.save(file);
				s.sendMessage(Mensagens.Warp_Definida.replace("%warp%", warp));
			} catch (IOException e) {
				Bukkit.getConsoleSender().sendMessage(Mensagens.Falha_Ao_Salvar.replace("%arquivo%", file.getName()));
			}
			return true;
		}
		return false;
	}

    private String serializeLocation(Location l) {
    	return l.getWorld().getName()+","+l.getX()+","+l.getY()+","+l.getZ()+","+l.getYaw()+","+l.getPitch();
    }
}
