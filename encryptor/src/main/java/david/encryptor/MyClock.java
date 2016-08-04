package david.encryptor;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.TimeZone;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyClock extends Clock{
	
	@Override
	public Clock withZone(ZoneId zone) {
		//return TimeZone.getTimeZone(zone);
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
