package encryptor;

import java.util.Observable;
import java.util.Observer;

import encryptor.EncdecState.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StateObserver implements Observer{
	
	@Getter private State curState; //current state
	@Getter private State prevState; //previous state
	

	public void update(Observable o, Object arg) {
		prevState = curState;
		curState = ((EncdecState) o ).getState();
	}

}
