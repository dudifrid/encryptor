package david.encryptor;

import javax.xml.bind.annotation.*;
import static david.encryptor.Enums.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Data public class Configuration {
	@NonNull private Goal goal;
	@NonNull private Scope scope;
	@NonNull private Family family;
	@NonNull private Algo first;
	private Algo second;
}
