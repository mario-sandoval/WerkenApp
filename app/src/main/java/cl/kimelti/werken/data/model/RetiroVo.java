package cl.kimelti.werken.data.model;

import java.util.Date;

public class RetiroVo extends AbstractVo {

    private EmpresaVo empresa;
    private Date fecha;
    private String direccion;
    private String comuna;
    private String contacto;
    private String fono;
    private String observacion;
    private MensajeroVo mensajero;


    public EmpresaVo getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaVo empresa) {
        this.empresa = empresa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getFono() {
        return fono;
    }

    public void setFono(String fono) {
        this.fono = fono;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public MensajeroVo getMensajero() {
        return mensajero;
    }
    public void setMensajero(MensajeroVo mensajero) {
        this.mensajero = mensajero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RetiroVo other = (RetiroVo) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(empresa.getNombre());
        sb.append("\n");
        sb.append(direccion);
        sb.append("\n");
        sb.append(comuna);
        sb.append("\n");
        sb.append("Contacto: ");
        sb.append(contacto);
        return sb.toString();
    }
}
