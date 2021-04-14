package mx.com.systemjorge.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class carrito_Activity extends AppCompatActivity {

    ListView lvCarrito;
    TextView txtTotal;
    ArrayList<Servicio> listaCarrito;
    ArrayAdapter<Servicio> adaptador;
    String idCliente;
    FirebaseDatabase barber;
    DatabaseReference miReferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito_);

        lvCarrito = findViewById(R.id.listCarrito);
        txtTotal = findViewById(R.id.total);
        setTitle("Carrito de Compras");

        idCliente = getIntent().getExtras().getString("clienteN");
        listaCarrito = new ArrayList<>();
        adaptador = new ArrayAdapter<Servicio>(carrito_Activity.this, android.R.layout.simple_list_item_1,
                listaCarrito);
        lvCarrito.setAdapter(adaptador);

        lvCarrito.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                miReferencia.child("Clientes").child(idCliente).child("carrito").child(listaCarrito.get(position).id).removeValue();
                return true;
            }
        });

        barber = FirebaseDatabase.getInstance();
        miReferencia = barber.getReference();

        miReferencia.child("Clientes").child(idCliente).child("carrito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCarrito.clear();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        String idServicio = item.getKey();
                        miReferencia.child("Servicios").child(idServicio).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Servicio miServicio = snapshot.getValue(Servicio.class);
                                listaCarrito.add(miServicio);
                                adaptador.notifyDataSetChanged();
                                definirTotal();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                }else{
                    definirTotal();
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void definirTotal() {
        double total=0;
        for(Servicio miServicio: listaCarrito){
            total = total+miServicio.precio;
        }
        txtTotal.setText("Total: $"+total);
    }
}