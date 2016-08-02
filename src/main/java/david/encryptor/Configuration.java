package david.encryptor;

import static david.encryptor.Enums.*;
import lombok.Data;
import lombok.NonNull;

@Data public class Configuration {
	@NonNull final private Goal goal;
	@NonNull final private Scope scope;
	@NonNull final private Family family;
	@NonNull final private Algo first;
	final private Algo second;
}
