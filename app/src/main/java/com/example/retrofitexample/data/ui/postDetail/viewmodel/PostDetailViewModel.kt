package com.example.retrofitexample.data.ui.postDetail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.data.model.*
import com.example.retrofitexample.data.repository.PostRepository
import com.example.retrofitexample.data.ui.posts.viewmodel.PostsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class PostDetailViewModel @Inject constructor(private val DetailPostRepository: PostRepository, savedStateHandle: SavedStateHandle) : ViewModel() {
    private var _DetailPostLiveData = MutableLiveData<DataState<List<PostDTO>?>>()
    val DetailPostLiveData: LiveData<DataState<List<PostDTO>?>>
        get() = _DetailPostLiveData

    private val _eventStateLiveData = MutableLiveData<PostsViewModel.PostViewEvent>()
    val eventStateLiveData: LiveData<PostsViewModel.PostViewEvent>
        get() = _eventStateLiveData

    init {
        savedStateHandle.get<Int>("userId")?.let {
            Log.d("DENEME2", "MODEL GELEN ${it}")

        }
        val postId = savedStateHandle.get<Int>("userId")
        getPostDetailById(postId)


    }
    private fun getPostDetailById(postId: Int?){
        _DetailPostLiveData.postValue(DataState.Loading())
        if (postId != null) {
            DetailPostRepository.getPostById(postId).enqueue(object : Callback<Post> {
                override fun onResponse(call: Call<Post>, response: Response<Post>) {
                    if (response.isSuccessful) {
                        response.body()?.let {

                            _DetailPostLiveData.postValue(DataState.Success(listOf(PostDTO(
                                id = it.id,
                                title = it.title,
                                body = it.body,
                                userId = it.userId,
                                isFavorite = isExists(it.id)
                            ))))

                        } ?: kotlin.run {
                            _DetailPostLiveData.postValue(DataState.Error("Data Empty"))
                        }
                    } else {
                        _DetailPostLiveData.postValue(DataState.Error(response.message()))
                    }
                }

                override fun onFailure(call: Call<Post>, t: Throwable) {
                    _DetailPostLiveData.postValue(t.message?.let { DataState.Error(it) })
                }
            })
        }
    }
    private fun isExists(postId: Int?): Boolean {
        postId?.let {
            DetailPostRepository.getPostById(it)?.let {
                return true
            }
        }
        return false
    }


}







