package net.thebattlecraft.eegabooga.BattleChestEvents.objects;

public class ScheduledTime implements Comparable<ScheduledTime> {
	public static final int NORMAL = 0;
	public static final int SPAWN = 1;
	public static final int UNLOCK = 2;
	public static final int DESPAWN = 3;

	long timeBeforeEvent; /// When this is supposed to occur
	String message; /// message to display
	int specialEvent; /// spawn, unlock, lock

	public ScheduledTime(){}
	public ScheduledTime(long time, int sp){
		this.timeBeforeEvent=time;
		this.specialEvent = sp;
	}
	public long getTime() {return timeBeforeEvent;}

	public boolean isNormal() {return specialEvent == NORMAL;}
	public boolean isSpawn() {return specialEvent == SPAWN;}
	public boolean isUnlock() {return specialEvent == UNLOCK;}
	public boolean isDespawn() {return specialEvent == DESPAWN;}

	public void setSpecial(int special) {this.specialEvent = special;}
	public boolean isSpecial() {return specialEvent != NORMAL;}
	public int getSpecial(){return specialEvent;}
	public void setMessage(String message) {this.message = message;}
	public String getMessage(){return message;}
	public void setTimeBeforeEvent(long time){this.timeBeforeEvent = time;}
	public long getTimeFromNow(long eventTime) {
		long t = System.currentTimeMillis();
		return (eventTime - timeBeforeEvent) - t;
	}

	@Override
	public int compareTo(ScheduledTime arg0) {
		if (this.timeBeforeEvent == arg0.timeBeforeEvent) return 0;
		else if (this.timeBeforeEvent < arg0.timeBeforeEvent) return 1;
		else return -1;
	}
	public String toString(){
		return "special=" + specialEvent + "  time=" + timeBeforeEvent + " msg=" + message;
	}
	
}
