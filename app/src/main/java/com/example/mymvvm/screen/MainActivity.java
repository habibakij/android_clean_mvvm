package com.example.mymvvm.screen;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mymvvm.R;
import com.example.mymvvm.adapter.PlaceHolderAdapter;
import com.example.mymvvm.data.datasourse.local.PlaceHolderEntity;
import com.example.mymvvm.databinding.ActivityMainBinding;
import com.example.mymvvm.data.model.RecyclerItemClick;
import com.example.mymvvm.viewModel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClick {
    private static final String TAG = "MainActivity";
    private MainActivityViewModel mainActivityViewModel;
    PlaceHolderAdapter dataAdapter;
    ActivityMainBinding activityMainBinding;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    List<PlaceHolderEntity> entityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);
        setNavigationDrawer();

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getAllPlaceHolderData().observe(this, new Observer<List<PlaceHolderEntity>>() {
            @Override
            public void onChanged(List<PlaceHolderEntity> dataModels) {
                entityList= dataModels;
                dataAdapter= new PlaceHolderAdapter(MainActivity.this, dataModels, MainActivity.this, 1);
                activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                activityMainBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                activityMainBinding.recyclerView.setAdapter(dataAdapter);
                dataAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setNavigationDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        activityMainBinding.navToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.idNavGit) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                } else if (itemId == R.id.idNavPro) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                } else if (itemId == R.id.idNavUpdate) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null){
            int uid= data.getIntExtra("uid",0);
            int id= data.getIntExtra("id",0);
            int userid= data.getIntExtra("userid",0);
            String title= data.getStringExtra("title");
            String status= data.getStringExtra("status");
            Log.d(TAG, "check_main_intent: "+uid+", "+id+", "+userid+", "+title+", "+status);
            if (uid == 0){
                Toast.makeText(this, "not updated", Toast.LENGTH_LONG).show();
                return;
            }
            PlaceHolderEntity entity= new PlaceHolderEntity(id, userid, title, status);
            entity.setUID(uid);
            mainActivityViewModel.updatePlaceHolderData(entity);
            Toast.makeText(this, "Updated success", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        PlaceHolderEntity entity= entityList.get(position);
        Intent intent= new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("uid", entity.getUID());
        intent.putExtra("id", entity.getID());
        intent.putExtra("userid", entity.getUserID());
        intent.putExtra("title", entity.getTitle());
        intent.putExtra("status", entity.getStatus());
        startActivityForResult(intent, 100);
    }
}