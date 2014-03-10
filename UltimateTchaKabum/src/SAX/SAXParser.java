package SAX;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import Model.Mission;
import android.content.SharedPreferences;
import android.util.Log;

public class SAXParser {

	public static Mission[] getMissions(InputStream is, int pack) {
		ArrayList<Mission> allMissions = new ArrayList<Mission>();
		Mission[] missions = new Mission[3];
		int[] missionsId = new int[3];
		try {
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
					.getXMLReader();
			MissionHandler mHandler = new MissionHandler();

			xmlReader.setContentHandler(mHandler);

			xmlReader.parse(new InputSource(is));

			allMissions = mHandler.getMissions();

			for (int i = 0; i < missionsId.length; i++) {
				missionsId[i] = (((pack - 1) * 3) + i + 1);
			}

			for (Mission m : allMissions) {
				for (int i = 0; i < missionsId.length; i++) {
					if (m.getId() == missionsId[i]) {
						missions[i] = m;
					}
				}
			}
		} catch (Exception ex) {
			Log.d("XML", "SAXParser: getMissions() failed");
		}
		return missions;
	}
}
