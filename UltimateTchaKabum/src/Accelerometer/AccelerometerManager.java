package Accelerometer;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class AccelerometerManager {

	private static Context aContext = null;
	
	// Accuracy Configuration
	private static float threshold  = 20.0f;
	private static int interval = 1000;
	
	private static Sensor sensor;
	private static SensorManager sensorManager;
	
	private static AccelerometerListener listener;
	
	private static Boolean supported;
	private static boolean running = false;
	
	public static boolean isListening(){
		return running;
	}
	
	public float getThreshold(){
		return threshold;
	}
	
	public static void stopListening(){
		running = false;
		try{
			if(sensorManager != null && sensorEventListener != null){
				sensorManager.unregisterListener(sensorEventListener);
			}
		}catch(Exception ex){
			
		}
	}
	
	public static boolean isSupported(Context context){
		aContext = context;
		if(supported == null){  
			if(aContext != null){
		sensorManager = (SensorManager) aContext.
                  getSystemService(Context.SENSOR_SERVICE);
          
          // Get all sensors in device
          List<Sensor> sensors = sensorManager.getSensorList(
                  Sensor.TYPE_ACCELEROMETER);
          
          supported = new Boolean(sensors.size() > 0);
          
          
          
      } else {
          supported = Boolean.FALSE;
      }
  }
		return supported;
	}
	
	 /**
     * Configure the listener for shaking
     * @param threshold
     *             minimum acceleration variation for considering shaking
     * @param interval
     *             minimum interval between to shake events
     */
    public static void configure(int threshold, int interval) {
        AccelerometerManager.threshold = threshold;
        AccelerometerManager.interval = interval;
    }
 
    /**
     * Registers a listener and start listening
     * @param accelerometerListener
     *             callback for accelerometer events
     */
    public static void startListening( AccelerometerListener accelerometerListener ) 
    {
    	
        sensorManager = (SensorManager) aContext.
                getSystemService(Context.SENSOR_SERVICE);
        
        // Take all sensors in device
        List<Sensor> sensors = sensorManager.getSensorList(
                Sensor.TYPE_ACCELEROMETER);
        
        if (sensors.size() > 0) {
        	
            sensor = sensors.get(0);
            
            // Register Accelerometer Listener
            running = sensorManager.registerListener(
                    sensorEventListener, sensor, 
                    SensorManager.SENSOR_DELAY_GAME);
            
            listener = accelerometerListener;
        }
        
		
    }
 
    /**
     * Configures threshold and interval
     * And registers a listener and start listening
     * @param accelerometerListener
     *             callback for accelerometer events
     * @param threshold
     *             minimum acceleration variation for considering shaking
     * @param interval
     *             minimum interval between to shake events
     */
    public static void startListening(
            AccelerometerListener accelerometerListener, 
            int threshold, int interval) {
        configure(threshold, interval);
        startListening(accelerometerListener);
    }
 
    /**
     * The listener that listen to events from the accelerometer listener
     */
    private static SensorEventListener sensorEventListener = 
        new SensorEventListener() {
 
        private long now = 0;
        private long timeDiff = 0;
        private long lastUpdate = 0;
        private long lastShake = 0;
 
        private float x = 0;
        private float y = 0;
        private float z = 0;
        private float lastX = 0;
        private float lastY = 0;
        private float lastZ = 0;
        private float force = 0;
 
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        	
        }
 
        public void onSensorChanged(SensorEvent event) {
            // use the event timestamp as reference
            // so the manager precision won't depends 
            // on the AccelerometerListener implementation
            // processing time
            now = event.timestamp;
 
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
 
            // if not interesting in shake events
            // just remove the whole if then else block
            if (lastUpdate == 0) {
                lastUpdate = now;
                lastShake = now;
                lastX = x;
                lastY = y;
                lastZ = z;
                Toast.makeText(aContext,"No Motion detected", 
                   Toast.LENGTH_SHORT).show();
            	
            } else {
                timeDiff = now - lastUpdate;
               
                if (timeDiff > 0) { 
                	
                    /*force = Math.abs(x + y + z - lastX - lastY - lastZ) 
                                / timeDiff;*/ 
                    force = Math.abs(x + y + z - lastX - lastY - lastZ);
                	
                	if (Float.compare(force, threshold) >0 ) {
                    	//Toast.makeText(Accelerometer.getContext(), 
                        //(now-lastShake)+"  >= "+interval, 1000).show();
                        
                        if (now - lastShake >= interval) { 
                        	
                            // trigger shake event
                            listener.onShake(force);
                        }
                        else
                        {
                        	Toast.makeText(aContext,"No Motion detected", 
                                Toast.LENGTH_SHORT).show();
                        	
                        }
                        lastShake = now;
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                    lastUpdate = now; 
                }
                else
                {
                
                	
                }
            }
            // trigger change event
            listener.onAccelerationChanged(x, y, z);
        }
 
    };
	
}
