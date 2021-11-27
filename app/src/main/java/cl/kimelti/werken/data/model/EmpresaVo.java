package cl.kimelti.werken.data.model;

public class EmpresaVo extends AbstractVo {

    private String rut;
    private String nombre;
    private String direccion;
    private String comuna;
    private String fono;
    private String email;

    public EmpresaVo(){

    }
    public EmpresaVo(Integer id, String rut, String nombre, String direccion, String comuna, String fono, String email) {
        this.id = id;
        this.rut = rut;
        this.nombre = nombre;
        this.direccion = direccion;
        this.comuna = comuna;
        this.fono = fono;
        this.email = email;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getFono() {
        return fono;
    }
    public void setFono(String fono) {
        this.fono = fono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

}
