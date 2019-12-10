import java.io.Serializable;

public class Persona implements Serializable {

	private String nombre;
	private int edad;
	private static final long serialVersionUID = -3465798897039839299L;

	public Persona(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	public Persona() {
		this.nombre = null;
	}

	public void setNombre(String nom) {
		nombre = nom;
	}

	public void setEdad(int ed) {
		edad = ed;
	}

	public String getNombre() {
		return nombre;
	}

	public int getEdad() {
		return edad;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", edad=" + edad + "]";
	}
}