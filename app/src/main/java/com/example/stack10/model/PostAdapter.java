package com.example.stack10.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stack10.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Post> lista;

    public PostAdapter(Context context, ArrayList<Post> post) {
        this.context = context;
        this.lista = post;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Post post = lista.get(position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.posts, null);

        TextView autor = layout.findViewById(R.id.t1);
        TextView titulo = layout.findViewById(R.id.t2);
        autor.setText(post.getNomeUser());
        titulo.setText(post.getTitulo());

        return layout;
    }
}
