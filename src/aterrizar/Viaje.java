package aterrizar;

import java.util.ArrayList;

import org.uqbar.commons.model.Entity;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;


import uqbar.arena.persistence.annotations.PersistentClass;
import uqbar.arena.persistence.annotations.PersistentField;
import uqbar.arena.persistence.annotations.Relation;

import aterrizar.Asiento;




@Transactional
@Observable
@PersistentClass
public class Viaje extends Entity{
        float precio;
        ArrayList<Asiento> asientos;
        
        public Viaje(Asiento asientoUno, Asiento asientoDos, Asiento asientoTres) throws Exception
        {  
                this.precio = 0;
                
                this.asientos = new ArrayList<Asiento>();
                this.asientos.add(asientoUno);
                if(asientoDos != null)
                        this.asientos.add(asientoDos);
                if(asientoTres != null)
                        this.asientos.add(asientoTres);
        }
        @PersistentField
        public float getPrecio() {
                return this.precio;
        }
        @Relation
        public Asiento getAsientoUno(){
                return this.asientos.get(0);
        }
        public Asiento getAsientoDos(){
                return this.asientos.get(1);
        }
        public Asiento getAsientoTres(){
                return this.asientos.get(2);
        }

		public boolean estaReservado() {
			boolean estado = false;
			for(Asiento unAsiento: this.asientos){
				if(unAsiento.getEstado().equals("R"))
					estado = true;
			}
			return estado;
		}
}