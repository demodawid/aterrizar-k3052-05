package aterrizar;

public class NivelNormal implements NivelImportancia {

	@Override
	public Boolean puedeVer(Asiento unAsiento) {
		return !unAsiento.esSuperOferta();
	}

}
