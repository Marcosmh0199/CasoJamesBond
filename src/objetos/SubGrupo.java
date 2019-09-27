package objetos;

import java.util.ArrayList;

public class SubGrupo {
	private ArrayList<Character> letras;
    private ArrayList<Character> numeros;
    private int factibilidad;

    public SubGrupo(){
        setfactibilidad(0);
    }

    public ArrayList<Character> getLetras() {
        return letras;
    }

    public void setLetras(ArrayList<Character> letras) {
        this.letras = letras;
    }

    public ArrayList<Character> getNumeros() {
        return numeros;
    }

    public void setNumeros(ArrayList<Character> numeros) {
        this.numeros = numeros;
    }

    public void setfactibilidad(int factibilidad) {
        this.factibilidad = factibilidad;
    }

    public void aumentarFactibilidad(){
        factibilidad++;
    }

    public int getFactibilidad() {
        return factibilidad;
    }

    public char getLetra(int indice){
        return letras.get(indice);
    }

    public char getNumero(int indice){
        return numeros.get(indice);
    }
}
