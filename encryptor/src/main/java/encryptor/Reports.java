package encryptor;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.*;

//@Data
@XmlRootElement(name ="reports")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Reports {
	@XmlElement(name ="report")
	@Getter private ArrayList<ReportAbstract> reports= new ArrayList<>();
	
	//public ArrayList<ReportAbstract> getReports(){
	//	return reports;
	//}
}
