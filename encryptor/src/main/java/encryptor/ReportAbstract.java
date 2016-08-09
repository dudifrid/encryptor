package encryptor;

import javax.xml.bind.annotation.*;

import encryptor.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.xml.bind.annotation.*;

import static encryptor.Enums.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
//@Data
public abstract class ReportAbstract {
	@XmlElement
	@Getter
	@NonNull private String name;
	@XmlElement
	@Getter
	private Status status;
	@XmlElement
	@Getter
	private Long time;
	@XmlElement
	@Getter
	private String exceptionName;
	@XmlElement
	@Getter
	private String exceptionMessage;
	@XmlElement
	@Getter
	private Strings myStackTrace;
	//private ArrayList<String> stackTrace;
	//private StackTraceElement[] stackTrace;
	
	private static Strings arrayToString(Object[] objects){
		if (objects==null)
			return null;
		ArrayList<String> res = new ArrayList<>();
		for (int i=0;i<objects.length;i++)
			res.add(objects[i].toString());
		return new Strings(res);
	}
	
	public void setAll(String name, Status status, Long time, String exceptionName, String exceptionMessage, StackTraceElement[] stackTrace){
		this.name=name;
		this.status=status;
		this.time=time;
		this.exceptionName=exceptionName;
		this.exceptionMessage=exceptionMessage;
		
		this.myStackTrace=arrayToString(stackTrace);
	}

}
