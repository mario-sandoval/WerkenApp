package cl.kimelti.werken.data.model;

public class EstadoVo extends AbstractVo {

    private String nombre;
    private String descripcion;
    private int codigo;

    public EstadoVo() {
    }

    public EstadoVo(Integer id, String nombre, String descripcion, int codigo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
