package david.encryptor;

import lombok.Data;


@Data
public class Report {
	final private boolean status;
	final private Long time;
	final private String ExceptionName;
	final private String ExceptionMessage;
	final private StackTraceElement ExceptionStackTrace;	
}
