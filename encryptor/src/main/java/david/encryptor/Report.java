package david.encryptor;

import static david.encryptor.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@Data
public class Report {
	@NonNull private String name;
	private Status status;
	private Long time;
	private String exceptionName;
	private String exceptionMessage;
	private StackTraceElement[] stackTrace;
	

}
