package rush.comandos;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import rush.configuracoes.Mensagens;

public class ComandoClear implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		if (cmd.getName().equalsIgnoreCase("clear")) {

			// Verificando se o sender � um player
			if (!(s instanceof Player)) {

				// Se o sender for o console ele precisa especificar um player
				if (args.length != 1) {
					s.sendMessage(Mensagens.Clear_Comando_Incorreto);
					return false;
				}

				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return false;
				}

				// Verificando se o inventario do player ja esta vazio
				if (inventoryIsEmpty(p)) {
					s.sendMessage(Mensagens.Clear_Inventario_Vazio_Outro.replace("%player%", p.getName()));
					return false;
				}

				// Limpando o inventario do player
				clearInventory(p);
				s.sendMessage(Mensagens.Clear_Inventario_Limpado_Outro.replace("%player%", p.getName()));
				return false;
			}

			// Verificando se o player informou mais de 1 argumento
			if (args.length > 1) {
				s.sendMessage(Mensagens.Clear_Comando_Incorreto);
				return false;
			}

			// Caso o player tenha digita 1 argumento...
			if (args.length == 1) {

				// Verificando se ele tem permiss�o para limpar outros invent�rios
				if (!s.hasPermission("system.clear.outros")) {
					s.sendMessage(Mensagens.Clear_Outro_Sem_Permissao);
					return false;
				}

				// Pegando o player e verificando se ele esta online
				Player p = Bukkit.getPlayer(args[0]);
				if (p == null) {
					s.sendMessage(Mensagens.Player_Offline);
					return false;
				}

				// Verificando se o inventario do player ja esta vazio
				if (inventoryIsEmpty(p)) {
					s.sendMessage(Mensagens.Clear_Inventario_Vazio_Outro.replace("%player%", p.getName()));
					return false;
				}

				// Limpando o inventario do player
				clearInventory(p);
				s.sendMessage(
						Mensagens.Clear_Inventario_Limpado_Outro.replace('&', '�').replace("%player%", p.getName()));
				return false;
			}

			// Caso o player n�o digite nenhum argumento ent�o sabemos que � para limpar o
			// pr�pio inventario
			// Verificando se o inventario do player ja esta vazio
			Player p = (Player) s;
			if (inventoryIsEmpty(p)) {
				s.sendMessage(Mensagens.Clear_Inventario_Vazio_Voce);
				return false;
			}

			// Limpando o inventario do player
			clearInventory(p);
			s.sendMessage(Mensagens.Clear_Inventario_Limpado_Voce);
		}
		return false;
	}

	// M�todo para verificar se o inventario j� esta vazio
	private boolean inventoryIsEmpty(Player p) {
		PlayerInventory inv = p.getInventory();

		// Verificando se a itens do inventario
		for (ItemStack i : inv.getContents()) {
			if (i != null && i.getType() != Material.AIR)
				return false;
		}

		// Verificando se a itens na armadura
		for (ItemStack i : inv.getArmorContents()) {
			if (i != null && i.getType() != Material.AIR)
				return false;
		}

		// Verificando se a um item no cursor
		if (p.getItemOnCursor() != null && p.getItemOnCursor().getType() != Material.AIR)
			return false;

		return true;
	}

	// M�todo para limpar o inventario do player
	/*
	 * N�s poderiamos simplesmente usar inv.clear(), mas isto n�o limpa a armadura e
	 * tamb�m n�o limpa o item no cursor do player caso ele estiver segundo no inventario
	 */
	private void clearInventory(Player p) {
		PlayerInventory inv = p.getInventory();
		p.setItemOnCursor(null);
		inv.clear();
		inv.setHelmet(null);
		inv.setChestplate(null);
		inv.setLeggings(null);
		inv.setBoots(null);
	}
}