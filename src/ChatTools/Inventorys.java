package ChatTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventorys {

	public static ItemStack CreateItem(String lore, String title, Material item) {
        ItemStack ref = new ItemStack(item);
        ItemMeta metaref = ref.getItemMeta();
        if (lore != "") 
        {
        	ArrayList<String> lore1 = new ArrayList<String>();
        	lore1.add(Main.chat(lore));
        	metaref.setLore(lore1);
        }
        metaref.setDisplayName(Main.chat(title));
        ref.setItemMeta(metaref);
        return ref;
	}
	
	public static void main(Player p) {
		Inventory Menu = Bukkit.getServer().createInventory(p, 9, "ChatTools");
		List<String> perms = new ArrayList<>(); 
		if (p.hasPermission("ChatTools.ChatToolsMenu.MuteChat")) {
			perms.add("MuteChat");
		}
		if (p.hasPermission("ChatTools.ChatToolsMenu.ClearChat")) {
			perms.add("ClearChat");
		}
		if (p.hasPermission("ChatTools.ChatToolsMenu.AddToFilter")) {
			perms.add("Filter");
		}
		if (p.hasPermission("ChatTools.ChatToolsMenu.SlowmodeSettings")) {
			perms.add("Slowmode");
		}
		if (perms.size() == 0 ) {
			p.sendMessage(Main.chat("&4you do not have any permissions for menu items, please contatct the admins/devs."));
			return;
		}
		
		switch(perms.size()) {
		case 1:
			switch(perms.get(0)) {
			case "MuteChat":
				if (Main.ChatMute == false) {
					Menu.setItem(4, CreateItem("&aStop all players from chatting","&2Mute Chat", Material.BARRIER));
				}else {
					Menu.setItem(4, CreateItem("&alet all players from chat","&2UnMute Chat", Material.BARRIER));
				}
				break;
			case "ClearChat":
				Menu.setItem(4, CreateItem("&aBlank out the chat", "&2Clear Chat", Material.TNT));
				break;
			case "Filter":
				Menu.setItem(4, CreateItem("&aAdds a word to the filter that will automatically update", "&2Add a word to the filter", Material.HOPPER));
				break;
			case "Slowmode":
				Menu.setItem(4, CreateItem("&aslow mode settings", "&2Slow Mode", Material.SOUL_SAND));
			}
			break;
		case 2:
			for (int i = 0; i < 2; i++) {
				if (i==0) {
					switch(perms.get(i)) {
					case "MuteChat":
						if (Main.ChatMute == false) {
							Menu.setItem(3, CreateItem("&aStop all players from chatting","&2Mute Chat", Material.BARRIER));
						}else {
							Menu.setItem(3, CreateItem("&alet all players from chat","&2UnMute Chat", Material.BARRIER));
						}
							break;
					case "ClearChat":
						Menu.setItem(3, CreateItem("&aBlank out the chat", "&2Clear Chat", Material.TNT));
						break;
					case "Filter":
						Menu.setItem(3, CreateItem("&aAdds a word to the filter that will automatically update", "&2Add a word to the filter", Material.HOPPER));
						break;
					case "Slowmode":
						Menu.setItem(3, CreateItem("&aslow mode settings", "&2Slow Mode", Material.SOUL_SAND));
					}
				}else {
					switch(perms.get(i)) {
					case "MuteChat":
						if (Main.ChatMute == false) {
							Menu.setItem(5, CreateItem("&aStop all players from chatting","&2Mute Chat", Material.BARRIER));
						}else {
							Menu.setItem(5, CreateItem("&alet all players from chat","&2UnMute Chat", Material.BARRIER));
						}
						break;
					case "ClearChat":
						Menu.setItem(5, CreateItem("&aBlank out the chat", "&2Clear Chat", Material.TNT));
						break;
					case "Filter":
						Menu.setItem(5, CreateItem("&aAdds a word to the filter that will automatically update", "&2Add a word to the filter", Material.HOPPER));
						break;
					case "Slowmode":
						Menu.setItem(5, CreateItem("&aslow mode settings", "&2Slow Mode", Material.SOUL_SAND));
					}
				}
				
			}
			break;
		case 3:
			for (int i=0; i < 3; i++) {
				switch(perms.get(i)) {
				case "MuteChat":
					if (Main.ChatMute == false) {
						Menu.setItem(2*i+2, CreateItem("&aStop all players from chatting","&2Mute Chat", Material.BARRIER));
					}else {
						Menu.setItem(2*i+2, CreateItem("&alet all players from chat","&2UnMute Chat", Material.BARRIER));
					}
					break;
				case "ClearChat":
					Menu.setItem(2*i+2, CreateItem("&aBlank out the chat", "&2Clear Chat", Material.TNT));
					break;
				case "Filter":
					Menu.setItem(2*i+2, CreateItem("&aAdds a word to the filter that will automatically update", "&2Add a word to the filter", Material.HOPPER));
					break;
				case "Slowmode":
					Menu.setItem(2*i+2, CreateItem("&aslow mode settings", "&2Slow Mode", Material.SOUL_SAND));
				}
			}
			break;
		case 4:
			for (int i=0; i < 4; i++) {
				if (i==0) {
					switch(perms.get(i)) {
					case "MuteChat":
						if (Main.ChatMute == false) {
							Menu.setItem(1, CreateItem("&aStop all players from chatting","&2Mute Chat", Material.BARRIER));
						}else {
							Menu.setItem(1, CreateItem("&alet all players from chat","&2UnMute Chat", Material.BARRIER));
						}
						break;
					case "ClearChat":
						Menu.setItem(1, CreateItem("&aBlank out the chat", "&2Clear Chat", Material.TNT));
						break;
					case "Filter":
						Menu.setItem(1, CreateItem("&aAdds a word to the filter that will automatically update", "&2Add a word to the filter", Material.HOPPER));
						break;
					case "Slowmode":
						Menu.setItem(1, CreateItem("&aslow mode settings", "&2Slow Mode", Material.SOUL_SAND));
					}
				}else {
					switch(perms.get(i)) {
					case "MuteChat":
						if (Main.ChatMute == false) {
							Menu.setItem(2*i+1, CreateItem("&aStop all players from chatting","&2Mute Chat", Material.BARRIER));
						}else {
							Menu.setItem(2*i+1, CreateItem("&alet all players from chat","&2UnMute Chat", Material.BARRIER));
						}
						break;
					case "ClearChat":
						Menu.setItem(2*i+1, CreateItem("&aBlank out the chat", "&2Clear Chat", Material.TNT));
						break;
					case "Filter":
						Menu.setItem(2*i+1, CreateItem("&aAdds a word to the filter that will automatically update", "&2Add a word to the filter", Material.HOPPER));
						break;
					case "Slowmode":
						Menu.setItem(2*i+1, CreateItem("&aslow mode settings", "&2Slow Mode", Material.SOUL_SAND));
					}
				}
			}
			break;
		}
        for (int i=0;i < 9;i++) {
        	if (Menu.getItem(i) == null) {
        		Menu.setItem(i, CreateItem(""," ",Material.GRAY_STAINED_GLASS_PANE));
        	}
        }
        p.openInventory(Menu);
	}
	
	public static void slowchat(Player p) {
		Inventory Menu = Bukkit.getServer().createInventory(p, 9, "Slowmode");
		
		Menu.setItem(3, CreateItem("&aSets delay of slowmode. Delay: &2"+Main.config.getInt("slowmodedelay")+"&a Seconds","&2Delay",Material.ENDER_PEARL));
		
		if (Main.config.getBoolean("slowmode") == false) {
			Menu.setItem(5, CreateItem("&aTurns slowmode on", "&2Turn on", Material.RED_WOOL));
		}else {
			Menu.setItem(5, CreateItem("&aTurns slowmode off", "&2Turn off", Material.GREEN_WOOL));
		}
        for (int i=0;i < 9;i++) {
        	if (Menu.getItem(i) == null) {
        		Menu.setItem(i, CreateItem(""," ",Material.GRAY_STAINED_GLASS_PANE));
        	}
        }
		p.openInventory(Menu);
	}
	
}
