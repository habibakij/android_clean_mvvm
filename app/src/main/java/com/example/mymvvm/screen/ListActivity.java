package com.example.mymvvm.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mymvvm.R;
import com.example.mymvvm.data.datasourse.local.PlaceHolderEntity;
import com.example.mymvvm.databinding.ActivityListBinding;
import com.example.mymvvm.databinding.ActivityMainBinding;
import com.example.mymvvm.viewModel.ListActivityViewModel;
import com.example.mymvvm.viewModel.MainActivityViewModel;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    ActivityListBinding activityListBinding;
    ListActivityViewModel listActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityListBinding = ActivityListBinding.inflate(getLayoutInflater());
        View view = activityListBinding.getRoot();
        setContentView(view);

        listActivityViewModel = new ViewModelProvider(this).get(ListActivityViewModel.class);

        Intent intent = this.getIntent();
        int uid= intent.getIntExtra("uid",0);
        int id= intent.getIntExtra("id",0);
        int userid= intent.getIntExtra("userid",0);
        String title= intent.getStringExtra("title");
        String status= intent.getStringExtra("status");
        Log.d(TAG, "check_intent: "+uid+", "+id+", "+userid+", "+title+", "+status);
        activityListBinding.edtUserId.setText(""+userid);
        activityListBinding.edtTitle.setText(title);
        activityListBinding.edtStatus.setText(status);


        activityListBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*listActivityViewModel.deletePlaceHolderData(new PlaceHolderEntity(
                        id,
                        Integer.parseInt(activityListBinding.edtUserId.getText().toString()),
                        activityListBinding.edtTitle.getText().toString(),
                        activityListBinding.edtStatus.getText().toString()
                ));*/

                PlaceHolderEntity entity= new PlaceHolderEntity(
                        id,
                        Integer.parseInt(activityListBinding.edtUserId.getText().toString()),
                        activityListBinding.edtTitle.getText().toString(),
                        activityListBinding.edtStatus.getText().toString()
                );
                entity.setUID(uid);
                listActivityViewModel.updatePlaceHolderData(entity);
                finish();

                /*Intent intent = new Intent();
                intent.putExtra("uid", uid);
                intent.putExtra("id", id);
                intent.putExtra("userid", Integer.parseInt(activityListBinding.edtUserId.getText().toString()));
                intent.putExtra("title", activityListBinding.edtTitle.getText().toString());
                intent.putExtra("status", activityListBinding.edtStatus.getText().toString());
                setResult(RESULT_OK, intent);
                finish();*/

            }
        });

    }
}