package encryptor;

import static encryptor.Enums.*;

import javax.xml.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Data public class Configuration {
	@NonNull private Goal goal;
	@NonNull private Sync sync;
	@NonNull private Type type;
	@NonNull private Family family;
	@NonNull private Algo first;
	private Algo second;
}
