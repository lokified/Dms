package com.dmssystem.dms.ui.createaccount.security

import androidx.lifecycle.*
import com.dmssystem.dms.data.remote.model.Security
import com.dmssystem.dms.data.remote.model.SecurityAnswer
import com.dmssystem.dms.data.repository.RegisterRepository
import com.dmssystem.dms.util.Event
import com.dmssystem.dms.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SecurityViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
): ViewModel() {

    private val _securityQuestions = MutableLiveData<List<Security>?>()
    val securityQuestions: LiveData<List<Security>?> = _securityQuestions

    private val _errorText = MutableLiveData<Event<String>>()
    val errorText : LiveData<Event<String>> = _errorText

    init {
        loadQuestions()
    }

    private fun loadQuestions() {

        viewModelScope.launch(Dispatchers.IO) {

            val questions = registerRepository.getSecurityQuestions { _errorText.postValue(Event(it)) }

            _securityQuestions.postValue(questions)
        }
    }

    fun postAnswers(answer: SecurityAnswer) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {

            emit(Resource.success(data = registerRepository.postSecurityAnswers(answer)))
        }catch (exception: Exception) {

            emit(Resource.error(message = exception.message?: "Error occurred", data = null))
        }
    }
}