package com.et.missae.cadastron;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContatosAdpter extends BaseAdapter {

    private Context ctx;
    private List<Contato> Listar;

    public ContatosAdpter(Context ctx2, List<Contato> Listar2) {
        ctx = ctx2;
        Listar = Listar2;
    }

    @Override
    public int getCount() {
        return Listar.size(); /*retorno do banco de dados*/
    }

    @Override
    public Contato getItem(int position) {
        return Listar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View V = null;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
            V = inflater.inflate(R.layout.item_lista, null);
        } else {
            V = convertView;
        }
        Contato C = getItem(position);
        /*chamadas de elementos*/
        TextView itemNome = (TextView) V.findViewById(R.id.ItemNome);
        TextView itemTelefone = (TextView) V.findViewById(R.id.ItemTelefone);
        TextView itemEmail = (TextView) V.findViewById(R.id.ItemEmail);

        itemNome.setText(C.getNome());
        itemTelefone.setText(C.getTelefone());
        itemEmail.setText(C.getEmail());
        return V;
    }
}
