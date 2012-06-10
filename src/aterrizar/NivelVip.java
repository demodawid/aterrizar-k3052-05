package aterrizar;

public class NivelVip implements NivelImportancia {

	@Override
	public Boolean puedeVer(Asiento unAsiento) {
		return true;
	}

}
