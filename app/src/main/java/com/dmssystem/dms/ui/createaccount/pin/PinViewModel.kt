package com.dmssystem.dms.ui.createaccount.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmssystem.dms.data.remote.model.Pin
import com.dmssystem.dms.data.repository.MainRepository
import com.dmssystem.dms.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PinViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {

    fun updatePin(pin: Pin, userId: Int) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(repository.updateNewPin(pin, userId)))

        } catch (e: Exception) {

            emit(Resource.error(e.message ?: "Error occurred", data = null))
        }
    }
}