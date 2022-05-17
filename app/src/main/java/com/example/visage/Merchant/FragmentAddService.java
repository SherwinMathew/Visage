package com.example.visage.Merchant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.visage.Customer.FData;
import com.example.visage.Customer.ListAvailableMerchants;
import com.example.visage.Customer.ServiceCategory;
import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FragmentAddService extends Fragment {

    TextInputEditText serviceRate,serviceInfo;
    Button addService;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Spinner service_cat,service_name;
    ArrayList<String> cat_list,name_list;
    ArrayList<String> body_care_list,facial_list,hair_care_list,hair_colour_list,hair_cut_list,makeover_list;
    String s_category,s_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_add_service, container, false);

        service_cat = v.findViewById(R.id.spinner_service_category);
        service_name = v.findViewById(R.id.spinner_service_name);
        serviceRate = v.findViewById(R.id.service_rate);
        serviceInfo = v.findViewById(R.id.service_info);
        addService = v.findViewById(R.id.addser_btn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        cat_list = new ArrayList<>();
        name_list = new ArrayList<>();

        body_care_list = new ArrayList<>();
        facial_list = new ArrayList<>();
        hair_care_list = new ArrayList<>();
        hair_colour_list = new ArrayList<>();
        hair_cut_list = new ArrayList<>();
        makeover_list = new ArrayList<>();

        cat_list.add("Body Care");
        cat_list.add("Facial");
        cat_list.add("Hair Care");
        cat_list.add("Hair Colour");
        cat_list.add("Hair Cut");
        cat_list.add("Makeover");

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,cat_list);
        service_cat.setAdapter(cat_adapter);

        service_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(cat_list.get(i).equals("Body Care"))
                {
                    set_body_care_data();
                    ArrayAdapter<String> name_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,body_care_list);
                    service_name.setAdapter(name_adapter);

                }
                else if(cat_list.get(i).equals("Facial"))
                {
                    set_facial_data();
                    ArrayAdapter<String> name_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,facial_list);
                    service_name.setAdapter(name_adapter);
                }
                else if(cat_list.get(i).equals("Hair Care"))
                {
                    set_hair_care_data();
                    ArrayAdapter<String> name_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,hair_care_list);
                    service_name.setAdapter(name_adapter);
                }
                else if(cat_list.get(i).equals("Hair Cut"))
                {
                    set_haircut_data();
                    ArrayAdapter<String> name_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,hair_cut_list);
                    service_name.setAdapter(name_adapter);
                }
                else if(cat_list.get(i).equals("Hair Colour"))
                {
                    set_hair_colour_data();
                    ArrayAdapter<String> name_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,hair_colour_list);
                    service_name.setAdapter(name_adapter);
                }
                else if(cat_list.get(i).equals("Makeover"))
                {
                    set_makeover_data();
                    ArrayAdapter<String> name_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,makeover_list);
                    service_name.setAdapter(name_adapter);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                s_category = service_cat.getSelectedItem().toString();
                s_name =  service_name.getSelectedItem().toString();

                String s_rate = serviceRate.getText().toString().trim();
                String s_info = serviceInfo.getText().toString().trim();

                String user_email = auth.getCurrentUser().getEmail();
               // String user_email = "brimstone@gmail.com";

                Services obj = new Services(s_rate,s_info);

                firestore.collection("MERCHANT").document(user_email)
                        .collection(s_category).document(s_name)
                        .set(obj)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    firestore.collection("SERVICES").document(s_category)
                                            .collection(s_category).document(s_name)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        ArrayList<String>details;
                                                        details = new ArrayList<>();
                                                        DocumentSnapshot doc_obj = task.getResult();

                                                       // Toast.makeText(getContext(), "task successful", Toast.LENGTH_SHORT).show();

                                                        if(doc_obj.contains("available_merchants"))
                                                        {
                                                            FData obj2 = doc_obj.toObject(FData.class);
                                                            details = obj2.getAvailable_merchants();
                                                            details.add(user_email+" @"+s_rate);
                                                            Map<String,Object> data = new HashMap<>();
                                                            data.put("available_merchants",details);
                                                            firestore.collection("SERVICES").document(s_category)
                                                                    .collection(s_category).document(s_name)
                                                                    .update(data)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                Toast.makeText(getContext(), "Service added successfully", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            else
                                                                            {
                                                                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }
                                                        else
                                                        {
                                                            FData fData  = new FData(details);
                                                            firestore.collection("SERVICES").document(s_category)
                                                                    .collection(s_category).document(s_name)
                                                                    .set(fData)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if(task.isSuccessful())
                                                                            {
                                                                                Toast.makeText(getContext(), "Service added successfully", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                            else
                                                                            {
                                                                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        }
                                                                    })
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });
                                                        }

                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                else
                                {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });

        return v;
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