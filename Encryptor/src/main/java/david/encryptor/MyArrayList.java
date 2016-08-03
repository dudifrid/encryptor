package david.encryptor;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data; 

@XmlRootElement
@Data
public class MyArrayList<T> extends ArrayList<T>{
	
}
