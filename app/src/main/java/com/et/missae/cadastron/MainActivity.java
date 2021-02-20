package com.et.missae.cadastron;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editTelefone, editEmail, editId;
    Button btnNovo, btnSalvar, btnExcluir, btnEditar;
    ListView listViewContatos;

    private final String HOST = "http://192.168.1.29/contatos";  // lembrar se for servidor local colocar apenas http e não HTTPS

    ContatosAdapter contatosAdapter;
    List<Contato> lista;


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editTelefone = (EditText) findViewById(R.id.editTelefone);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editId = (EditText) findViewById(R.id.editId);

        btnNovo = (Button) findViewById(R.id.btnNovo);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnEditar = (Button) findViewById(R.id.btnEditar);

        listViewContatos = (ListView) findViewById(R.id.listViewContatos);

        lista = new ArrayList<Contato>();
        contatosAdapter = new ContatosAdapter(MainActivity.this, lista);

        listViewContatos.setAdapter(contatosAdapter);

        btnSalvar.setOnClickListener(v -> {
            String id = editId.getText().toString();
            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String telefone = editTelefone.getText().toString();

            String url = HOST + "/create.php";
            if (nome.isEmpty()) {
                editNome.setError("O nome está em branco por favor digite um nome no campo");
            } else if (id.isEmpty()) {

                Ion.with(MainActivity.this)
                        .load(url)
                        .setBodyParameter("nome", nome)
                        .setBodyParameter("telefone", telefone)
                        .setBodyParameter("email", email)
                        .asJsonObject()
                        .setCallback((new FutureCallback<JsonObject>() {

                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    if (result.get("CREATE").getAsString().equals("OK")) {
                                        int idRetornado = Integer.parseInt(result.get("ID").getAsString());
                                        Toast.makeText(MainActivity.this,
                                                "O Contato foi salvo com sucesso utilizando o  ID(" + idRetornado + ")",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this,
                                                "Ocorreu um erro ao salvar",
                                                Toast.LENGTH_LONG).show();

                                    }
                                } catch (OutOfMemoryError e1) {
                                    e1.printStackTrace();
                                    Log.e("Memory exceptions", "exceptions" + e1);

                                }
                            }
                        }));
            } else {

            }
        });
    }

    public void limpaCampos() {
        editId.setText("");
        editNome.setText("");
        editTelefone.setText("");
        editEmail.setText("");
        editNome.requestFocus();
    }

    private void listaContatos() {
        String url = HOST + "/read.php";

        Ion.with(getBaseContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {

                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        for (int i = 0; i < result.size(); i++) {
                            JsonObject objeto = result.get(i).getAsJsonObject();
                            Contato c = new Contato();

                            c.setId(objeto.get("id").getAsInt());
                            c.setNome(objeto.get("Nome").getAsString());
                            c.setTelefone(objeto.get("Telefone").getAsString());
                            c.setEmail(objeto.get("Email").getAsString());

                            lista.add(c);

                        }
                        contatosAdapter.notifyDataSetChanged(); // Funçãoo para ve se tem alterações na listanotificações na lista
                    }
                });
    }
}