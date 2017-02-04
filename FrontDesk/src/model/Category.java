package model;

public class Category {
	private String catName;
	private String currentPatient;
	private String lastPatient;
	private int waitingPatients;
	private int preClinicWaitingPatients;
	
	
	public Category(String catName, String currentPatient, String lastPatient,
			int waitingPatients, int preClinicWaitingPatients) {
		super();
		this.catName = catName;
		this.currentPatient = currentPatient;
		this.lastPatient = lastPatient;
		this.waitingPatients = waitingPatients;
		this.preClinicWaitingPatients = preClinicWaitingPatients;
	}


	public int getPreClinicWaitingPatients() {
		return preClinicWaitingPatients;
	}


	public void setPreClinicWaitingPatients(int preClinicWaitingPatients) {
		this.preClinicWaitingPatients = preClinicWaitingPatients;
	}


	public String getLastPatient() {
		return lastPatient;
	}


	public void setLastPatient(String lastPatient) {
		this.lastPatient = lastPatient;
	}


	public Category() {
		super();
	}


	public String getCatName() {
		return catName;
	}


	public void setCatName(String catName) {
		this.catName = catName;
	}


	public String getCurrentPatient() {
		return currentPatient;
	}


	public void setCurrentPatient(String currentPatient) {
		this.currentPatient = currentPatient;
	}


	public int getWaitingPatients() {
		return waitingPatients;
	}


	public void setWaitingPatients(int waitingPatients) {
		this.waitingPatients = waitingPatients;
	}


	@Override
	public String toString() {
		return "Category [catName=" + catName + ", currentPatient=" + currentPatient + ", lastPatient=" + lastPatient
				+ ", waitingPatients=" + waitingPatients + ", preClinicWaitingPatients=" + preClinicWaitingPatients
				+ "]";
	}


	


	
	
	
}
