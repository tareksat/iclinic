package model;

public class ClinicWaititngList {
	
  private long clinicWaitingList;

public ClinicWaititngList(long clinicWaitingList) {
	super();
	this.clinicWaitingList = clinicWaitingList;
}

public long getClinicWaitingList() {
	return clinicWaitingList;
}

public void setClinicWaitingList(long clinicWaitingList) {
	this.clinicWaitingList = clinicWaitingList;
}

@Override
public String toString() {
	return "ClinicWaititngList [clinicWaitingList=" + clinicWaitingList + "]";
}
  
  
}
