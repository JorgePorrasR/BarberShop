package mx.com.systemjorge.barbershop;

public class Servicio {
    public String descripcion;
    public String id;
    public int precio;

    public Servicio(){

    }

    public Servicio(String descripcion, String id, int precio) {
        this.descripcion = descripcion;
        this.id = id;
        this.precio = precio;
    }

    public String toString(){
        return this.descripcion+" - $"+this.precio;
    }
}
