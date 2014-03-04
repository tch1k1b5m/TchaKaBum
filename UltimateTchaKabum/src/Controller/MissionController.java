package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import Model.Mission;
import Model.MissionKeys;
import SAX.MissionHandler;
import SharedPreferences.SharedPreferencesController;

public class MissionController {

	private MissionHandler missionHandler = new MissionHandler();
	
	private Mission[] missions = new Mission[3];
	private MissionKeys MKeys = new MissionKeys();
	

	private ArrayList<Integer> missionKeys;
	private int level;
	private int score;
	private int combo;
	private int gold;
	private int fullLifesLvl;
	private int singleLifesLvl;
	private int twoLifesLvl;
	private String[] missionsStatus;
	private int missionPackage;

	public MissionController(ArrayList<Integer> missionKeys, int level, int score, int combo, int gold, int missionPackage, String[] missionStatus) {
		this.missionPackage = missionPackage;
		this.missionsStatus = missionStatus;
		this.level = level;
		this.gold = gold;
		this.combo = combo;
		this.score = score;
		this.missionKeys = missionKeys;
		LoadMissions();
	}

	public MissionController(ArrayList<Integer> missionKeys, int level) {
		LoadMissions();
		this.missionKeys = missionKeys;
	}

	public String[] getMissionsUpdatedStatus(){
		if(missions[0].getRequiredKeys() != missionsStatus[0])
			missionsStatus[0] = UpdateMission(0);
		if(missions[1].getRequiredKeys() != missionsStatus[1])
			missionsStatus[1] = UpdateMission(1);
		if(missions[2].getRequiredKeys() != missionsStatus[2])
			missionsStatus[2] = UpdateMission(2);
		
		return missionsStatus;
	}
	
	public String UpdateMission(int index) {
		int necessaryKeyCont = 0;
		int necessaryKeyCont2 = 0;
		int necessaryKeys = 0;
		int necessaryKeys2 = 0;
		String strKeys = "";
		int keysCont = 0;
		int keysCont2 = 0;
		int i = missionKeys.size();
		if (missions[index].getMissionType() == MKeys.PopAnyColorBalloonMKey) {
			if(!missions[index].isSingleMatch())
				keysCont = missions[index].getSingleStatus();
			necessaryKeyCont = missions[index].getSingleRequiredKeys();
			for(int cont = 0; cont< i;cont++){
				if(missionKeys.get(cont) > 0 && missionKeys.get(cont) < 10)
					keysCont++;
			}
			if(keysCont > necessaryKeyCont)
				keysCont = necessaryKeyCont;
			strKeys = String.valueOf(keysCont);
		}else if (missions[index].getMissionType() == MKeys.SingleActionMKey) {
			if(!missions[index].isSingleMatch())
				keysCont = missions[index].getSingleStatus();
			necessaryKeys = missions[index].getSingleMissionKeyType();
			necessaryKeyCont = missions[index].getSingleRequiredKeys();
			for(int cont = 0; cont< i;cont++){
				if(missionKeys.get(cont)== necessaryKeys)
					keysCont++;
			}
			if(keysCont > necessaryKeyCont)
				keysCont = necessaryKeyCont;
			strKeys = String.valueOf(keysCont);
		}else if(missions[index].getMissionType() == MKeys.PlayerConditionMKey) {
			if(!missions[index].isSingleMatch())
				keysCont = missions[index].getSingleStatus();
			necessaryKeys = missions[index].getSingleMissionKeyType();
			necessaryKeyCont = missions[index].getSingleRequiredKeys();
			if(missions[index].getMissionType() == MKeys.SurviveWithOneLife){
				keysCont += singleLifesLvl;
			} else if(missions[index].getMissionType() == MKeys.SurviveWithThreeLife){
				keysCont += fullLifesLvl;
			}else if(missions[index].getMissionType() == MKeys.SurviveWithTwoLife){
				keysCont += twoLifesLvl;
			}
			if(keysCont > necessaryKeyCont)
				keysCont = necessaryKeyCont;
			strKeys = String.valueOf(keysCont);
		} else if (missions[index].getMissionType() == MKeys.DualActionsMKey) {
			if(!missions[index].isSingleMatch()){
				keysCont = missions[index].getDualStatus()[0];
				keysCont2 = missions[index].getDualStatus()[1];
			}
			necessaryKeys = missions[index].getDualMKeyType()[0];
			necessaryKeys2 = missions[index].getDualMKeyType()[1];
			necessaryKeyCont = missions[index].getDualRKeys()[0];
			necessaryKeyCont2 = missions[index].getDualRKeys()[1];
			
			for(int cont = 0; cont< i;cont++){
				if(missionKeys.get(cont)== necessaryKeys)
					keysCont++;
				if(missionKeys.get(cont)== necessaryKeys2)
					keysCont2++;
			}
			if(keysCont > necessaryKeyCont)
				keysCont = necessaryKeyCont;
			if(keysCont2 > necessaryKeyCont2)
				keysCont2 = necessaryKeyCont2;
			strKeys = String.valueOf(keysCont) +";"+String.valueOf(keysCont2) ;
		}
		 
		
		return strKeys;
	}

	public void LoadMissions() {
		
		
		ArrayList<Mission> mList = missionHandler.getMissions();
		int size = mList.size();
		for(int cont = 0; cont < size; cont++){
			if(mList.get(cont).getId() == (missionPackage * 3)){
				missions[2] = mList.get(cont);
			} else if(mList.get(cont).getId() == ((missionPackage * 3)-1)){
				missions[1] = mList.get(cont);
			} else if(mList.get(cont).getId() == ((missionPackage * 3)-2)){
				missions[0] = mList.get(cont);
			}
		}
		//asdas

	}

}
