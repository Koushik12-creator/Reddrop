package uk.ac.tees.mad.rd.mainapp.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.rd.mainapp.model.RequestModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel(){

    private val _addedRequest = MutableStateFlow(false)
    val addedRequest = _addedRequest.asStateFlow()

    private val _allRequests = MutableStateFlow<List<RequestModel>>(emptyList())
    val allRequests = _allRequests.asStateFlow()

    init {
        getAllRequests()
    }

    fun addRequests(request: RequestModel){
        viewModelScope.launch {
            firestore.collection("requests")
                .document()
                .set(request)
                .addOnSuccessListener {
                    _addedRequest.value = true
                    Log.i("The Request: ", "New Request is raised!!")
                }
                .addOnFailureListener{
                    _addedRequest.value = false
                    Log.i("The Request: ", "New Request is raised!!")
                }
        }
    }

    fun getAllRequests(){
        viewModelScope.launch {
            firestore.collection("requests")
                .get()
                .addOnSuccessListener {
                    _allRequests.value = it.toObjects(RequestModel::class.java)
                    Log.i("Getting all Requests", it.toString())
                }
                .addOnSuccessListener {
                    Log.i("Getting all Requests", "The Error ${it}")
                }
        }
    }
}