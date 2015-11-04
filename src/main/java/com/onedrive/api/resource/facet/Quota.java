package com.onedrive.api.resource.facet;

public class Quota {
	public static final String STATE_NORMAL = "normal";
	public static final String STATE_NEARING = "nearing";
	public static final String STATE_CRITICAL = "critical";
	public static final String STATE_EXCEEDED = "exceeded";
	private Long total;
	private Long used;
	private Long remaining;
	private Long deleted;
	private String state;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Long getUsed() {
		return used;
	}
	public void setUsed(Long used) {
		this.used = used;
	}
	public Long getRemaining() {
		return remaining;
	}
	public void setRemaining(Long remaining) {
		this.remaining = remaining;
	}
	public Long getDeleted() {
		return deleted;
	}
	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Quota [total=" + total + ", used=" + used + ", remaining=" + remaining + ", deleted=" + deleted
				+ ", state=" + state + "]";
	}
}