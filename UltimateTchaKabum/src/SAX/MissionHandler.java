package SAX;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.SharedPreferences;
import Model.Mission;
import SharedPreferences.SharedPreferencesController;
public class MissionHandler extends DefaultHandler{
	Mission mission;
	private ArrayList<Mission> missions;
	private String tempVal;

	boolean bStatus = false;
	boolean bDescription = false;
	boolean bMissionType = false;
	boolean bSingleMatch = false;
	boolean bMissionKey = false;
	boolean bRequiredKeys = false;
	
	public MissionHandler(){
		missions =  new ArrayList<Mission>();
	}
	
	public ArrayList<Mission> getMissions(){
		return this.missions;
	}
	
	 public void startElement(String uri, String localName, String qName,
	            Attributes attributes) throws SAXException {
		 if (qName.equalsIgnoreCase("mission")) {
 			mission = new Mission();
 			int id = Integer.parseInt(attributes.getValue("id"));
 			mission.setId(id);    	      
 		}else if (qName.equalsIgnoreCase("status")) {
 			bStatus = true;
 		}else if(qName.equalsIgnoreCase("missionType")){
 			bMissionType = true;
 		}else if(qName.equalsIgnoreCase("description")){
 			bDescription = true;
 		}else if(qName.equalsIgnoreCase("singleMatch")){
 			bSingleMatch = true;
 		}else if(qName.equalsIgnoreCase("missionKey")){
 			bMissionKey = true;
 		}else if(qName.equalsIgnoreCase("requiredKeys")){
 			bRequiredKeys = true;
 		}
	    }
	    
	    public void characters(char[] ch, int start, int length)
	            throws SAXException {
	        tempVal = new String(ch, start, length);
	        
	        if(bStatus){
	        	mission.setStatus(tempVal);
	        	bStatus = false;
	        }
	        else if(bMissionType){
	        	mission.setMissionType(Integer.parseInt(tempVal));
	        	bMissionType = false;
	        	
	        }
	        else if(bDescription){
	        	mission.setDescription(tempVal);
	        	bDescription = false;
	        }
	        else if(bSingleMatch){
	        	mission.setSingleMatch(Boolean.parseBoolean(tempVal));
	        	bSingleMatch = false;
	        }
	        else if(bMissionKey){
	        	mission.setMissionKey(tempVal);
	        	bMissionKey = false;
	        }
	        else if(bRequiredKeys){
	        	mission.setRequiredKeys(tempVal);
	        	bRequiredKeys = false;
	        }
	        
	    }
	    
	    public void endElement(String uri, String localName, String qName)
	            throws SAXException {
	      		    if(qName.equalsIgnoreCase("mission")){
	        		missions.add(mission);
	      		    }
	        	   }
	           
	          
	    }

