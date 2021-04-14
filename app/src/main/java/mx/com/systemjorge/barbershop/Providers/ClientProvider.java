package mx.com.systemjorge.barbershop.Providers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientProvider {
    private DatabaseReference mDatabase;

    public ClientProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Clientes");
    }

    public DatabaseReference getClientes() {
        return mDatabase;
    }
}
