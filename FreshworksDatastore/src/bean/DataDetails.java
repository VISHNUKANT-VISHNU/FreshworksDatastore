package bean;

import java.io.Serializable;

import org.json.simple.JSONObject;

public class DataDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;
	
	private int timeToLive;
	
	private JSONObject value;
	
	private long creationTime;
	
	public DataDetails() {
		super();
	}

	public DataDetails(String key, int timeToLive, JSONObject value, long creationTime) {
		super();
		this.key = key;
		this.timeToLive = timeToLive;
		this.value = value;
		this.creationTime = creationTime;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public JSONObject getValue() {
		return value;
	}

	public void setValue(JSONObject value) {
		this.value = value;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return "DataDetails [key=" + key + ", timeToLive=" + timeToLive + ", value=" + value + ", creationTime="
				+ creationTime + "]";
	}

	
}
