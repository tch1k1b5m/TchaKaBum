package Controller;

import java.util.ArrayList;
import java.util.Collections;

import Model.Mission;
import Model.MissionKeys;

public class MissionController {

	private Mission[] missions = new Mission[3];
	private MissionKeys MKeys = new MissionKeys();

	private ArrayList<Integer> missionKeys;
	private int level;
	private int score;
	private int combo;
	private int gold;
	private int fullLifesLvl;
	private int singleLifesLvl;

	public MissionController(ArrayList<Integer> missionKeys, int level,
			int score, int combo, int gold) {
		LoadMissions();
		this.missionKeys = missionKeys;
	}

	public MissionController(ArrayList<Integer> missionKeys, int level) {
		LoadMissions();
		this.missionKeys = missionKeys;
	}

	public void UpdateMission(int index) {
		int necessaryKeys = 0;
		int keysCont = 0;
		int i = missionKeys.size();
		if (missions[index].getMissionType() == MKeys.PopAnyColorBalloonMKey) {
			if(!missions[index].isSingleMatch())
				keysCont = missions[index].getSingleStatus();
			for(int cont = 0; cont< i;cont++){
				if(missionKeys.get(cont) > 0 && missionKeys.get(cont) < 10)
					keysCont++;
			}
		}else if (missions[index].getMissionType() == MKeys.SingleActionMKey) {
			if(!missions[index].isSingleMatch())
				keysCont = missions[index].getSingleStatus();
			necessaryKeys = missions[index].getSingleRequiredKeys();
			for(int cont = 0; cont< i;cont++){
				if(missionKeys.get(cont)== necessaryKeys)
					keysCont++;
			}
		}else if(missions[index].getMissionType() == MKeys.PlayerConditionMKey) {
			keysCont = missions[index].getSingleStatus();
			necessaryKeys = missions[index].getSingleRequiredKeys();
			
		}
	}

	public void LoadMissions() {
		// Load from database
		
	}

}
