package encryptor;

import java.util.ArrayList;
import lombok.Getter;

import javax.xml.bind.annotation.*;


@XmlRootElement(name ="reports")
public class Reports {
	@XmlElement(name ="report")
	@Getter private ArrayList<ReportAbstract> reports= new ArrayList<ReportAbstract>();
	
}
