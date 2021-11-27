package cl.kimelti.werken.data.model;

public class MensajeroVo extends AbstractVo {

    private String nombre;
    private String password;
    private String login;
    private String fono;
    private Boolean admin;

    public MensajeroVo(){

    }
    public MensajeroVo(int id, String nombre, String password, String login, String fono, boolean admin) {
        this.id = this.id;
        this.nombre = this.nombre;
        this.password = this.password;
        this.login = this.login;
        this.fono = this.fono;
        this.admin = this.admin;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getFono() {
        return fono;
    }
    public void setFono(String fono) {
        this.fono = fono;
    }

    public Boolean getAdmin() {
        return admin;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

}
