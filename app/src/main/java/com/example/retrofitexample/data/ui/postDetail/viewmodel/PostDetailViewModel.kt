package com.example.retrofitexample.data.ui.postDetail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.data.local.database.entity.PostEntity
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

    private val _eventStateLiveData = MutableLiveData<PostsViewModel>()
    val eventStateLiveData: LiveData<PostsViewModel>
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
    fun onFavoritePost(post: PostDTO) {
        DetailPostRepository.getPostByIdForFavorite(post.id ?: 0)?.let {
            DetailPostRepository.deleteFavoritePost(
                post.id.toString()
            )
            updateFavoriteState( post.id,false)
        } ?: kotlin.run {
            DetailPostRepository.insertFavoritePost(
                PostEntity(
                    postId = post.id.toString(),
                    postTitle = post.title,
                    postBody = post.body
                )
            )
            updateFavoriteState( post.id,true)
        }
    }
    private fun updateFavoriteState(id:Int?, isFavorite:Boolean){
        when(val current= _DetailPostLiveData.value){
            is DataState.Success -> {
                val currentList= current.data?.map {
                    if (it.id==id){
                        it.copy(isFavorite = isFavorite)

                    }
                    else it
                }
                _DetailPostLiveData.value=DataState.Success(currentList)
            }
            is DataState.Error -> {}
            is DataState.Loading -> {}

            null -> {}
        }
    }


}







