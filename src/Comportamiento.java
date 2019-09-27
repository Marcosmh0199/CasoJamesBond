import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import objetos.Constantes;
import objetos.SubGrupo;

public class Comportamiento implements Constantes{
    private ArrayList<Character> letras;
    private ArrayList<Character> numeros;
    private ArrayList<SubGrupo> subGrupos;
    private HashSet<Character> mejoresLetras;
    private HashSet<Character> mejoresNumeros;
    private StringBuilder llave = new StringBuilder("29dh120_dk1_3");
    private Aes aes;
    
    public Comportamiento(){
    	setAes();
    	setLetras();
        setNumeros();
        setSubGrupos();
        setMejoresLetras();
        setMejoresNumeros();
    }

	public ArrayList<Character> getLetras() {
		return letras;
	}

	public void setLetras() {
        this.letras = new ArrayList<>();
        for(char letra = 'a'; letra <= 'z'; letra++){
            letras.add(letra);
        }
	}

	public ArrayList<Character> getNumeros() {
		return numeros;
	}

	public void setNumeros() {
		this.numeros = new ArrayList<>();
        for(char numero = '0'; numero <= '9'; numero++){
            numeros.add(numero);
        }
	}

	public ArrayList<SubGrupo> getSubGrupos() {
		return subGrupos;
	}

	public void setSubGrupos() {
        subGrupos = new ArrayList<>();
        SubGrupo subGrupo;
        for(int cantidadSubrupos = 0; cantidadSubrupos < CANTIDADSUBGRUPOS; cantidadSubrupos++){
            Collections.shuffle(letras);
            Collections.shuffle(numeros);
            subGrupo = new SubGrupo();
            if(doubleRandom(100,0) > 50){
                subGrupo.setLetras(new ArrayList<>(letras.subList(letras.size()-TAMANOSUBGRUPOLETRAS,letras.size())));
                subGrupo.setNumeros(new ArrayList<>(numeros.subList(numeros.size()-TAMANOSUBGRUPONUMERO,numeros.size())));
            }else{
                subGrupo.setLetras(new ArrayList<>(letras.subList(0,TAMANOSUBGRUPOLETRAS)));
                subGrupo.setNumeros(new ArrayList<>(numeros.subList(0,TAMANOSUBGRUPONUMERO)));
            }
            subGrupos.add(subGrupo);
        }
	}

	public HashSet<Character> getMejoresLetras() {
		return mejoresLetras;
	}

	public void setMejoresLetras() {
		this.mejoresLetras = new HashSet(Arrays.asList(letras.toArray()));
	}

	public HashSet<Character> getMejoresNumeros() {
		return mejoresNumeros;
	}

	public void setMejoresNumeros() {
		this.mejoresNumeros = new HashSet(Arrays.asList(numeros.toArray()));
	}

	public StringBuilder getLlave() {
		return llave;
	}

	public void setLlave(StringBuilder llave) {
		this.llave = llave;
	}

	public Aes getAes() {
		return aes;
	}

	public void setAes() {
		this.aes = new Aes();
	}
	
    private double doubleRandom(double max, double min){
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    private int intRandom(int max, int min){
        return ThreadLocalRandom.current().nextInt(min,max);
    }
    
}
