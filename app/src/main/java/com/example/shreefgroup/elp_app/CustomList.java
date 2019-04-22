package com.example.shreefgroup.elp_app;

/**
 * Created by ashfaq on 11/18/2016.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
   // private Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, int select_dialog_singlechoice) {
        super(context, R.layout.list_singl, web);
        this.context = context;
        this.web = web;
      //  this.imageId = imageId;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_singl, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

       // ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}