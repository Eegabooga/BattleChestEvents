package net.thebattlecraft.eegabooga.BattleChestEvents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Logger;

import net.thebattlecraft.eegabooga.BattleChestEvents.listeners.BCEPlayerListener;
import net.thebattlecraft.eegabooga.BattleChestEvents.listeners.BCEPluginListener;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.BCEChestManager;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.BCETimeManager;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.BCETimeManager.EventTask;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.CommandManager;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.ConfigController;
import net.thebattlecraft.eegabooga.BattleChestEvents.managers.PermissionManager;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.ChestEvent;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;


public class BattleChestEvents extends JavaPlugin {

	public static final String DEFAULT_CONFIGURATION_FILE = "/default_files/config.yml";
	private static String CONFIGURATION_FILE;
	Logger log = Logger.getLogger("Minecraft");
	public final BCEPlayerListener playerListener = new BCEPlayerListener(this);
	public static String prefix = "";
	public static PermissionHandler permissionHandler;
	
	BCEChestManager chestManager = new BCEChestManager();
	BCETimeManager timeManager = new BCETimeManager();
	CommandManager commandManager = new CommandManager(chestManager, timeManager);
	Listener pluginListener = new BCEPluginListener();
	static BattleChestEvents plugin;
	
	
	static Server server;
	static String pluginname;
	static String version;
	static Set<ChestEvent> active_events = new HashSet<ChestEvent>();
	static LinkedList<ChestEvent> inactive_events = new LinkedList<ChestEvent>();
	
	public void onDisable() {
		BCETimeManager.cancelEvents();
		log.info(prefix()+"Plugin has been disabled.");		
	}

	public void onEnable() {
		log.info(prefix()+" is starting enable.");
		server = getServer();
		PluginManager pm = server.getPluginManager();
        File dir = this.getDataFolder();
        if (!dir.exists()){
        	dir.mkdirs();}
        CONFIGURATION_FILE = this.getDataFolder() + "/config.yml";
        
        plugin = this;
        
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLUGIN_ENABLE, pluginListener, Priority.Monitor, this);
		loadConfig();
		schedule(ConfigController.scheduleAtIntervals);
		log.info(prefix+" enabled!.");
	}
	
	public String prefix() {
		PluginDescriptionFile desc = this.getDescription();
		prefix = "[" + desc.getName() + " v" + desc.getVersion() + "]";
		return prefix;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			/// Check to see if has permissions before handling the command
			if (PermissionManager.isAdmin((Player)sender, "battlechestevents.admin"))
				return commandManager.handleCommand((Player) sender,cmd,commandLabel, args);
			else 
				return true;
		}
		return true;
	}
	public static Server getBukkitServer(){return server;}

	public static String getPluginNameVersion() {return prefix;}

	public void loadConfig(){
        ConfigController.setConfig(
        		loadFile(getClass().getResourceAsStream(DEFAULT_CONFIGURATION_FILE), 
        				CONFIGURATION_FILE));
        
	}

	public static File loadFile(InputStream inputStream, String config_file) {
		File file = new File(config_file);
		if (!file.exists()){ /// Create a new config file from our default
			try{
				OutputStream out=new FileOutputStream(config_file);
				byte buf[]=new byte[1024];
				int len;
				while((len=inputStream.read(buf))>0){
					out.write(buf,0,len);}
				out.close();
				inputStream.close();
			} catch (Exception e){
			}
		}
		return file;
	}

	public static void freeEvent(ChestEvent e) {
		inactive_events.add(e);
	}
	
	
	public static ChestEvent getNextChestEvent() {
		return (inactive_events.isEmpty()) ? null : inactive_events.remove();
	}

	public static void addChestEvent(ChestEvent ce) {
		inactive_events.add(ce);
	}

	public static void schedule(String scheduleAtIntervals) {
		if (scheduleAtIntervals.equalsIgnoreCase("NONE")){
			/// Do Nothing
		} else if (scheduleAtIntervals.equalsIgnoreCase("EVERYHOUR")){
			getTimeManager().scheduleRepeatingEveryHour();
		} else {
			getTimeManager().scheduleRepeating(Integer.valueOf(scheduleAtIntervals.replaceAll(" ", "")));
			
		}
	}
	public static BCETimeManager getTimeManager(){return plugin.timeManager;}
}
