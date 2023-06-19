package com.example.mymvvm.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mymvvm.data.datasourse.local.PlaceHolderEntity;
import com.example.mymvvm.data.repository.MainActivityRepo;

public class ListActivityViewModel extends AndroidViewModel {

    MainActivityRepo placeHolderRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        placeHolderRepository= new MainActivityRepo(application);
    }

    public void updatePlaceHolderData(PlaceHolderEntity entity){
        placeHolderRepository.updatePlaceHolderData(entity);
    }
    public void deletePlaceHolderData(PlaceHolderEntity entity){
        placeHolderRepository.deletePlaceHolderData(entity);
    }
}
