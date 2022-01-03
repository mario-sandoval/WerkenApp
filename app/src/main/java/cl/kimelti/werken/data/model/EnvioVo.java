package cl.kimelti.werken.data.model;

import java.util.Date;

public class EnvioVo extends AbstractVo {

    private Integer folio;
    private OrdenVo ordenTrabajo;
    private String numeroTracking;
    private String nombreDestinatario;
    private String direccion;
    private String comuna;
    private String fono;
    private Date fechaCreacion;
    private Date fechaEntrega;
    private String comentario;
    private EstadoVo estado;
    private EmpresaVo empresa;
    private String emailDestinatario;
    private String codigo;
    private String tipo;
    private Integer cantidadBultos;
    private String recibidoPor;
    private String parentesco;
    private Boolean recibeTitular;
    private byte[] evidencia;
    private MensajeroVo mensajero;
    private String errorCarga;

    public EnvioVo() {
    }

    public EnvioVo(Integer id, Integer folio, OrdenVo ordenTrabajo, String numeroTracking, String nombreDestinatario, String direccion, String comuna, String fono, Date fechaCreacion, Date fechaEntrega, String comentario, EstadoVo estado, EmpresaVo empresa, String emailDestinatario, String codigo, String tipo, Integer cantidadBultos, String recibidoPor, String parentesco, Boolean recibeTitular, MensajeroVo mensajero) {
        this.id = id;
        this.folio = folio;
        this.ordenTrabajo = ordenTrabajo;
        this.numeroTracking = numeroTracking;
        this.nombreDestinatario = nombreDestinatario;
        this.direccion = direccion;
        this.comuna = comuna;
        this.fono = fono;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntrega = fechaEntrega;
        this.comentario = comentario;
        this.estado = estado;
        this.empresa = empresa;
        this.emailDestinatario = emailDestinatario;
        this.codigo = codigo;
        this.tipo = tipo;
        this.cantidadBultos = cantidadBultos;
        this.recibidoPor = recibidoPor;
        this.parentesco = parentesco;
        this.recibeTitular = recibeTitular;
        this.mensajero = mensajero;
    }

    public Integer getFolio() {
        return folio;
    }
    public void setFolio(Integer folio) {
        this.folio = folio;
    }
    public OrdenVo getOrdenTrabajo() {
        return ordenTrabajo;
    }
    public void setOrdenTrabajo(OrdenVo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }
    public String getNumeroTracking() {
        return numeroTracking;
    }
    public void setNumeroTracking(String numeroTracking) {
        this.numeroTracking = numeroTracking;
    }
    public String getNombreDestinatario() {
        return nombreDestinatario;
    }
    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getComuna() {
        return comuna;
    }
    public void setComuna(String comuna) {
        this.comuna = comuna;
    }
    public String getFono() {
        return fono;
    }
    public void setFono(String fono) {
        this.fono = fono;
    }
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public Date getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public EstadoVo getEstado() {
        return estado;
    }
    public void setEstado(EstadoVo estado) {
        this.estado = estado;
    }

    public EmpresaVo getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaVo empresa) {
        this.empresa = empresa;
    }

    public String getEmailDestinatario() {
        return emailDestinatario;
    }
    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }
    public byte[] getEvidencia() {
        return evidencia;
    }
    public void setEvidencia(byte[] evidencia) {
        this.evidencia = evidencia;
    }

    public MensajeroVo getMensajero() {
        return mensajero;
    }

    public void setMensajero(MensajeroVo mensajero) {
        this.mensajero = mensajero;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getCantidadBultos() {
        return cantidadBultos;
    }
    public void setCantidadBultos(Integer cantidadBultos) {
        this.cantidadBultos = cantidadBultos;
    }

    public String getRecibidoPor() {
        return recibidoPor;
    }
    public void setRecibidoPor(String recibidoPor) {
        this.recibidoPor = recibidoPor;
    }
    public String getParentesco() {
        return parentesco;
    }
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
    public Boolean getRecibeTitular() {
        return recibeTitular;
    }
    public void setRecibeTitular(Boolean recibe_titular) {
        this.recibeTitular = recibe_titular;
    }
    public String getErrorCarga() {
        return errorCarga;
    }
    public void setErrorCarga(String errorCarga) {
        this.errorCarga = errorCarga;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(nombreDestinatario);
        sb.append("\n");
        sb.append(direccion);
        sb.append("\n");
        sb.append(comuna);
        sb.append("\n");
        sb.append(fono);
        sb.append("\n");
        sb.append("Fecha: ");
        sb.append(fechaCreacion);
        sb.append("\n");
        sb.append("Remitente: ");
        sb.append(empresa.getNombre());
        return sb.toString();
    }

}
