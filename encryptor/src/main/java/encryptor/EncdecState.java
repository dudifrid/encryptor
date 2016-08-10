package encryptor;

import java.util.Observable;

import lombok.Getter;

public class EncdecState extends Observable{
	
	@Getter private State state;
	
	public static enum State{
		BEFORE,
		DURING,
		AFTER
	}
	
	public void setState(State state){
		this.state = state;
		setChanged();
		notifyObservers(state);
	}

	public EncdecState() {
		state = State.BEFORE;
	}

}
