package mx.com.systemjorge.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase barber;
    DatabaseReference miReferencia;

    ArrayList<Cliente> listaClientes;
    EditText edtUsuario;
    Button btnIngresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        barber = FirebaseDatabase.getInstance();
        miReferencia = barber.getReference();

        listaClientes = new ArrayList<>();
        edtUsuario = findViewById(R.id.edtUser);
        btnIngresa = findViewById(R.id.btnIngresar);

        miReferencia.child("Clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    listaClientes.clear();
                    for (DataSnapshot dato:snapshot.getChildren()) {
                        Cliente unCliente = dato.getValue(Cliente.class);
                        listaClientes.add(unCliente);
                        Toast.makeText(MainActivity.this,
                                "Cliente: " + unCliente.nombre, Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsuario.getText().toString();
                boolean correcto=false;
                for (Cliente miCliente: listaClientes){
                    if (user.equalsIgnoreCase(miCliente.usuario)) {
                        correcto=true;
                        //Toast.makeText(MainActivity.this, "Is Correct"+miCliente.nombre,Toast.LENGTH_LONG).show();
                        Intent intento = new Intent(MainActivity.this, ServiciosActivity.class);
                        intento.putExtra("cliente", miCliente.id);
                        startActivity(intento);
                    }
                }
                if(correcto==false){
                    Toast.makeText(MainActivity.this, "Is not Correct",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
        //Cliente gelipe = new Cliente("2","gelipe","felipe");
        //miReferencia.child("Clientes").child(gelipe.id).setValue(gelipe);
    }

