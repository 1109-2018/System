package rush.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import rush.configuracoes.Mensagens;

public class ComandoPotion implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {

		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			s.sendMessage(Mensagens.Console_Nao_Pode);
			return true;
		}

		// Se o sender for o console ele precisa especificar um player
		if (args.length != 3) {
			s.sendMessage(Mensagens.Potion_Comando_Incorreto);
			return true;
		}

		// Pegando o tipo do efeito e verificando se � 1 efeito valido
		String ef = args[0].toUpperCase();
		PotionEffectType effectType = getPotionEffectType(ef);
		if (effectType == null) {
			s.sendMessage(Mensagens.Potion_Efeito_Invalido.replace("%effect%", ef).replace("%lista%", getEffectTypes()));
			return true;
		}

		// Pegando a duracao e o amplicador verificando se � 1 numero valido
		int duration;
		int amplifier;
		try {
			duration = Integer.parseInt(args[1]);
			amplifier = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
			return true;
		}

		// Pegando o player o item na sua m�o e verificando se � valido
		Player p = (Player) s;
		ItemStack hand = p.getItemInHand();
		if (hand.getType() != Material.POTION) {
			s.sendMessage(Mensagens.Potion_Item_Invalido);
			return true;
		}

		// Adicionando o efeito na po��o
		PotionMeta meta = (PotionMeta) hand.getItemMeta();
		PotionEffect effect = new PotionEffect(effectType, (duration * 20), (amplifier - 1));
		meta.addCustomEffect(effect, true);
		hand.setItemMeta(meta);
		s.sendMessage(Mensagens.Potion_Editada_Sucesso);
		return true;
	}

	// M�todo para pegar o efeito da po��o
	private PotionEffectType getPotionEffectType(String effect) {
		try {
			return PotionEffectType.getByName(effect);
		} catch (Exception e) {
			return null;
		}
	}

	// M�todo para pegar a lista de efeitos
	private String getEffectTypes() {
		List<String> effects = new ArrayList<>();
		for (PotionEffectType e : PotionEffectType.values()) {
			if (e != null) effects.add(e.getName());
		}
		return effects.toString();
	}
}