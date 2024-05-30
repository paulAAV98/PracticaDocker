package distribuidos;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductoRepository {


    public ProductoRepository(){}


    public ProductoRepository(String codigo, String nombre, Double precio){
        this.codigo=codigo;
        this.nombre=nombre;
        this.precio=precio;
    }

    @Id
    private String codigo;

    private String nombre;

    private Double precio;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "PersonaRepository{" +
                "cedula='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + precio + '\'' +
                '}';
    }
}

