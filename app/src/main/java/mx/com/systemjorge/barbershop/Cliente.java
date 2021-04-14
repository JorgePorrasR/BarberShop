package mx.com.systemjorge.barbershop;

import java.util.HashMap;

public class Cliente {
    public String id;
    public String usuario;
    public String nombre;
    public boolean frecuente;
    public HashMap<String, Boolean> carrito;

    public Cliente(){

    }
    public Cliente(String id,String usuario,String nombre){
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        frecuente = false;
        this.carrito = new HashMap<>();

    }



}
