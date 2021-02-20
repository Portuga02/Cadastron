package com.et.missae.cadastron;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContatosAdapter extends BaseAdapter {

    private Context ctx;
    private List<Contato> lista;

    public ContatosAdapter(Context ctx2, List<Contato> lista2) {
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size(); /*retorno do banco de dados*/
    }

    @Override
    public Contato getItem(int position)
    {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.item_lista, null);
        } else {
            v = convertView;
        }

        Contato c = getItem(position);

        /*chamadas de elementos*/
        TextView itemNome = (TextView) v.findViewById(R.id.ItemNome);
        TextView itemTelefone = (TextView) v.findViewById(R.id.ItemTelefone);
        TextView itemEmail = (TextView) v.findViewById(R.id.ItemEmail);

        itemNome.setText(c.getNome());
        itemTelefone.setText(c.getTelefone());
        itemEmail.setText(c.getEmail());

        return v;
    }
}
