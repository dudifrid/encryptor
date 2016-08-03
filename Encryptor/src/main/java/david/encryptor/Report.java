package david.encryptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@AllArgsConstructor
public class Report {
	@NonNull private String name;
	private boolean status;
	private Long time;
	private String exceptionName;
	private String exceptionMessage;
	private StackTraceElement[] stackTrace;	
}
