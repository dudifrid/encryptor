package david.encryptor;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data public class Key implements Serializable{
	private byte firstKey;
	private byte secondKey;

}
