package ChatTools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements CommandExecutor,Listener {

	public static boolean ChatMute = false;
	public List<String> FilterAdd = new ArrayList<>();
	public List<String> DelaySet = new ArrayList<>();
	public List<String> SlowmodeWait = new ArrayList<>();
	public static FileConfiguration config;
    
	@Override
	public void onEnable() {
	    Metrics metrics = new Metrics(this, 8221);
	    metrics.addCustomChart(new Metrics.SingleLineChart("staff", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            // (This is useless as there is already a player chart by default.)
	        	int staff = 0;
	        	for (Player p : Bukkit.getOnlinePlayers()) {
	        		if (p.hasPermission("ChatTools.ChatToolsMenu")) {
	        			staff++;
	        		}
	        	}
	            return staff;
	        }
	    }));
		saveDefaultConfig();
		 config = this.getConfig();
		this.getCommand("ChatTools").setExecutor(this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) {
		 if (sender instanceof Player) {
			 Player p = (Player)sender;
			 
             Inventorys.main(p);
             
             return true;
		 }else {
			 sender.sendMessage(chat("&4you are not a human, stupid computer thinking you can open a menu, i'll add comands later nerd"));
			 return true;
		 }
	}
	@EventHandler
	private void inventoryClick(InventoryClickEvent e)
    {
  
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().equalsIgnoreCase("ChatTools"))
        {
           e.setCancelled(true);
           if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
               return;
           }
           if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Mute Chat"))) {
        	   ChatMute = true;
        	   for (Player player : Bukkit.getOnlinePlayers()) {
        		   player.sendMessage(chat("&a-----------------------------------------------------"));
        		   player.sendMessage(chat("&2Chat has been muted by "+p.getName()));
        		   player.sendMessage(chat("&a-----------------------------------------------------"));
        	   }
        	   p.closeInventory();
           }
           if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2UnMute Chat"))) {
        	   ChatMute = false;
        	   for (Player player : Bukkit.getOnlinePlayers()) {
        		   player.sendMessage(chat("&a-----------------------------------------------------"));
        		   player.sendMessage(chat("&2Chat has been unmuted by "+p.getName()));
        		   player.sendMessage(chat("&a-----------------------------------------------------"));
        	   }
        	   p.closeInventory();
           }
           if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Clear Chat"))) {
        	   for (Player player : Bukkit.getOnlinePlayers()) {
        		   for (int x = 0; x < 200; x++){
            		   p.sendMessage("");
            	   }
        		   p.sendMessage(chat("&a-----------------------------------------------------"));
        		   p.sendMessage(chat("&2Chat was cleared by "+p.getName()));
        		   p.sendMessage(chat("&a-----------------------------------------------------"));   
        	   }
        	   p.closeInventory();
           }
           
           if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Add a word to the filter"))) {
        	   p.closeInventory();
        	   p.sendMessage(chat("&aType word you want to add to the filter"));
        	   FilterAdd.add(p.getName());
           }
           if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Slow Mode"))) {
        	  // p.closeInventory();
        	   Inventorys.slowchat(p);
           }
        }
        if (e.getView().getTitle().equalsIgnoreCase("Slowmode"))
        {
            e.setCancelled(true);
            if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
                return;
            }
        	if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Turn on"))) {
        		config.set("slowmode", true);
        		saveConfig();
        		Inventorys.slowchat(p);
        		 for (Player player : Bukkit.getOnlinePlayers()) {
        			player.sendMessage(chat("&aslowmode turned on by &2"+p.getName()+" &a and is on a &2"+Main.config.getInt("slowmodedelay")+"&a second delay"));
        		 }
        	}
        	if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Turn off"))) {
        		config.set("slowmode", false);
        		saveConfig();
        		Inventorys.slowchat(p);
        		 for (Player player : Bukkit.getOnlinePlayers()) {
        			 player.sendMessage(chat("&aslowmode turned off by &2"+p.getName()));
        		 }
        	}
        	if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(chat("&2Delay"))) {
        		DelaySet.add(p.getName());
        		p.sendMessage(chat("&aType in chat a number to set delay"));
         	   p.closeInventory();
        	}
        }
    }
	@EventHandler
	private void OnChat(PlayerChatEvent e) {
		if (ChatMute == true) {
			if (!e.getPlayer().hasPermission("ChatTools.MuteChatBypass")) {
				e.getPlayer().sendMessage(chat("&cChat is Muted!"));
				e.setCancelled(true);
			}
		}
		if (e.getPlayer().hasPermission("ChatTools.TranslateColorCodes")) {
			char[] ColorCode = config.getString("translatecolorcode").toCharArray();
			if (ColorCode.length == 1) {
				e.setMessage(ChatColor.translateAlternateColorCodes(ColorCode[0],e.getMessage()));
			}
			
			
		}
		if (!e.getPlayer().hasPermission("ChatTools.BypassFiltered")) {
			List<String> filter = config.getStringList("filter");
			boolean bad = false;
			int badw = 0;
			for (int i = 0; i < filter.size(); i++) {
				if (e.getMessage().contains(filter.get(i))) {
					bad = true;
					badw = i;
					break;
				}
			}
			if (bad == true) {
				 for (Player player : Bukkit.getOnlinePlayers()) {
					 if (player.hasPermission("ChatTools.SeeFiltered")) {
					 	player.sendMessage(chat("&7[&4Filtered&7] &a"+e.getPlayer().getName() +" &7said &a"+e.getMessage()+" &7which contains &a"+filter.get(badw)));
					 }
				 }
				e.getPlayer().sendMessage(chat("&4You cant say "+filter.get(badw)));
				e.setCancelled(true);
			}
		}
		
		if (FilterAdd.contains(e.getPlayer().getName())) {
			List<String> list = this.getConfig().getStringList("filter");
			list.add(e.getMessage());
			config.set("filter", list);
			FilterAdd.remove(e.getPlayer().getName());
			saveConfig();
			e.getPlayer().sendMessage(chat("&a"+e.getMessage()+" was added to the filter"));
			e.setCancelled(true);
		}
		if (DelaySet.contains(e.getPlayer().getName())) {
			e.setCancelled(true);
			try {
				config.set("slowmodedelay",Integer.parseInt(e.getMessage()));
				saveConfig();
				e.getPlayer().sendMessage(chat("&aSet slowmode delay to &2" + Integer.parseInt(e.getMessage())));
				if (Main.config.getBoolean("slowmode") == true) {
					 for (Player player : Bukkit.getOnlinePlayers()) {
						 player.sendMessage(chat("&aSlwomode delay was set to &2"+Integer.parseInt(e.getMessage())+"&a Seconds"));
					 }
				}
				DelaySet.remove(e.getPlayer().getName());
			} catch(NumberFormatException e1) {
				e.getPlayer().sendMessage(chat("&4Send a valid number"));
				
			}
		}
		if (config.getBoolean("slowmode") == true && !e.getPlayer().hasPermission("ChatTools.SlowmodeBypass")) {
			if (SlowmodeWait.contains(e.getPlayer().getName())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(chat("&aSlwomode is on, please wait &2"+config.getInt("slowmodedelay")+"&a seconds between chats"));
			}else {
				SlowmodeWait.add(e.getPlayer().getName());
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            
				            public void run() {
				            	SlowmodeWait.remove(e.getPlayer().getName());
				            }
				        }, 
				        config.getInt("slowmodedelay") * 1000
				);
			}
		}
	}
}
