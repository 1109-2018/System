package rush.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import rush.apis.ItemAPI;
import rush.configuracoes.Mensagens;

public class ComandoEditaritemOLD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
			
		// Verificando se o sender � um player
		if (!(s instanceof Player)) {
			s.sendMessage(Mensagens.Console_Nao_Pode);
			return true;
		}
			
		// Verificando se o player digitou o n�mero minimo de argumentos
		if (args.length < 1) {
			s.sendMessage(Mensagens.Editar_Item_Comando_Incorreto);
			return true;
		}
			
		// Pegando o player e o item que esta na sua m�o
		Player p = (Player)s;
		ItemStack item = p.getItemInHand();
			
		// Verificando se o item � valido
		if (item == null || item.getType() == Material.AIR) {
			s.sendMessage(Mensagens.Editar_Item_Invalido);
			return true;
		}
			
		// Pegando a ItemMeta do item para podermos edita-la
		ItemMeta meta = item.getItemMeta();
		
		// Verificando se o player quer renomear o item
		if (args[0].equalsIgnoreCase("renomear")) {
			String nome = "";
			for (int i = 1; i < args.length; i++) {nome += args[i] + " ";}
			meta.setDisplayName(nome.replace('&', '�').trim());
			item.setItemMeta(meta);
			s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
			return true;
		}
			
		// Verificando se o player quer adicionar flags no item
		if (args[0].equalsIgnoreCase("addflags")) {
			s.sendMessage(Mensagens.Erro_Versao_Nao_Suportada);
			return true;
		}
			
		// Verificando se o player quer remover flags no item
		if (args[0].equalsIgnoreCase("removeflags")) {
			s.sendMessage(Mensagens.Erro_Versao_Nao_Suportada);
			return true;
		}
			
		// Verificando se o player quer adiconar glow no item
		if (args[0].equalsIgnoreCase("glow")) {
			s.sendMessage(Mensagens.Erro_Versao_Nao_Suportada);
			return true;
		}
			
		// Verificando se o player quer remover a lore
		if (args[0].equalsIgnoreCase("removelore")) {
			meta.setLore(null);
			item.setItemMeta(meta);
			s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
			return true;
		}
			
		// Verificando se o player quer adicionar uma lore
		if (args[0].equalsIgnoreCase("addlore")) {
			List<String> lore = new ArrayList<>();
			if (meta.hasLore()) lore.addAll(meta.getLore());
			String novaLinha = "";
			for (int i = 1; i < args.length; i++) {novaLinha  += args[i] + " ";}
			lore.add(novaLinha.replace('&', '�'));
			meta.setLore(lore);
			item.setItemMeta(meta);
			s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
			return true;
		}
		
		// Verificando se o player quer alterar a quantidade do item
		if (args[0].equalsIgnoreCase("quantia")) {
			int quantia;
			try {
				quantia = Integer.parseInt(args[1]);
				item.setAmount(quantia);
				s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
				return true;
			} catch (NumberFormatException e) {
				s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
				return true;
			} catch (ArrayIndexOutOfBoundsException e) {
				s.sendMessage(Mensagens.Editar_Item_Comando_Incorreto);
				return true;
			}
		}
		
		// Verificando se o player quer adiconar 'bugar' o item
		if (args[0].equalsIgnoreCase("bugar")) {
			item.setDurability(Short.MAX_VALUE);
			s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
			return true;
		}
			
		// Verificando se o player que deixar o item negativo
		if (args[0].equalsIgnoreCase("negativo")) {
			item.setAmount(Short.MAX_VALUE);
			s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
			return true;
		}
		
		// Verificando se o player quer deixar o item inquebravel
		if (args[0].equalsIgnoreCase("inquebravel")) {
			s.sendMessage(Mensagens.Erro_Versao_Nao_Suportada);
			return true;
		}
		
		// Verificando se o player quer alterar o custo de repara��o do item
		if (args[0].equalsIgnoreCase("custoreparar")) {
			int custo;
			try {
				custo = Integer.parseInt(args[1]);
				p.setItemInHand(ItemAPI.setRepairCost(item, custo == 39 ? 39 : custo - 1));
				s.sendMessage(Mensagens.Editar_Item_Com_Sucesso);
				return true;
			} catch (NumberFormatException e) {
				s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
				return true;
			} catch (ArrayIndexOutOfBoundsException e) {
				s.sendMessage(Mensagens.Editar_Item_Comando_Incorreto);
				return true;
			}
		}
		
		// Verificando se o player que adicionar algum atributo ao item
		if (args[0].equalsIgnoreCase("atributo")) {
			s.sendMessage(Mensagens.Erro_Versao_Nao_Suportada);
			return true;
		}
			
		// Caso nenhuma das op��o acima for aceita sera dado como comando incorreto
		s.sendMessage(Mensagens.Editar_Item_Comando_Incorreto);
		return true;
	}
}