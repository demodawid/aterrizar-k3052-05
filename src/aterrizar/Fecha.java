package aterrizar;

public class Fecha {
	private Integer anios;
	private Integer meses;
	private Integer dias;
	
	public Fecha(Integer dias, Integer meses, Integer anios){
		super();
		this.dias = dias;
		this.meses = meses;
		this.anios = anios;
	}
	
	public Integer getAnios(){
		return this.anios;
	}
	
	public Integer getMeses(){
		return this.meses;
	}
	
	public Integer getDias(){
		return this.dias;
	}
	
	public Integer diasEntre(Fecha otraFecha){
		Integer misDias = this.getAnios()*365 		+ this.getMeses()*30 		+ this.getDias();
		Integer susDias = otraFecha.getAnios()*365  + otraFecha.getMeses()*30   + otraFecha.getDias();
		return Math.abs(misDias - susDias);
		
	}
	
	public Boolean esLaMisma(Fecha otraFecha){
		return 	( this.getAnios() == otraFecha.getAnios() &&
				this.getMeses() == otraFecha.getMeses() &&
				this.getDias()  == otraFecha.getDias() 
				);
	}
	
	public Boolean esAnterior(Fecha otraFecha){
		if (this.getAnios() > otraFecha.getAnios()){
			return true;
		}
		else if(this.getAnios() == otraFecha.getAnios() && 
				this.getMeses() > otraFecha.getMeses()){
			return true;
		}
		else if (this.getAnios() == otraFecha.getAnios() && 
				this.getMeses() == otraFecha.getMeses() &&
				this.getDias() > otraFecha.getDias()){
			return true;
		}
		
		return false;
	}
	
	public Boolean esPosterior(Fecha otraFecha){
		return !( this.esAnterior(otraFecha) || this.esLaMisma(otraFecha) );
	}

	public String comoString() {
		if(this.anios == 0 || this.meses == 0 || this.dias == 0){
			throw new FechaInvalidaException();
		}
		
		return "" + this.anios + this.meses + this.dias;
	}
}
