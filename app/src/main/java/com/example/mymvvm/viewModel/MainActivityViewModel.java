package com.example.mymvvm.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.mymvvm.data.datasourse.local.PlaceHolderEntity;
import com.example.mymvvm.data.model.PlaceHolderModel;
import com.example.mymvvm.data.repository.MainActivityRepo;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "DataViewModel";
    MainActivityRepo placeHolderRepository;
    private MutableLiveData<List<PlaceHolderModel>> data = new MutableLiveData<>();
    private LiveData<List<PlaceHolderEntity>> getAllPlaceHolder = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        placeHolderRepository= new MainActivityRepo(application);
        getAllPlaceHolder= placeHolderRepository.getAllPlaceHolderData();
        fetchData();
    }

    public LiveData<List<PlaceHolderEntity>> getAllPlaceHolderData() {
        return getAllPlaceHolder;
    }
    public LiveData<List<PlaceHolderModel>> getAllData() {
        return data;
    }
    public void fetchData() {
        if (data != null) {
            MutableLiveData<List<PlaceHolderModel>> response = placeHolderRepository.fetchNetworkData();
            data = response;
        } else {
            Log.d(TAG, "fetchData_data: "+data);
        }
    }

    public void updatePlaceHolderData(PlaceHolderEntity entity){
        placeHolderRepository.updatePlaceHolderData(entity);
    }
    public void deletePlaceHolderData(PlaceHolderEntity entity){
        placeHolderRepository.deletePlaceHolderData(entity);
    }

    public void storeLocal(){
        getAllData().observe(getApplication(), new Observer<List<PlaceHolderModel>>() {
            @Override
            public void onChanged(List<PlaceHolderModel> dataModels) {
                Log.d(TAG, "check_list: "+dataModels.size());
                for (PlaceHolderModel model: dataModels) {
                    placeHolderRepository.insertPlaceHolderData(new PlaceHolderEntity(
                            model.getId(), model.getUserId(), model.getTitle(), model.getCompleted().equals(false) ? "False" : "True"));
                }
            }
        });
    }


}

