package net.thebattlecraft.eegabooga.BattleChestEvents.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import net.thebattlecraft.eegabooga.BattleChestEvents.BattleChestEvents;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.ChestEvent;
import net.thebattlecraft.eegabooga.BattleChestEvents.objects.ScheduledTime;

import org.bukkit.Location;
import org.bukkit.Server;


public class BCETimeManager {
	static long last = System.currentTimeMillis();
	static final boolean DEBUG = false;
	
	class EventTask extends TimerTask{
		int progress =-1; BCETimeManager tm; ChestEvent ce;

		EventTask(EventTask et){progress = et.progress;tm = et.tm; ce=et.ce;}
		EventTask(BCETimeManager tm, ChestEvent ce){this.tm = tm; this.ce = ce;}

		int getProgress(){return progress;}

		@Override
		public void run() {
			last = System.currentTimeMillis();
			this.cancel();
			progress++;
			tm.progressEvent(this);
		}	
	}
	
	public void scheduleEvent(ChestEvent ce) {
		Timer timer = new Timer();

		last = System.currentTimeMillis();
		timer.schedule(new EventTask(this,ce) , ce.getFirstScheduledEvent());
	}

	public void progressEvent(EventTask eventTask) {
		Timer t = new Timer();
		ChestEvent ce = eventTask.ce;
		Server server = BattleChestEvents.getBukkitServer();

		ArrayList<ScheduledTime> stes = ce.getScheduledTimes();
		int progress = eventTask.getProgress();

		Location loc = ce.getLocation();
		String nameOfEvent = ce.getName();
		ScheduledTime ste = stes.get(progress);
		
		String message = ste.getMessage();
		if (message != null)
			server.broadcastMessage(formatMessage(message, nameOfEvent));

		if (DEBUG) System.out.println(ste);
		if (ste.isSpawn()){
			BCEChestManager.createChest(loc, ce.getItems());
			BCEChestManager.lockChest(loc);
		} else if (ste.isUnlock()){
			BCEChestManager.unLockChest(loc);
		} else if (ste.isDespawn()){
			BCEChestManager.removeChest(loc);
		}
		if (progress + 1 < stes.size()){
			/// Set our next timer
			ste = stes.get(progress+1);
			long time = ste.getTimeFromNow(ce.getEventTime());
	
			t.schedule(new EventTask(eventTask), time);				
		} else {
			/// We are done
			BattleChestEvents.freeEvent(ce);
		}

	}

	public static String formatMessage(String msg, Object... varArgs) {
		StringBuilder buf = new StringBuilder();
		Formatter form = new Formatter(buf);
		form.format(msg, varArgs);
		return colorChat(buf.toString());
	}

	public static String colorChat(String msg) {
		String langChar = Character.toString((char) 167);
		msg = msg.replaceAll("&", langChar);
		return msg;
	}

	class RepeatingTask extends TimerTask{
		@Override
		public void run() {
			ChestEvent ce = BattleChestEvents.getNextChestEvent();
			if (ce != null){
				ce.setEventTimeNow();
				BattleChestEvents.getTimeManager().scheduleEvent(ce);
			}
		}
	}

	public void scheduleRepeatingEveryHour() {
		long now = System.currentTimeMillis();
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		long timeToStart = cal.getTimeInMillis() + 60*60*1000; /// add an hour
		Timer t = new Timer();
		t.scheduleAtFixedRate(new RepeatingTask(), timeToStart - now , 60*60*1000);
	}

	public void scheduleRepeating(Integer everyxsec) {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new RepeatingTask(), 10000, everyxsec*1000);
	}

}
