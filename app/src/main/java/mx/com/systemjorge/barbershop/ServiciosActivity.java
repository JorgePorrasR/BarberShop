package mx.com.systemjorge.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import mx.com.systemjorge.barbershop.Providers.ClientProvider;

public class ServiciosActivity extends AppCompatActivity {
    TextView txtUsuario;
    TextView textView;
    Button botCarrito;
    ListView listaServicios;
    Cliente micliente;
    DatabaseReference miReferencia = FirebaseDatabase.getInstance().getReference("Clientes");
    DatabaseReference miReferencia2 = FirebaseDatabase.getInstance().getReference("Servicios");
    ArrayList<Servicio> listaServicios2;
    ArrayAdapter<Servicio> adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);


        String id = getIntent().getExtras().getString("cliente");
        DatabaseReference barber = miReferencia.child(id);

        //mClientProvider = new ClientProvider();
        //micliente = new Cliente();
        txtUsuario = findViewById(R.id.txtUser);
        botCarrito = findViewById(R.id.btnCarrito);
        listaServicios = (ListView) findViewById(R.id.lvlServicios);
        textView = findViewById(R.id.textView);
        listaServicios2 = new ArrayList<>();


        adaptador = new ArrayAdapter<Servicio>(ServiciosActivity.this, android.R.layout.simple_list_item_1, listaServicios2);
        listaServicios.setAdapter(adaptador);


        listaServicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ServiciosActivity.this, DetalleActivity.class);
                intent.putExtra("servicio", listaServicios2.get(position).id);
                intent.putExtra("cliente", micliente.id);
                startActivity(intent);
            }
        });
        miReferencia2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if(snapshot.hasChildren()){
                    listaServicios2.clear();
                    for(DataSnapshot dato: snapshot.getChildren()){
                        Servicio unServicio = dato.getValue(Servicio.class);
                        listaServicios2.add(unServicio);
                        adaptador.notifyDataSetChanged();
                        //Toast.makeText(ServiciosActivity.this,
                        //        unServicio.descripcion , Toast.LENGTH_LONG).show();
                        //Log.i ("info", adaptador.toString());
                        //textView.setText(listaServicios);

                    }

                //}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        barber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                micliente = snapshot.getValue(Cliente.class);
                    txtUsuario.setText(micliente.nombre);

                   if (micliente.carrito != null) {
                        botCarrito.setText(getString(R.string.ver_carrito) + " (" + micliente.carrito.size() + ")");
                    } else {
                        botCarrito.setText(getString(R.string.ver_carrito));
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        botCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencion = new Intent(ServiciosActivity.this,carrito_Activity.class);
                intencion.putExtra("clienteN",micliente.id);
                startActivity(intencion);
            }
        });
    }
}