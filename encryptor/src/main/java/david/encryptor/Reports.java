package david.encryptor;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import javax.xml.bind.annotation.*;

@Data
@XmlRootElement
public class Reports {
	private ArrayList<Report> reports= new ArrayList<>();
}
