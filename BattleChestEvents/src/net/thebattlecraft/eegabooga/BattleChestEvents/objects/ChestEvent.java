package net.thebattlecraft.eegabooga.BattleChestEvents.objects;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class ChestEvent {
	Random r = new Random();
	Calendar timeOfEvent = new GregorianCalendar();
	SpawnLocation loc;
	private ArrayList<ScheduledTime> stes = new ArrayList<ScheduledTime>();
	List<ItemStack> orig_items = new ArrayList<ItemStack>();
	List<ItemStack> items = new ArrayList<ItemStack>();
	boolean randomizeQuantities = false;
	
	public ChestEvent(){}

	public ArrayList<ScheduledTime> getScheduledTimes(){return stes;}
	public Location getLocation(){return loc.location;}
	public String getName(){return loc.name;}

	public List<ItemStack> getItems(){return items;}
	public void setSpawnLocation(SpawnLocation sl) {loc = sl;}

	public void addItem(ItemStack is) {orig_items.add(is);}

	
	public void setEventTime(long time){
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(time);
		timeOfEvent = cal;
	}

	public long getTimeInMillis(){
		return timeOfEvent.getTimeInMillis();
	}

	public long getTimeDifference(long time) {
		return timeOfEvent.getTimeInMillis() - time;
	}

	

	public long getEventTime() {return timeOfEvent.getTimeInMillis();}

	public long totalEventTime(){
		long min_time = stes.get(0).getTime(); /// +number
		long max_time = stes.get(stes.size()-1).getTime();///-number
		return min_time - max_time;
	}
	
	public void setEventTimeNow() {
		long t = new GregorianCalendar().getTimeInMillis();
		/// add 1 second just to be sure we complete all events
		/// 0 might be dicy with timers
		t += stes.get(0).getTime() + 1000; 

		timeOfEvent.setTimeInMillis(t);
		setItems();
	}

	public void setEventTime(Calendar cal) {
		timeOfEvent = cal;
		setItems();
	}

	public Long getFirstScheduledEvent() {		
		return stes.get(0).getTimeFromNow(timeOfEvent.getTimeInMillis());
	}

	public void addScheduledTime(ArrayList<ScheduledTime> stes) {
		this.stes = stes;
	}

	public void setRandomizeQuantities(boolean randomizeQuantities) {
		this.randomizeQuantities = randomizeQuantities;		
	}
	
	private void setItems(){
		items.clear();
		for (ItemStack is: orig_items){
			items.add(new ItemStack(is.getTypeId(), r.nextInt(is.getAmount())));
		}
	}

}
