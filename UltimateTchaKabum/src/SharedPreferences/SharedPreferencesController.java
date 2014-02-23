package SharedPreferences;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesController {

		// -----------------------------------------------
		// Shared Preferences
		// -----------------------------------------------

		
		// Coins
		// -----------------------------------------------
	
		public void saveCoinsPreferences(String key, int value, SharedPreferences sharedPreferences) {
			Editor editor = sharedPreferences.edit();
			String coinsValue = String.valueOf(value);
			editor.putString(key, coinsValue);
			editor.commit();

		}

		public String loadCoinsPreferences(SharedPreferences sharedPreferences) {
			String coins = sharedPreferences.getString("coins", "");
			return coins;

		}
		
		// Music
		// -----------------------------------------------

		public void saveMusicPreferences(boolean bool, SharedPreferences sharedPreferences){
			Editor editor = sharedPreferences.edit();
			editor.putBoolean("MusicPreferences", bool);
			editor.commit();
			
		}
		
		public boolean loadMusicPreferences (SharedPreferences sharedPreferences){
			boolean musicIsOn = sharedPreferences.getBoolean("MusicPreferences", true);
			
			return musicIsOn;
		}
		
		// Sounds
				// -----------------------------------------------

				public void saveSoundPreferences(boolean bool, SharedPreferences sharedPreferences){
					Editor editor = sharedPreferences.edit();
					editor.putBoolean("SoundPreferences", bool);
					editor.commit();
					
				}
				
				public boolean loadSoundPreferences (SharedPreferences sharedPreferences){
					boolean soundIsOn = sharedPreferences.getBoolean("SoundPreferences", true);
					
					return soundIsOn;
				}
		/*
		 
		 >>>>>>>>>>>>> Challenge <<<<<<<<<<<<<
		 
		*/
		
		// Combo
		// -----------------------------------------------
		
		public void saveComboPreferences(String key, int value, SharedPreferences sharedPreferences) {
			Editor editor = sharedPreferences.edit();
			editor.putInt(key, value);
			editor.commit();

		}

		public int loadComboPreferences(SharedPreferences sharedPreferences) {
			int combo = sharedPreferences.getInt("combo", 0);
			return combo;
		}

		// Score
		// -----------------------------------------------
		
		public void saveScorePreferences(int[] scores, SharedPreferences sharedPreferences) {
			Editor editor = sharedPreferences.edit();
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < scores.length; i++) {
				str.append(scores[i]).append(",");
			}
			editor.putString("challenge_scores", str.toString());
			editor.commit();

		}

		public int[] loadScorePreferences(SharedPreferences sharedPreferences) {
			int[] scores = new int[10];
			String savedString = sharedPreferences
					.getString("challenge_scores", "");
			StringTokenizer st = new StringTokenizer(savedString, ",");
			int indice = st.countTokens();
			for (int i = 0; i < indice; i++) {
				try {
					scores[i] = Integer.parseInt(st.nextToken());
					} catch (Exception e) {
				}

			}
			return scores;

		}

		// Mission Completed
		// -----------------------------------------------
		
		public void saveMissionStatusPreferences(String [] mStatus, SharedPreferences sharedPreferences) {
			Editor editor = sharedPreferences.edit();
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < mStatus.length; i++) {
				str.append(mStatus[i]).append(",");
			}
			editor.putString("mission_status", str.toString());
			editor.commit();

		}

		public String[] loadMissionStatusPreferences(SharedPreferences sharedPreferences) {
			String[] mStatus = new String[3];
			String savedString = sharedPreferences
					.getString("mission_status", "");
			StringTokenizer st = new StringTokenizer(savedString, ",");
			for (int i = 0; i < mStatus.length; i++) {
				try {
					mStatus[i] = st.nextToken();
					} catch (Exception e) {
				}

			}
			return mStatus;

		}
		
		// Mission
		// -----------------------------------------------
			
				public void savePackagePreferences(int packageMission, SharedPreferences sharedPreferences) {
					Editor editor = sharedPreferences.edit();
					int packageId = packageMission;
					editor.putInt("package", packageId);
					editor.commit();

				}

				public int loadPackagePreferences(SharedPreferences sharedPreferences) {
					int packageId = sharedPreferences.getInt("package",0);
					return packageId;

				}
		
		/*
		 
		 >>>>>>>>>>>>> Store <<<<<<<<<<<<<
		 
		*/
		
		// Set
		// -----------------------------------------------
		
		public void saveSetPreferences(int[] set, SharedPreferences sharedPreferences) {
			Editor editor = sharedPreferences.edit();
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < set.length; i++) {
				str.append(set[i]).append(",");
			}
			editor.putString("challenge_set", str.toString());
			editor.commit();

		}

		public int[] loadSetPreferences(SharedPreferences sharedPreferences) {
			int[] set = new int[3];
			String savedString = sharedPreferences
					.getString("challenge_set", "");
			StringTokenizer st = new StringTokenizer(savedString, ",");
			for (int i = 0; i < set.length; i++) {
				try {
					set[i] = Integer.parseInt(st.nextToken());
					} catch (Exception e) {
				}

			}
			return set;

		}
		
		// Items
		// -----------------------------------------------
		
		public void saveChallengeItemsPreferences(ArrayList<Integer> items, SharedPreferences sharedPreferences){
			Editor editor = sharedPreferences.edit();
			StringBuilder str = new StringBuilder();
			for (Integer i : items) {
				str.append(i).append(",");
			}
			editor.putString("challenge_items", str.toString());
			editor.commit();

		}
		
		public ArrayList<Integer> loadChallengeItemsPreferences(SharedPreferences sharedPreferences) {
			ArrayList<Integer> items = new ArrayList<Integer>();
			String savedString = sharedPreferences
					.getString("challenge_items", "");
			StringTokenizer st = new StringTokenizer(savedString, ",");
			int indice = st.countTokens();
			for (int i = 0; i<indice; i++) {
				try {
					items.add(Integer.parseInt(st.nextToken()));
				} catch (Exception e) {
				}

			}
			return items;

		}
		
		// Special Balloons
				// -----------------------------------------------
				
				public void saveSpecialBalloonsPreferences(ArrayList<Integer> items, SharedPreferences sharedPreferences){
					Editor editor = sharedPreferences.edit();
					StringBuilder str = new StringBuilder();
					for (Integer i : items) {
						str.append(i).append(",");
					}
					editor.putString("special_ballons", str.toString());
					editor.commit();

				}
				
				public ArrayList<Integer> loadSpecialBalloonsPreferences(SharedPreferences sharedPreferences) {
					ArrayList<Integer> sBalloons = new ArrayList<Integer>();
					String savedString = sharedPreferences
							.getString("special_ballons", "");
					StringTokenizer st = new StringTokenizer(savedString, ",");
					int indice = st.countTokens();
					for (int i = 0; i<indice; i++) {
						try {
							sBalloons.add(Integer.parseInt(st.nextToken()));
						} catch (Exception e) {
						}

					}
					return sBalloons;

				}
			
		/*
		 
		 >>>>>>>>>>>>> Genius <<<<<<<<<<<<<
		 
		*/		
		
		// Genius High Level
		// -----------------------------------------------
		
		public void saveHighLevelPreferences(int value, SharedPreferences sharedPreferences) {
			Editor editor = sharedPreferences.edit();
			editor.putInt("high_level", value);
			editor.commit();

		}

		public int loadHighLevelPreferences(SharedPreferences sharedPreferences) {
			int combo = sharedPreferences.getInt("high_level", 0);
			return combo;
		}
}
