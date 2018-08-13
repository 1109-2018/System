package rush.entidades;

import org.bukkit.inventory.ItemStack;

import rush.utils.Serializer;

public class Kit {

	private String nome;
	private String permissao;
	private long delay;
	private ItemStack[] itens;
	private int amountItens;
	
	public Kit(String nome, String permissao, long delay, String itens) {
		ItemStack[] itensStack = Serializer.deserializeListItemStack(itens);
		this.nome = nome;
		this.permissao = permissao;
		this.delay = delay;
		this.itens = itensStack;
		this.amountItens = calcule(itensStack);
	}
	
	public String getName() {
		return nome;
	}

	public void setName(String kit) {
		this.nome = kit;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public ItemStack[] getItens() {
		return itens;
	}

	public void setItens(ItemStack[] itens) {
		this.itens = itens;
		this.amountItens = calcule(itens);
	}
	
	public int getAmountItens() {
		return amountItens;
	}
	
	private int calcule(ItemStack[] itens) {
		int amount = 0;
		for (ItemStack item : itens) {
			if (item != null) amount++;
		}
		return amount;
	}

	@Override
	public String toString() {
		return "Kit [permissao=" + permissao + ", delay=" + delay + ", itens=" + itens + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kit other = (Kit) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}	
}
