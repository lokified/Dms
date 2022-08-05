package com.dmssystem.dms.ui.createaccount.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dmssystem.dms.data.repository.userRepository.UserRepository
import com.dmssystem.dms.data.local.model.User
import com.dmssystem.dms.data.repository.RegisterRepository
import com.dmssystem.dms.util.Event
import com.dmssystem.dms.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val registerRepository: RegisterRepository
) :ViewModel() {

    private val mToastMessage = MutableLiveData<Event<String>>()
    val toastMessage: LiveData<Event<String>> = mToastMessage

    fun saveUserDetails(user: User) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = registerRepository.postUserDetails(user)))
            userRepository.addUser(user)
        } catch (e: Exception) {

            emit(Resource.error(message = e.message ?: "Error occurred", data = null))
        }
    }
}