package model;

public class PreClinicWaititngList {
	private long preClinicWaitingList;

	
	
	public PreClinicWaititngList(long preClinicWaitingList) {
		super();
		this.preClinicWaitingList = preClinicWaitingList;
	}

	public long getPreClinicWaitingList() {
		return preClinicWaitingList;
	}

	public void setPreClinicWaitingList(long preClinicWaitingList) {
		this.preClinicWaitingList = preClinicWaitingList;
	}

	@Override
	public String toString() {
		return "PreClinicWaititngList [preClinicWaitingList=" + preClinicWaitingList + "]";
	}
	
	
	
}

