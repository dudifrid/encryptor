package encryptor;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Key implements Serializable{
	@Getter private byte firstKey;
	@Getter private byte secondKey;
	private static final long serialVersionUID = 1L;

}
