package com.example.djangopro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RestApiRCVA.OnItemClickListener {
    private int INTERNET_PERMISSION_CODE = 1;
    private static final String ROOT_URL = "http://192.168.43.87:5000/api_posts";
    private static final String REGISTER = "http://192.168.43.87/Android/v1/user.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<ForRest> forRestArrayList = new ArrayList<>();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.profName);


        requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ROOT_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("List of Posts");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String author = (String) jsonObject.getString("author");
                                String title = (String) jsonObject.getString("title");
                                String post = (String) jsonObject.getString("post");
                                String creationDate = (String) jsonObject.getString("creationDate");
                                forRestArrayList.add(new ForRest(title, post, author, creationDate));
                            }
                            RestApiRCVA restApiRCVA = new RestApiRCVA(MainActivity.this, forRestArrayList);
                            recyclerView.setAdapter(restApiRCVA);
                            LinearLayoutManager schoolslayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                            recyclerView.setLayoutManager(schoolslayoutManager);
                            restApiRCVA.setOnRCVItemClickListener(MainActivity.this);


                        } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    profName.setText(""+e);
                            Toast.makeText(MainActivity.this, "error ocurred" + e, Toast.LENGTH_SHORT).show();
                            System.out.println(e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Sorry"+error, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, Addpost.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void OnClick(int position) {
        Intent intent = new Intent(this, Addpost.class);
        startActivity(intent);

    }
}
