package encryptor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyClock extends Clock{
	
	@Override
	public Clock withZone(ZoneId zone) {
		return null;
	}
	
	@Override
	public Instant instant() {
		return Instant.now();
	}
	
	@Override
	public ZoneId getZone() {
		return null;
	}

}
