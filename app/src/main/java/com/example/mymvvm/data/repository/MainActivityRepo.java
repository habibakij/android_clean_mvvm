package com.example.mymvvm.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mymvvm.data.datasourse.local.PlaceHolderDao;
import com.example.mymvvm.data.datasourse.local.PlaceHolderDatabase;
import com.example.mymvvm.data.datasourse.local.PlaceHolderEntity;
import com.example.mymvvm.data.datasourse.remote.ApiClient;
import com.example.mymvvm.data.datasourse.remote.ApiInterface;
import com.example.mymvvm.data.model.PlaceHolderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityRepo {
    private static final String TAG = "DataRepository";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private PlaceHolderDao placeHolderDao;
    private MutableLiveData<List<PlaceHolderModel>> dataModel= new MutableLiveData<>();
    private LiveData<List<PlaceHolderEntity>> getAllPlaceHolder;

    public MainActivityRepo(Application application){
        PlaceHolderDatabase placeHolderDatabase= PlaceHolderDatabase.getInstance(application);
        placeHolderDao= placeHolderDatabase.placeHolderDao();
        getAllPlaceHolder= placeHolderDao.getAllPlaceHolderData();
    }

    public MutableLiveData<List<PlaceHolderModel>> fetchNetworkData() {
        Call<List<PlaceHolderModel>> call = apiInterface.getPlaceHolderData();
        call.enqueue(new Callback<List<PlaceHolderModel>>() {
            @Override
            public void onResponse(Call<List<PlaceHolderModel>> call, Response<List<PlaceHolderModel>> response) {
                dataModel.postValue(response.body());
                Log.d(TAG, "check_list: "+response.body().size());
                for (PlaceHolderModel model: response.body()) {
                    insertPlaceHolderData(new PlaceHolderEntity(
                            model.getId(), model.getUserId(), model.getTitle(), model.getCompleted().equals(false) ? "False" : "True"));
                }
            }
            @Override
            public void onFailure(Call<List<PlaceHolderModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return dataModel;
    }

    public LiveData<List<PlaceHolderEntity>> getAllPlaceHolderData(){
        return getAllPlaceHolder;
    }
    public void insertPlaceHolderData(PlaceHolderEntity entity){
        new InsertPlaceHolderAsyncTask(placeHolderDao).execute(entity);
    }
    public void updatePlaceHolderData(PlaceHolderEntity entity){
        new UpdatePlaceHolderAsyncTask(placeHolderDao).execute(entity);
    }
    public void deletePlaceHolderData(PlaceHolderEntity entity){
        new DeletePlaceHolderAsyncTask(placeHolderDao).execute(entity);
    }

    private static class InsertPlaceHolderAsyncTask extends AsyncTask<PlaceHolderEntity, Void, Void> {
        private PlaceHolderDao placeHolderDao;
        private InsertPlaceHolderAsyncTask(PlaceHolderDao placeHolderDao){
            this.placeHolderDao= placeHolderDao;
        }
        @Override
        protected Void doInBackground(PlaceHolderEntity... placeHolderEntities) {
            try {
                placeHolderDao.insert(placeHolderEntities[0]);
            } catch (Exception exception){
                Log.d(TAG, "doInBackground: "+exception.toString());
            }
            return null;
        }
    }
    private static class UpdatePlaceHolderAsyncTask extends AsyncTask<PlaceHolderEntity, Void, Void> {
        private PlaceHolderDao placeHolderDao;
        private UpdatePlaceHolderAsyncTask(PlaceHolderDao placeHolderDao){
            this.placeHolderDao= placeHolderDao;
        }
        @Override
        protected Void doInBackground(PlaceHolderEntity... placeHolderEntities) {
            try {
                placeHolderDao.update(placeHolderEntities[0]);
            } catch (Exception exception){
                exception.printStackTrace();
            }
            return null;
        }
    }
    private static class DeletePlaceHolderAsyncTask extends AsyncTask<PlaceHolderEntity, Void, Void> {
        private PlaceHolderDao placeHolderDao;
        private DeletePlaceHolderAsyncTask(PlaceHolderDao placeHolderDao){
            this.placeHolderDao= placeHolderDao;
        }
        @Override
        protected Void doInBackground(PlaceHolderEntity... placeHolderEntities) {
            try {
                placeHolderDao.delete(placeHolderEntities[0]);
            } catch (Exception exception){
                exception.printStackTrace();
            }
            return null;
        }
    }

}
