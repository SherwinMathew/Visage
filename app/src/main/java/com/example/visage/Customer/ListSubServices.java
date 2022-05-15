package com.example.visage.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListSubServices extends AppCompatActivity {
    
    ArrayList<String> body_care_list,facial_list,hair_care_list,hair_colour_list,hair_cut_list,makeover_list;
    ArrayList<String> arrayList;
    ListView listview;
    String s_category;
    TextView tv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sub_services);
        
        listview = findViewById(R.id.list_view_list_sub_services);
        tv=findViewById(R.id.textView9);

        Bundle bundle = getIntent().getExtras();
        s_category = bundle.getString("CAT");

        tv.setText("Select a sub service of "+s_category);

        body_care_list = new ArrayList<>();
        facial_list = new ArrayList<>();
        hair_care_list = new ArrayList<>();
        hair_colour_list = new ArrayList<>();
        hair_cut_list = new ArrayList<>();
        makeover_list = new ArrayList<>();


        if(s_category.equals("Body Care"))
        {
            set_body_care_data();
            arrayList = body_care_list;
            ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,body_care_list);
            listview.setAdapter(name_adapter);

        }
        else if(s_category.equals("Facial"))
        {
            set_facial_data();
            arrayList = facial_list;
            ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,facial_list);
            listview.setAdapter(name_adapter);
        }
        else if(s_category.equals("Hair Care"))
        {
            set_hair_care_data();
            arrayList = hair_care_list;
            ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,hair_care_list);
            listview.setAdapter(name_adapter);
        }
        else if(s_category.equals("Hair Cut"))
        {
            set_haircut_data();
            arrayList = hair_cut_list;
            ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,hair_cut_list);
            listview.setAdapter(name_adapter);
        }
        else if(s_category.equals("Hair Colour"))
        {
            set_hair_colour_data();
            arrayList = hair_colour_list;
            ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,hair_colour_list);
            listview.setAdapter(name_adapter);
        }
        else if(s_category.equals("Makeover"))
        {
            set_makeover_data();
            arrayList = makeover_list;
            ArrayAdapter<String> name_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,makeover_list);
            listview.setAdapter(name_adapter);
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String s_sub = arrayList.get(i);
                Toast.makeText(ListSubServices.this,s_sub, Toast.LENGTH_SHORT).show();

                Intent i2 = new Intent(ListSubServices.this,ListAvailableMerchants.class);
                i2.putExtra("CAT",s_category);
                i2.putExtra("SUB",s_sub);
                startActivity(i2);

            }
        });
        
    }

    public void set_body_care_data()
    {
        body_care_list.add("Body Spa");
        body_care_list.add("Body Polish");
        body_care_list.add("Ayurvedic Body Massage");
        body_care_list.add("Steam Bath");
        body_care_list.add("Nose Piercing");
        body_care_list.add("Ear Piercing");
        body_care_list.add("Warts Removal");
        body_care_list.add("Tattoo");
    }

    public void set_makeover_data()
    {
        makeover_list.add("Saree Draping");
        makeover_list.add("Party Make Up + Saree & Hair");
        makeover_list.add("Reception Makeover");
        makeover_list.add("Engagement Makeover");
        makeover_list.add("Bridal Makeover");
        makeover_list.add("Mehandi Designing");
    }

    public void set_haircut_data()
    {
        hair_cut_list.add("Kids");
        hair_cut_list.add("Gents");
        hair_cut_list.add("Beard Trimming");
        hair_cut_list.add("Baby Cut");
        hair_cut_list.add("Mushroom Cut");
        hair_cut_list.add("Bob Cut");
        hair_cut_list.add("V Cut");
        hair_cut_list.add("Deep-U");
        hair_cut_list.add("Square Cut");
        hair_cut_list.add("Layer Cut");
        hair_cut_list.add("Feather Cut ");
        hair_cut_list.add("Step Cut");
    }

    public void set_hair_colour_data()
    {

        hair_colour_list.add("Hair Colour (Gents)");
        hair_colour_list.add("Hair Colour Global");
        hair_colour_list.add("Hair Colour Highlighting Per Strand");
        hair_colour_list.add("Hair Colour Highlighting With Pre Light");
    }

    public void set_hair_care_data()
    {
        hair_care_list.add("Hair Wash & Dry");
        hair_care_list.add("Hair Wash & Curling");
        hair_care_list.add("Fiber Strong Spa");
        hair_care_list.add("Protein Treatment");
        hair_care_list.add("Keratin Treatment");
        hair_care_list.add("Straightening");
        hair_care_list.add("Smoothening Full Hair");
        hair_care_list.add("Permanent Blow Dry");
        hair_care_list.add("Hair Nourishing Treatment");
        hair_care_list.add("Dandruff Treatment");
        hair_care_list.add("Henna");
        hair_care_list.add("Colour Henna");

    }

    public void set_facial_data()
    {
        facial_list.add("Shahnaz Gold");
        facial_list.add("Cheryls Sensiglow");
        facial_list.add("Cheryls Oxyblast");
        facial_list.add("Shahnaz Pearl");
        facial_list.add("Serenite Charcol (For Oily Skin)");
        facial_list.add("Serenite Vit C");
        facial_list.add("Cheryls Tan Clear");
        facial_list.add("Cheryls Glow White");
        facial_list.add("Kaya Kalp Whitening");
        facial_list.add("Natures Pearl");
        facial_list.add("Natures Pappaya");
        facial_list.add("Natures Fruit (All Skin Type)");
    }
}