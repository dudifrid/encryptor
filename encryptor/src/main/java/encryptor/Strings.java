package encryptor;

import java.lang.annotation.Native;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.xml.bind.annotation.*;

@AllArgsConstructor
@XmlRootElement
@NoArgsConstructor
public class Strings {
	@XmlElement(name="stackTrace")
	private ArrayList<String> strings= new ArrayList<>();

}
