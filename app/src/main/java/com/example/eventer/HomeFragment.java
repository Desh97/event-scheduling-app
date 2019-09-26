package com.example.eventer;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {


    GridView gridView;
    String [] Cname = {"IT","Music","Art","Culture","Dancing","Politics","Social","Sports"};
    int[] Cimage = {R.drawable.it,R.drawable.music,R.drawable.art,R.drawable.culture,R.drawable.dancing,
            R.drawable.politics,R.drawable.social,R.drawable.sports};

   public HomeFragment(){

   }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gridView = (GridView)view.findViewById(R.id.grid_view);

        MainAdepter adepter =new MainAdepter(getActivity(),Cname,Cimage);
        gridView.setAdapter(adepter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        Intent intent = new Intent(getActivity(), MusicActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(), ArtActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(getActivity(), CultureActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(getActivity(), DancingActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(getActivity(), PoliticsActivity.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(getActivity(), SocialActivity.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(getActivity(), SportsActivity.class);
                        startActivity(intent7);
                        break;
                    case 0:
                        Intent intent8 = new Intent(getActivity(), ItActivity.class);
                        startActivity(intent8);
                        break;
                }
            }
        });

        return view;
    }
    public class MainAdepter extends BaseAdapter{

       private Context context;
       private LayoutInflater inflater;
       private String[] Cname;
       private int [] Cimage;

       public MainAdepter(Context c,String[] Cname,int[] Cimage){
           context = c;
           this.Cname = Cname;
           this.Cimage = Cimage;

       }

        @Override
        public int getCount() {
            return Cname.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           if(inflater==null){
               inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

           }
           if(view == null){
               view = inflater.inflate(R.layout.row_item,null);
           }
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            TextView textView = (TextView) view.findViewById(R.id.text_view);

            imageView.setImageResource(Cimage[i]);
            textView.setText(Cname[i]);
            return view;
        }
    }
}
