package domein;

import java.util.*;
import persistentie.OpdrachtenMapper;

public class OpdrachtRepository {

	private Collection<Opdracht> opdrachten;
        private OpdrachtenMapper om;

	public OpdrachtRepository() {
		om = new OpdrachtenMapper();
	}

}