package rush.comandos;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import rush.apis.UltimateFancy;
import rush.configuracoes.Mensagens;
import rush.configuracoes.Settings;
import rush.utils.Backup;
import rush.utils.ConfigManager;

public class ComandoSystem implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
			
		// Verificando se o sender informou o comando correto
		if (args.length < 1 || args.length > 2) {
			s.sendMessage(Mensagens.System_Comando_Incorreto);
			return true;
		}
			
		// Caso o argumento seja 'reload' ent�o recarregamos as configs do pl
		if (args[0].equalsIgnoreCase("reload")) {
			Settings.loadSettings();
			Mensagens.loadMensagens();
			s.sendMessage(Mensagens.Plugin_Recarregado_Sucesso);
			return true;
		}
			
		// Caso o argumento seja 'backup' ent�o criamos 1 backup dos arquivos do plugin
		if (args[0].equalsIgnoreCase("backup")) {
			Backup.create();
			s.sendMessage(Mensagens.Backup_Com_Sucesso);
			return true;
		}
			
		// Caso o argumento seja 'contato' ent�o � exibido os contatos do desenvolvodres
		if (args[0].equalsIgnoreCase("contato")) {
			s.sendMessage("");
			s.sendMessage("�6 �l* �eEduardo Mior - RUSHyoutuber");
			s.sendMessage("");
			s.sendMessage("�aWhatsApp: �f(54) 991343192");
			s.sendMessage("�9Facebook: �fhttp://fb.com/eduardo.mior.3");
			s.sendMessage("�bTwitter: �fhttps://twitter.com/CanalDaRUSH");
			s.sendMessage("�bSkype: �flive:eduardo-mior");
			s.sendMessage("�3Discord: �fEduardo Mior#5793");
			s.sendMessage("�cE-Mail: �feduardo-mior@hotmail.com");
			s.sendMessage("�2TeamSpeak: �frush.ts3elite.com");
			s.sendMessage("�eSpigot: �fhttps://spigotmc.org/members/mior.344828");
			return true;
		}
			
		// Caso o argumento seja 'help' ent�o � exibido a lista de comandos do plugin
		if (args[0].equalsIgnoreCase("help")) {
			
			// Verificando se o n�mero da p�gina informada � valido
			int pag = 1;
			if (args.length == 2) {
				try {
					pag = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					s.sendMessage(Mensagens.Numero_Invalido.replace("%numero%", e.getMessage().split("\"")[1]));
					return true;
				}
			}

			// Pegando o arquivo dos comandos e alista dos comandos
			FileConfiguration config = ConfigManager.getConfig("comandos");
			Set<String> keys = config.getConfigurationSection("comandos").getKeys(false);
			String[] cmds = keys.toArray(new String[keys.size()]);
				
			// Pegando o total de p�ginas, e administrando as paginas
			int maxPag =  (int) Math.ceil((double) cmds.length/10);
			int inicio = (pag - 1) * 10;
			int fim = ((pag - 1) * 10) + 10;
				
			// Verificando se a p�gina solicitada existe
			if (pag < 1 || pag > maxPag) {
				s.sendMessage("�cP�gina invalida!");
				return true;
			}
				
			// Criando a mensagem JSON com a lista de comandos
			UltimateFancy msg = new UltimateFancy();
			s.sendMessage("�e�lLista de comandos do �nSystem�e: ");
			for (int i = inicio; i < fim && i < cmds.length; i++) {
				String description = config.getString("comandos." + cmds[i] + ".descricao");
				String permission = "system." + cmds[i];
				String sempermission =  config.getString("comandos." + cmds[i] + ".sem-permissao").replace('&', '�');
				String aliases = config.getStringList("comandos." + cmds[i] + ".aliases").toString();
				boolean enabled = config.getBoolean("comandos." + cmds[i] + ".ativar-comando");
				msg.text("�b/" + cmds[i] + " �7-�f " + description + "\n");
				msg.hoverShowText(
						"�eComando ativado: �f" + enabled +
						"\n�eAliases: �f" + aliases +
						"\n�ePermiss�o: �f" + permission +
						"\n�eMensagem de erro: " + sempermission
						);
				msg.clickSuggestCmd("/" + cmds[i]);
				msg.next();
			}
				
			// Criando a mensagem JSON para passar e volta de p�gina
			msg.text("�ePagina ");
			msg.next();
			msg.text("�l[�b<�e] �r�e");
			msg.hoverShowText("�bVoltar p�gina");
			msg.clickRunCmd("/system help " + (pag - 1));
			msg.next();
			msg.text(pag + "�7/�e" + maxPag);
			msg.next();
			msg.text(" �l[�b>�e]");
			msg.hoverShowText("�bPr�xima p�gina");
			msg.clickRunCmd("/system help " + (pag + 1));
				
			// Enviando a mensagem para o player
			msg.send(s);
			return true;
		}
		
		// Caso o argumento seja 'info' ent�o � exibido algumas informa��es do plugin
		if (args[0].equalsIgnoreCase("info")) {					
			s.sendMessage("�e*-=-=-=-=-=-=-=-* �bSystem �e*-=-=-=-=-=-=-=-* ");
			s.sendMessage("�ePlugin Version: �61.3");
			s.sendMessage("�eMinecraft Version: �6" + getMinecraftVersion());
			s.sendMessage("�eServerAPI Vesrion: �6" + getApiVersion());
			s.sendMessage("�eServer JarType: �6" + getJarType());
			s.sendMessage("�e*-=-=-=-=-=-=-=-* �bSystem �e*-=-=-=-=-=-=-=-* ");
			return true;
		}
			
		// Caso nenhum dos argumentos acima for valido � dado com comando incorreto
		s.sendMessage(Mensagens.System_Comando_Incorreto);
		return true;
	}
	
	private String getMinecraftVersion() {
		String info = Bukkit.getVersion();
		return info.split("MC: ")[1].split("\\)")[0];
	}
	
	private String getApiVersion() {
		String info = Bukkit.getBukkitVersion();
		String[] version = info.split("-");
		return (version[0]+"-"+version[1]);
	}
	
	private String getJarType() {
		String info = Bukkit.getVersion();
		String version = info.split("git-")[1];
		return version.split("-")[0];
	}
}