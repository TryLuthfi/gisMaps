package heuroix.myapps.com.heuroix.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import heuroix.myapps.com.heuroix.json.ContentFoto;
import heuroix.myapps.com.heuroix.R;
import heuroix.myapps.com.heuroix.adapter.StaggeredRecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private static final String URL_PRODUCTS = "https://heuroix.000webhostapp.com/contentpreviewrandom.php";
    Context context;
    private static final int NUM_COLUMNS = 3;
    List<ContentFoto> productList;
    RecyclerView recyclerView;
    View view;
    private ProgressBar loading;

    public FragmentTwo() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_two, container, false);
        productList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recylcerView);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter =
                new StaggeredRecyclerViewAdapter(getActivity(), productList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);

        loading = view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);



//        recyclerView = view.findViewById(R.id.recylcerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadProducts();

        return view;
    }

    private void loadProducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new ContentFoto(
                                        product.getString("id_content"),
                                        product.getString("id_user"),
                                        product.getString("judul"),
                                        product.getString("gambar"),
                                        product.getString("deskripsi"),
                                        product.getString("date_created"),
                                        product.getString("nama"),
                                        product.getString("email"),
                                        product.getString("userimage")
                                ));
                            }

                            StaggeredRecyclerViewAdapter adapter = new StaggeredRecyclerViewAdapter(getActivity(), productList);

                            if (adapter != null){
                                recyclerView.setAdapter(adapter);

                                loading.setVisibility(View.INVISIBLE);
                            }else {
                                Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                            }

//                            loading.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(stringRequest);
    }

}
