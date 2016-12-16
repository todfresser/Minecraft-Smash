package todfresser.smash.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import todfresser.smash.events.main.EventManager;
import todfresser.smash.events.main.EventState;
import todfresser.smash.extrafunctions.PlayerFunctions;
import todfresser.smash.items.main.ItemManager;
import todfresser.smash.main.Smash;
import todfresser.smash.main.Statistics;
import todfresser.smash.map.events.FlyToggleEvent;
import todfresser.smash.mobs.main.SmashEntity;
import todfresser.smash.signs.SignManager;

public class Game implements Runnable{
	
	public Scoreboard board;
	private final Map m;
	private final EventState event;
	private GameState gs;
	private final World w;
	private int lives;
	private double damagemultiplier;
	private double velocitymultiplier; 
	
	private int counter;
	private final int taskID;
	
	private HashMap<Integer, Integer> itemChance = new HashMap<>();
	private ArrayList<Integer> allowedItems;
	
	private ArrayList<Integer> allowedEvents;
	
	private ArrayList<SmashEntity> entitys = new ArrayList<>();
	
	private HashMap<UUID, SmashPlayerData> players = new HashMap<>();
	
	private static ArrayList<Game> runningGames = new ArrayList<>();
	
	public static Collection<Game> getrunningGames(){
		return runningGames;
	}
	
	
	List<BukkitRunnable> tasks = new ArrayList<>();
	
    public void registerEventRunnable(BukkitRunnable itemrunnable, long delay, long period){
    	itemrunnable.runTaskTimer(Smash.getInstance(), delay, period);
    	tasks.add(itemrunnable);
    }
    public void cancelEventRunnable(BukkitRunnable itemrunnable){
    	if (tasks.contains(itemrunnable)){
    		tasks.remove(itemrunnable);
    		itemrunnable.cancel();
    	}
    }
    public void cancelAllEventRunnables(){
    	for (BukkitRunnable run : tasks){
    		run.cancel();
    	}
    	tasks.clear();
    }
    
    public void registerEntity(SmashEntity e){
    	for (SmashEntity entity : entitys){
    		if (e.getUniqueId().equals(entity.getUniqueId())) return;
    	}
    	entitys.add(e);
    }
    
    public Collection<SmashEntity> getEntitys(){
    	return entitys;
    }
    
    public void removeEntity(SmashEntity e){
    	for (SmashEntity entity : entitys){
    		if (entity.getUniqueId().equals(e.getUniqueId())){
    			entitys.remove(entity);
    			return;
    		}
    	}
    }
    
    public void removeAllEntitys(){
    	for (SmashEntity e : entitys){
    		if (!e.isDead() && !e.isEmpty()){
    			e.remove(this, false);
    		}
    	}
    	entitys.clear();
    }
	
	
	public int getMaxLives(){
		return lives;
	}
	
	public int getDamageMultiplied(int damage){
		return (int) Math.floor(damagemultiplier* damage);
	}
	
	public void setDamageMultiplier(double damagemultiplier){
		this.damagemultiplier = damagemultiplier;
	}
	
	public double getVelocityMultiplier(){
		return velocitymultiplier;
	}
	
	public void setVelocityMultiplier(double velocitymultiplier){
		this.velocitymultiplier = velocitymultiplier;
	}
	
	public Game(Map map){
		this.m = map;
		this.w = m.generateNewWorldandID();
		System.out.println("[Smash] Die Welt " + w.getName() + " wurde erstellt.");
		this.lives = 3;
		this.damagemultiplier = 1;
		this.velocitymultiplier = 1;
		gs = GameState.Lobby;
		allowedItems = new ArrayList<>(ItemManager.getAllItemDataIDs());
		for (int i : allowedItems){
			itemChance.put(i, ItemManager.getItemData(i).getSpawnChance());
		}
		allowedEvents = new ArrayList<>(EventManager.getAllEventDataIDs());
		runningGames.add(this);
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Smash.getInstance(), this, 1, 1);
		this.event = new EventState();
		updateSigns();
	}
	
	public World getWorld(){
		return w;
	}
	
	public GameState getGameState() {
		return gs;
	}
	
	public Collection<Integer> getAllowedItemIDs(){
		return allowedItems;
	}
	
	public Collection<Integer> getAllItemIDs(){
		return itemChance.keySet();
	}
	
	public int getCustomItemSpawnChance(int itemID){
		return itemChance.get(itemID);
	}
	
	public void setCustomItemSpawnChance(int itemID, int spawnChance){
		if (itemChance.containsKey(itemID)){
			itemChance.remove(itemID);
			itemChance.put(itemID, spawnChance);
		}
	}
	
	public Collection<Integer> getAllowedEventIDs(){
		return allowedEvents;
	}
	
	public void changeMaxLives(int lives){
		if (gs.equals(GameState.Starting) || gs.equals(GameState.Lobby)){
			this.lives = lives;
			for (UUID id : players.keySet()){
				players.get(id).setLives(lives);
				Bukkit.getPlayer(id).setMaxHealth(lives*2);
				Bukkit.getPlayer(id).setHealth(lives*2);
			}
			PlayerFunctions.sendDamageScoreboard(this);
		}
	}
	
	public void setGameState(GameState gamestate){
		if (gamestate.equals(this.gs)) return;
		try{
			this.gs = gamestate;
			SignManager.update(this);
			if (gamestate.equals(GameState.Ending)){
				sendPlayerGameStats();
				counter = 15;
				cancelAllEventRunnables();
				removeAllEntitys();
			}
			if (gamestate.equals(GameState.Running)){
				counter = 100+500*lives;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return;
	}
	
	public Map getMap(){
		return m;
	}
	
	public boolean containsPlayer(Player p){
		return (players.containsKey(p.getUniqueId()));
	}
	public boolean containsPlayer(UUID id){
		return (players.containsKey(id));
	}
	
	public Collection<UUID> getAllPlayers(){
		return players.keySet();
	}
	
	public SmashPlayerData getPlayerData(Player p){
		return players.get(p.getUniqueId());
	}
	public SmashPlayerData getPlayerData(UUID id){
		return players.get(id);
	}
	
	public ArrayList<UUID> getIngamePlayers(){
		ArrayList<UUID> l = new ArrayList<>();
		for (UUID id : players.keySet()){
			if (players.get(id).getType().equals(PlayerType.Ingame)) l.add(id);
		}
		return l;
	}
	
	public ArrayList<UUID> getSpectators(){
		ArrayList<UUID> l = new ArrayList<>();
		for (UUID id : players.keySet()){
			if (players.get(id).getType().equals(PlayerType.Spectator)) l.add(id);;
		}
		return l;
	}
	
	public void sendlocalMessage(String message){
		for (UUID id : players.keySet()){
			Bukkit.getPlayer(id).sendMessage(message);
		}
	}
	
	public void respawnPlayer(Player p){
		if (players.get(p.getUniqueId()).getType().equals(PlayerType.Spectator)){
			p.teleport(m.getSpectatorSpawnPoint(w));
			
			return;
		}
		if (gs.equals(GameState.Starting) || gs.equals(GameState.Lobby) || gs.equals(GameState.Ending)){
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.setGameMode(GameMode.SURVIVAL);
			
			if (gs.equals(GameState.Ending)){
				p.teleport(getrandomSpawnLocation(p.getUniqueId()));
			}else p.teleport(m.getLobbySpawnPoint(w));
			
			p.setFoodLevel(20);
			p.setHealth(lives*2);
			p.setExp(1f);
			p.setLevel(0);
			p.setCustomNameVisible(false);
			return;
		}
		UUID id = getPlayerData(p).getLastDamager();
		if (id!= null) getPlayerData(id).addKill(); 
		if (players.get(p.getUniqueId()).getLives() <= 1){
			players.get(p.getUniqueId()).resetDamage();
			players.get(p.getUniqueId()).setSpectator();
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			sendlocalMessage(Smash.pr + p.getName() + " ist ausgeschieden!");
			if (getIngamePlayers().size() <= 1){
				setGameState(GameState.Ending);
			}
			p.setGameMode(GameMode.SPECTATOR);
			p.setFireTicks(0);
			p.setHealth(lives*2);
			for (PotionEffect effect : p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
			}
			p.teleport(m.getSpectatorSpawnPoint(w));
			PlayerFunctions.sendDamageScoreboard(this);
			PlayerFunctions.updateDamageManually(p.getUniqueId(), this);
			return;
		}else{
			//p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			getPlayerData(p).removeItems(p);
			players.get(p.getUniqueId()).resetDamage();
			sendlocalMessage(Smash.pr + p.getName() + " ist gestorben!");
			p.setFireTicks(0);
			p.teleport(getrandomSpawnLocation(p.getUniqueId()));
			p.setGameMode(GameMode.SURVIVAL);
			p.setFoodLevel(20);
			p.setHealth(players.get(p.getUniqueId()).getHealth());
			for (PotionEffect effect : p.getActivePotionEffects()){
				p.removePotionEffect(effect.getType());
			}
			p.setAllowFlight(true);
			p.setExp(1f);
			PlayerFunctions.sendDamageScoreboard(this);
			PlayerFunctions.updateDamageManually(p.getUniqueId(), this);
			return;
		}
	}
	
	public void updateSigns(){
		Sign leave = m.getleaveSign(w);
		Sign lives = m.getLiveSign(w);
		Sign items = m.getItemSign(w);
		Sign itemchance = m.getItemChanceSign(w);
		Sign eventsign = m.getEventSign(w);
		if (leave != null){
			leave.setLine(0, ChatColor.GOLD + "");
			leave.setLine(1, ChatColor.DARK_BLUE + "Spiel");
			leave.setLine(2, ChatColor.DARK_BLUE + "verlassen");
			leave.setLine(3, ChatColor.GOLD + "");
			leave.update();
		}
		if (lives != null){
			lives.setLine(0, ChatColor.GOLD + "");
			lives.setLine(1, ChatColor.DARK_BLUE + "Lebensanzahl");
			lives.setLine(2, ChatColor.DARK_BLUE + "verändern");
			lives.setLine(3, ChatColor.GOLD + "");
			lives.update();
		}
		if (items != null){
			items.setLine(0, ChatColor.GOLD + "");
			items.setLine(1, ChatColor.DARK_BLUE + "Items");
			items.setLine(2, ChatColor.DARK_BLUE + "deaktivieren");
			items.setLine(3, ChatColor.GOLD + "");
			items.update();
		}
		if (itemchance != null){
			itemchance.setLine(0, ChatColor.GOLD + "");
			itemchance.setLine(1, ChatColor.DARK_BLUE + "Item");
			itemchance.setLine(2, ChatColor.DARK_BLUE + "Seltenheit");
			itemchance.setLine(3, ChatColor.GOLD + "");
			itemchance.update();
		}
		if (eventsign != null){
			eventsign.setLine(0, ChatColor.GOLD + "");
			eventsign.setLine(1, ChatColor.DARK_BLUE + "Events");
			eventsign.setLine(2, ChatColor.DARK_BLUE + "deaktivieren");
			eventsign.setLine(3, ChatColor.GOLD + "");
			eventsign.update();
		}
	}
	
	public Location getrandomSpawnLocation(UUID respawnPlayer){
		Random r = new Random();
		Location l = null;
		int distance = 59;
		wholeloop:
		while (distance > 2){
			for (Location loc : m.getPlayerSpawns(w)){
				loop:
				{
					for (UUID id : getIngamePlayers()){
						if (Bukkit.getPlayer(id).getLocation().distance(loc) < distance && !id.equals(respawnPlayer)){
							break loop;
						}
					}
					l = loc;
					break wholeloop;
				}
			}
			distance = distance - 4;
		}
		if (l == null) l = m.getPlayerSpawns(w).get(r.nextInt(m.getPlayerSpawns(w).size()));
		int y = (int) l.getBlockY();
		while( y > 0){
			if (w.getBlockAt(l.getBlockX(), y, l.getBlockZ()).getType().equals(Material.AIR) == false){
				l.setY(y + 1);
				return l;
			}
			y--;
		}
		Block b = w.getBlockAt(l.getBlockX(), l.getBlockY() - 1, l.getBlockZ());
		if (b.getType().equals(Material.AIR)){
			b.setType(Material.STAINED_GLASS);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (gs.equals(GameState.Ending)) return;
					if (b == null || b.getType() != Material.STAINED_GLASS) return;
					b.setType(Material.AIR);
				}
			}.runTaskLater(Smash.getInstance(), 60);
		}
		return l;
		
		/*Random r = new Random();
		Location l = null;
		double distance = -1;
		for (Location loc : m.getPlayerSpawns(w)){
			for (UUID id : getIngamePlayers()){
				if (Bukkit.getPlayer(id).getLocation().distance(loc) > distance){
					distance = Bukkit.getPlayer(id).getLocation().distance(loc);
					l = loc;
				}
			}
		}
		if (l == null) l = m.getPlayerSpawns(w).get(r.nextInt(m.getPlayerSpawns(w).size()));
		int y = (int) l.getBlockY();
		while( y > 0){
			if (w.getBlockAt(l.getBlockX(), y, l.getBlockZ()).getType().equals(Material.AIR) == false){
				l.setY(y + 1);
				return l;
			}
			y--;
		}
		Block b = w.getBlockAt(l.getBlockX(), l.getBlockY() - 1, l.getBlockZ());
		if (b.getType().equals(Material.AIR)){
			b.setType(Material.STAINED_GLASS);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if (gs.equals(GameState.Ending)) return;
					if (b == null || b.getType() != Material.STAINED_GLASS) return;
					b.setType(Material.AIR);
				}
			}.runTaskLater(Smash.getInstance(), 80);
		}
		return l;*/
		
		/*for (int integer = 0; integer < m.getPlayerSpawns(w).size()*4; integer++){
			l = m.getPlayerSpawns(w).get(r.nextInt(m.getPlayerSpawns(w).size() - 1));
			int y = (int) l.getY();
			while( y > 0){
				if (w.getBlockAt(l.getBlockX(), y, l.getBlockZ()).getType().equals(Material.AIR) == false){
					l.setY(y + 1);
					return l;
				}
				y--;
			}
		}
		return m.getPlayerSpawns(w).get(r.nextInt(m.getPlayerSpawns(w).size() - 1));
		*/
		
	}
	
	public void addPlayer(Player p){
		if ((gs != GameState.Ending) && (gs != GameState.Running)){
			boolean inanothergame = false;
			for (Game g : getrunningGames()){
				if (g.players.containsKey(p.getUniqueId())) inanothergame = true;
			}
			if (inanothergame == false){
				if (players.size() >= m.getmaxPlayers()){
					p.sendMessage(Smash.pr + "Das Spiel ist schon voll.");
					return;
				}
				players.put(p.getUniqueId(), new SmashPlayerData(p.getInventory(), p.getGameMode(), lives));
				PlayerFunctions.sendDamageScoreboard(this);
				p.getInventory().clear();
				p.getInventory().setArmorContents(null);
				p.setGameMode(GameMode.SURVIVAL);
				
				p.teleport(m.getLobbySpawnPoint(w));
				
				p.setFoodLevel(20);
				p.setMaxHealth(lives*2);
				p.setHealth(lives*2);
				p.setExp(0f);
				p.setLevel(0);
				p.setCustomNameVisible(false);
				p.setAllowFlight(false);
				p.setFlying(false);
				SignManager.update(this);
				event.addPlayer(p);
				
				sendlocalMessage(Smash.pr + ChatColor.RED + p.getName() + ChatColor.GRAY + " hat das Spiel betreten.");
				if (players.size() > 1) start(30);
			}else{
				p.sendMessage(Smash.pr + "Du bist bereits in einem Spiel.");
			}
		}else{
			p.sendMessage(Smash.pr + "Dieses Spiel läuft bereits.");
		}
		//updateSign(); !!!!!!!
	}
	public void removePlayer(Player p, boolean removefromList){
		if (players.containsKey(p.getUniqueId())){
			event.removePlayer(p);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			//
			p.setGameMode(players.get(p.getUniqueId()).getpreviousGameMode());
			if (p.getGameMode().equals(GameMode.SURVIVAL) || p.getGameMode().equals(GameMode.ADVENTURE)){
				p.setAllowFlight(false);
			}else p.setAllowFlight(true);
			PlayerInventory inv = players.get(p.getUniqueId()).getpreviousInventory();
			for (int i = 0; i < inv.getSize(); i++){
				if (inv.getItem(i) != null && !inv.getItem(i).getType().equals(Material.AIR)){
					p.getInventory().setItem(i, inv.getItem(i));
				}
			}
			//p.getInventory().setContents(inv.getContents());
			//p.getInventory().setArmorContents(inv.getArmorContents());
			players.get(p.getUniqueId()).cancelAllItemRunnables();
			//
			if (gs.equals(GameState.Ending)) Statistics.update(p.getUniqueId(), getPlayerData(p));
			if (removefromList) players.remove(p.getUniqueId());
			p.teleport(m.getLeavePoint());
			PlayerFunctions.removeScoreboard(p);
			if (removefromList) PlayerFunctions.sendDamageScoreboard(this);
			if (removefromList) SignManager.update(this);
			
			for(PotionEffect effect : p.getActivePotionEffects()){
			    p.removePotionEffect(effect.getType());
			}
			p.setFoodLevel(20);
			p.setMaxHealth(20);
			p.setHealth(20);
			p.setExp(0f);
			p.setLevel(0);
			p.setCustomNameVisible(false);
			if (gs.equals(GameState.Lobby) || gs.equals(GameState.Starting)){
				sendlocalMessage(Smash.pr + ChatColor.RED + p.getName() + ChatColor.GRAY + " hat das Spiel verlassen.");
			}
			if (players.size() <= 0){
				delete(true);
				return;
			}
			if (players.size() == 1){
				if (gs.equals(GameState.Running)) this.setGameState(GameState.Ending);
				if (gs.equals(GameState.Starting)) this.setGameState(GameState.Lobby);
			}
		}
	}

    public void start(int time) {
    	if (gs.equals(GameState.Lobby)){
        	counter = time;
        	this.setGameState(GameState.Starting);
        	return;
    	}
    	if (gs.equals(GameState.Starting) && counter > time){
        	counter = time;
        	return;
    	}
	}
    
    public void delete(boolean deletefromList){
    	Bukkit.getScheduler().cancelTask(taskID);
    	cancelAllEventRunnables();
    	/*if (deletefromList) {
    		for (int i = 0; i < runningGames.size(); i++){
        		if (runningGames.get(i).getWorld().getName().equals(w.getName())){
        			runningGames.remove(i);
        			continue;
        		}
    		}
    	}*/
    	for (UUID id : players.keySet()){
    		removePlayer(Bukkit.getPlayer(id), false);
    	}
    	players.clear();
    	SignManager.removeGamefromSign(this);
    	if (deletefromList) runningGames.remove(this);
    	removeAllEntitys();
    	m.deleteWorld(w);
    }
    
    public void sendPlayerGameStats(){
    	sendlocalMessage("");
    	sendlocalMessage("");
    	sendlocalMessage("");
    	if (getIngamePlayers().size() == 1){
        	sendlocalMessage(ChatColor.GOLD + "<<" + ChatColor.BLUE + "-----------------------" + ChatColor.GOLD + ">>");
			for (UUID id : getIngamePlayers()){
				sendlocalMessage(ChatColor.GOLD + "" + ChatColor.BOLD + Bukkit.getPlayer(id).getName() + ChatColor.WHITE + " hat das Spiel gewonnen.");
				sendlocalMessage(ChatColor.WHITE + "Ausgeteilter Schaden: " + ChatColor.GOLD + getPlayerData(id).getDamageDone());
				sendlocalMessage(ChatColor.WHITE + "Erhaltener Schaden: " + ChatColor.GOLD + getPlayerData(id).getTotalDamge());
				sendlocalMessage(ChatColor.GOLD + "<<" + ChatColor.BLUE + "-----------------------" + ChatColor.GOLD + ">>");
				Statistics.addWin(id);
				Player p = Bukkit.getPlayer(id);
				getPlayerData(id).registerItemRunnable(new BukkitRunnable() {
					
					@Override
					public void run() {
						Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation().add(Math.random()*2 -1, 0, Math.random()*2 -1), EntityType.FIREWORK);
			            FireworkMeta fwm = fw.getFireworkMeta();
			            Random r = new Random();   
			            int rt = r.nextInt(5) + 1;    
			            Type type = Type.BALL;
			            if (rt == 1) type = Type.BALL;
			            if (rt == 2) type = Type.BALL_LARGE;
			            if (rt == 3) type = Type.BURST;
			            if (rt == 4) type = Type.CREEPER;
			            if (rt == 5) type = Type.STAR;  
			            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.fromBGR((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255))).withFade(Color.fromBGR((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255))).with(type).trail(r.nextBoolean()).build();
			            fwm.addEffect(effect);
			            int rp = 1;
			            fwm.setPower(rp);
			            fw.setFireworkMeta(fwm); 
					}
				}, 0, 30);          
			}
			for (UUID id : getSpectators()){
				Bukkit.getPlayer(id).sendMessage(ChatColor.GRAY + "Deine Werte zum Vergleich:");
				Bukkit.getPlayer(id).sendMessage(ChatColor.GRAY + "Ausgeteilter Schaden: " + ChatColor.GOLD + getPlayerData(id).getDamageDone());
				Bukkit.getPlayer(id).sendMessage(ChatColor.GRAY + "Erhaltener Schaden: " + ChatColor.GOLD + getPlayerData(id).getTotalDamge());
			}
		}else{
			for (UUID id : getSpectators()){
				Bukkit.getPlayer(id).sendMessage(ChatColor.GRAY + "Deine Werte:");
				Bukkit.getPlayer(id).sendMessage(ChatColor.GRAY + "Ausgeteilter Schaden: " + ChatColor.GOLD + getPlayerData(id).getDamageDone());
				Bukkit.getPlayer(id).sendMessage(ChatColor.GRAY + "Erhaltener Schaden: " + ChatColor.GOLD + getPlayerData(id).getTotalDamge());
			}
		}
    	
    }
    
    
    int i= 0;
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for (UUID id: getIngamePlayers()){
			if (Bukkit.getPlayer(id).isOnGround()){
				if (gs.equals(GameState.Running) || gs.equals(GameState.Ending)){
					/*if (Bukkit.getPlayer(id).getAllowFlight() == false){
						Bukkit.getPlayer(id).setAllowFlight(true);
						if (Bukkit.getPlayer(id).getFoodLevel() >= 5) Bukkit.getPlayer(id).setExp(1f);
					}*/
					if (Bukkit.getPlayer(id).getFoodLevel() >= 5){
						if (Bukkit.getPlayer(id).getExp() < 1f){
							Bukkit.getPlayer(id).setAllowFlight(true);
							Bukkit.getPlayer(id).setExp(1f);
						}
					} else if (Bukkit.getPlayer(id).getExp() > 0f){
						Bukkit.getPlayer(id).setExp(0f);
						Bukkit.getPlayer(id).setAllowFlight(false);
					}
					if (FlyToggleEvent.cantSmash.contains(id)) FlyToggleEvent.cantSmash.remove(id);
					
					if (getPlayerData(id).direction != null) getPlayerData(id).direction = null;
					
				}
			}else if (getPlayerData(id).direction != null){
				PlayerFunctions.deleteBlocksNearPlayer(Bukkit.getPlayer(id), getPlayerData(id));
			}
			if (Bukkit.getPlayer(id).getLocation().getY() <= 0){
				this.respawnPlayer(Bukkit.getPlayer(id));
			}
		}
		if (i == 2 || i == 12 ){
			if (gs.equals(GameState.Running) && getAllowedEventIDs().size() > 0) event.update(this);
			for (UUID id: getIngamePlayers()){
				if (Bukkit.getPlayer(id).hasPotionEffect(PotionEffectType.HUNGER)){
					if (Bukkit.getPlayer(id).getFoodLevel() > 1){
						Bukkit.getPlayer(id).setFoodLevel(Bukkit.getPlayer(id).getFoodLevel()-2);
					}
				}else if (Bukkit.getPlayer(id).hasPotionEffect(PotionEffectType.SATURATION) && Bukkit.getPlayer(id).getFoodLevel() < 19){
					Bukkit.getPlayer(id).setFoodLevel(Bukkit.getPlayer(id).getFoodLevel()+2);
				}else if (Bukkit.getPlayer(id).getFoodLevel() != 20){
					Bukkit.getPlayer(id).setFoodLevel(Bukkit.getPlayer(id).getFoodLevel()+1);
				}
			}
		}
		
		if (i == 20){
			i = 0;
			
			if (gs == GameState.Lobby){
				for (UUID id : getIngamePlayers()){
					PlayerFunctions.sendActionBar(Bukkit.getPlayer(id), ChatColor.GRAY + "Warte auf weitere Spieler");
				}
				
				return;
			}
			if (gs == GameState.Starting){
				/*if (players.size() < 2){
					this.setGameState(GameState.Lobby);
					return;
				}*/
				if (counter <= 0){
					ArrayList<Location> spawns = new ArrayList<>(m.getPlayerSpawns(w));
					Player p;
					Random r = new Random();
					for (UUID id : getIngamePlayers()){
						p = Bukkit.getPlayer(id);
						PlayerFunctions.sendTitle(p, 1, 1, 1, "", "");
						PlayerFunctions.sendActionBar(Bukkit.getPlayer(id), ChatColor.GOLD + "Viel Glück");
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						p.setGameMode(GameMode.SURVIVAL);
						p.closeInventory();
						
						int i = r.nextInt(spawns.size() -1);
						p.teleport(spawns.get(i));
						spawns.remove(i);
						
						p.setFoodLevel(20);
						p.setMaxHealth(lives*2);
						p.setHealth(lives*2);
						p.setExp(1f);
						p.setLevel(0);
						p.setCustomNameVisible(false);
						p.setAllowFlight(true);
						p.getInventory().setHeldItemSlot(0);
					}
					this.setGameState(GameState.Running);
					return;
				}
				for (UUID id : getIngamePlayers()){
					PlayerFunctions.sendActionBar(Bukkit.getPlayer(id), ChatColor.GRAY + "Das Spiel startet in " + ChatColor.GOLD + Integer.toString(counter) + ChatColor.GRAY + " Sekunden");
					if (counter <= 5) PlayerFunctions.sendTitle(Bukkit.getPlayer(id), 10, 20, 2, ChatColor.RED + Integer.toString(counter), "");
				}
				counter--;
				return;
			}
			if (gs == GameState.Running){
				if (counter <= 0){
					this.setGameState(GameState.Ending);
					//
					return;
				}
				//
				if (((double) (counter / 10) == (int)(counter / 10)) && getAllowedItemIDs().size() > 0) ItemManager.spawnRandomItem(m.getItemSpawns(w), this);
				for (UUID id : getIngamePlayers()){
					if (Bukkit.getPlayer(id).getFireTicks() > 0) PlayerFunctions.playOutDamage(this, Bukkit.getPlayer(id), 3, false);
				}
				
				counter--;
				return;
			}
			if (gs == GameState.Ending){
				if (counter <=0){
					delete(true);
					return;
				}
				//
				for (UUID id : getIngamePlayers()){
					PlayerFunctions.sendActionBar(Bukkit.getPlayer(id), ChatColor.GRAY + "Das Spiel wird beendet...");
				}
				counter--;
				return;
			}
		}else i++;
	}
}
