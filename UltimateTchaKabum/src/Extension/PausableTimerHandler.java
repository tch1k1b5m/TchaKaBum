package Extension;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

public class PausableTimerHandler extends TimerHandler{

	private boolean mPause = false;
	public PausableTimerHandler(float pTimerSeconds, boolean pAutoReset,ITimerCallback pTimerCallback) {
		super(pTimerSeconds, pAutoReset, pTimerCallback);
	}
	public void pause(){
		this.mPause = true;
	}
	public void resume(){
		this.mPause = false;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed){
		if(!this.mPause){
			super.onUpdate(pSecondsElapsed);
		}
	}

}
