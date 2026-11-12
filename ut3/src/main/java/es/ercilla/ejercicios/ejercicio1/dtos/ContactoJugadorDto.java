package es.ercilla.ejercicios.ejercicio1.dtos;

public class ContactoJugadorDto {

    private String alias;
    private String telefono;

    public ContactoJugadorDto(){

    }

    public ContactoJugadorDto(String alias, String telefono) {
        this.alias = alias;
        if(telefono == null){
            this.telefono = "Sin teléfono.";
        }else{
            this.telefono = telefono;
        }
        
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "ContactoJugadorDto [alias=" + alias + ", telefono=" + telefono + "]";
    }
    
}
