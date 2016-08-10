package encryptor;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.*;

@AllArgsConstructor
@XmlRootElement
@NoArgsConstructor
public class Strings {
	@XmlElement(name="stackTrace")
	private ArrayList<String> strings= new ArrayList<String>();

}
