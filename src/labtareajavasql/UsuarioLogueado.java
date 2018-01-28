package labtareajavasql;

public class UsuarioLogueado {

    private static String Nombre;
 
    private static String Cargo;

    public UsuarioLogueado(String _nombre,  String _cargo) {
        this.Nombre = _nombre;      
        this.Cargo = _cargo;
    }

    public UsuarioLogueado() {

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

}
