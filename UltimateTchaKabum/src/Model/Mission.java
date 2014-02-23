package Model;

public class Mission {
	
	private int id;
	private String description;
	private String status;
	private boolean singleMatch;
	private int missionType;
	private String missionKeyType;
	private String requiredKeys;
	
	
	// Getters and Setters
	// ------------------------------------------
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isSingleMatch() {
		return singleMatch;
	}
	public void setSingleMatch(boolean singleMatch) {
		this.singleMatch = singleMatch;
	}
	public int getMissionType() {
		return missionType;
	}
	public void setMissionType(int missionType) {
		this.missionType = missionType;
	}
	public String getMissionKey() {
		return missionKeyType;
	}
	public void setMissionKey(String missionKey) {
		this.missionKeyType = missionKey;
	}
	public String getRequiredKeys() {
		return requiredKeys;
	}
	public void setRequiredKeys(String requiredKeys) {
		this.requiredKeys = requiredKeys;
	}
	
	
	// Getters and Setters facilitados
	// ------------------------------------------
	public int[] getDualStatus(){
		int[] status = new int[2];
		String aux = this.status.substring(0,this.status.indexOf(";"));
		status[0] = Integer.parseInt(aux);
		aux =  this.status.substring(this.status.indexOf(";")+1,this.status.length());
		status[1] = Integer.parseInt(aux);
		return status;
	}
	
	public int getSingleStatus(){
		return Integer.parseInt(status);
	}
	
	public int[] getDualRKeys(){
		int[] rkeys = new int[2];
		String aux = this.requiredKeys.substring(0,this.requiredKeys.indexOf(";"));
		rkeys[0] = Integer.parseInt(aux);
		aux =  this.requiredKeys.substring(this.requiredKeys.indexOf(";")+1,this.requiredKeys.length());
		rkeys[1] = Integer.parseInt(aux);
		return rkeys;
	}
	
	public int getSingleRequiredKeys(){
		return Integer.parseInt(this.requiredKeys);
	}
	
	public int getSingleMissionKeyType(){
		return Integer.parseInt(this.missionKeyType);
	}
	
	public int[] getDualMKeyType(){
		int[] mkeyType = new int[2];
		String aux = this.missionKeyType.substring(0,this.missionKeyType.indexOf(";"));
		mkeyType[0] = Integer.parseInt(aux);
		aux =  this.missionKeyType.substring(this.missionKeyType.indexOf(";")+1,this.missionKeyType.length());
		mkeyType[1] = Integer.parseInt(aux);
		return mkeyType;
	}
	
}
