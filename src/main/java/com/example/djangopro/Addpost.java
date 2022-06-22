package com.example.djangopro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Addpost extends AppCompatActivity {
    private static final String ROOT_URL_POST = "http://192.168.43.87:5000/add_api_posts".replaceAll(" ", "%20");
    Button send;
    EditText postauthor, posttitle, postsubtitle, postpost;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        send = (Button) findViewById(R.id.sendpost);
        postauthor = (EditText) findViewById(R.id.postauthorlayout);
        posttitle = (EditText) findViewById(R.id.posttitlelayout);
        postsubtitle = (EditText) findViewById(R.id.postsubtitlelayout);
        postpost = (EditText) findViewById(R.id.postpostlayout);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String author = postauthor.getText().toString();
                String title = posttitle.getText().toString();
                String subtitle = postsubtitle.getText().toString();
                String post = postpost.getText().toString();
                posting(author, title, subtitle, post);


            }
        });
    }

    private void posting(final String author, final String title, final String subtitle, final String post) {

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("author", author);
            parameters.put("title", title);
            parameters.put("subtitle", subtitle);
            parameters.put("post", post);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, ROOT_URL_POST, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        requestQueue.add(jsonObjReq);
    }
}
