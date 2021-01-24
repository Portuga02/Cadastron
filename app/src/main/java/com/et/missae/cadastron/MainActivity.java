    package com.et.missae.cadastron;

    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.google.gson.JsonObject;
    import com.koushikdutta.async.future.FutureCallback;
    import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editTelefone, editEmail, editId;
    Button btnNovo, btnSalvar, btnExcluir, btnEditar;
    ListView listViewContatos;

    private final String HostName = "https://192.168.1.11/contatos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome =(EditText) findViewById(R.id.editNome);

        editTelefone =(EditText) findViewById(R.id.editTelefone);

        editEmail =(EditText) findViewById(R.id.editEmail);

        editId =(EditText) findViewById(R.id.editId);

        btnNovo = (Button) findViewById(R.id.btnNovo);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
       btnExcluir = (Button) findViewById(R.id.btnExcluir);

        btnEditar=(Button) findViewById(R.id.btnEditar);

        listViewContatos = (ListView)  findViewById(R.id.listViewContatos);

        btnSalvar.setOnClickListener(v -> {
            String id = editId.getText().toString();
            String nome = editNome.getText().toString();
            String email = editEmail.getText().toString();
            String telefone = editTelefone.getText().toString();

            String url = HostName + "/create.php";
            if (nome.isEmpty()){
                editNome.setError("O nome está em branco por favor digite um nome no campo");
            } else if(id.isEmpty()){
                Ion.with(MainActivity.this)
                        .load(url)
                        .setBodyParameter("nome", nome)
                        .setBodyParameter( "telefone", telefone)
                        .setBodyParameter("email", email)
                        .asJsonObject()
                        .setCallback(( new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if(result.get("CREATE").getAsString().equals("OK")){
                                    int IdRetorno = Integer.parseInt(result.get("ID").getAsString());
                                    Toast.makeText(MainActivity.this,"Salvo com sucesso" + IdRetorno,
                                            Toast.LENGTH_LONG).show();


                                } else{
                                    Toast.makeText(MainActivity.this,"Ocorreu Algum erro ao salvar",Toast.LENGTH_LONG).show();
                                }
                            }
                        }));



            }
        });
    }
}