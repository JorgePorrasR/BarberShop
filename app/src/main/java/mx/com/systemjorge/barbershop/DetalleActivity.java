package mx.com.systemjorge.barbershop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalleActivity extends AppCompatActivity {
    TextView txtServicio;
    TextView txtDescripcion;
    TextView txtPrecio;
    Button btnAgregar;
    Servicio miservicio;
    String idCliente;
    FirebaseDatabase barber;
    DatabaseReference miReferencia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        txtServicio = findViewById(R.id.txtServicio);
        txtDescripcion = findViewById(R.id.txtdescripcion);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnAgregar = findViewById(R.id.btnAgregar);

        barber = FirebaseDatabase.getInstance();
        miReferencia = barber.getReference();

        String idServicio = getIntent().getExtras().getString("servicio");
        idCliente = getIntent().getExtras().getString("cliente");


        miReferencia.child("Servicios").child(idServicio).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                miservicio = snapshot.getValue(Servicio.class);
                txtServicio.setText(miservicio.id);
                txtDescripcion.setText(miservicio.descripcion);
                txtPrecio.setText("$"+miservicio.precio);
                setTitle("Detalle:"+miservicio.id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miReferencia.child("Clientes").child(idCliente).child("carrito").child(miservicio.id).setValue(true);
                finish();
            }
        });
    }
}
