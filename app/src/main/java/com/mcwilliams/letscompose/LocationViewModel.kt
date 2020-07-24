package com.mcwilliams.letscompose

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcwilliams.letscompose.model.citydata.CityDataByLatLong
import com.mcwilliams.letscompose.network.LocationApi
import kotlinx.coroutines.launch

class LocationViewModel @ViewModelInject constructor(private val locationApi: LocationApi) : ViewModel() {

    var _cityData = MutableLiveData<CityDataByLatLong>()
    var cityData : LiveData<CityDataByLatLong> = _cityData

    init {
        viewModelScope.launch {
            _cityData.postValue(locationApi.getLatLongByCity("Boerne"))
        }
    }

}