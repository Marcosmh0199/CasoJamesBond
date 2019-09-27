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
        aprender(0);
        afinar();
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
    
    public void aprender(int tandasAprendizaje){
        if(tandasAprendizaje > TANDASAPRENDIZAJE){
            return;
        }
        for(int grupos = 0; grupos < subGrupos.size(); grupos++){
            for(int intentos = 0; intentos < COMBSUBGRUPOS; intentos++){
                char letra = subGrupos.get(grupos).getLetra((int)doubleRandom(TAMANOSUBGRUPOLETRAS,0.0));
                char numero = subGrupos.get(grupos).getNumero((int)doubleRandom(TAMANOSUBGRUPONUMERO,0.0));
                if(tantearLlave(letra,numero) != null){
                    subGrupos.get(grupos).aumentarFactibilidad();
                }
            }
        }
        aprender(tandasAprendizaje+1);
    }
    
    private void afinar(){
        Collections.sort(subGrupos, (s1, s2) -> Integer.valueOf(s2.getFactibilidad()).compareTo(s1.getFactibilidad()));
        subGrupos = new ArrayList<>(subGrupos.subList(0,2));     
        for(SubGrupo subGrupo:subGrupos){
            HashSet<Character> letrasTmp = new HashSet(Arrays.asList(subGrupo.getLetras().toArray()));
            mejoresLetras.retainAll(letrasTmp);
            HashSet<Character> numerosTmp = new HashSet(Arrays.asList(subGrupo.getNumeros().toArray()));
            mejoresNumeros.retainAll(numerosTmp);
        }
        obtenerLlave();
    }
    
    private void obtenerLlave(){
        ArrayList<Character> letrasAptas = new ArrayList(mejoresLetras);
        ArrayList<Character> numerosAptos = new ArrayList<>(mejoresNumeros);
        ArrayList<Character[]> combinaciones = new ArrayList<>();
        for(int letra = 0; letra < letrasAptas.size(); letra++){
        	for(int numero = 0; numero < numerosAptos.size(); numero++){
        		Character[] comb = {letrasAptas.get(letra),numerosAptos.get(numero)};
        		combinaciones.add(comb);
        	}
        }
        Collections.shuffle(combinaciones);
        int contadorIntentos = 0;
        String textoDesencriptado = null;
        while (combinaciones.size() > 0){
        	Character[] comb = combinaciones.remove(0);
        	textoDesencriptado = tantearLlave(comb[LETRA],comb[NUMERO]);
            contadorIntentos++;
            if(textoDesencriptado != null){
            	System.out.println("Intentos necesarios: "+(contadorIntentos+TANDASAPRENDIZAJE));
            	System.out.println(textoDesencriptado);
            	return;
            }
        }
        System.out.println("Llave no encontrada");
    }
    
    private String tantearLlave(char letra, char numero){
        llave.setCharAt(7,letra);
        llave.setCharAt(11,numero);
        return aes.desencriptar(llave.toString(),TEXTOENCRIPTADO);
    }
    
}
