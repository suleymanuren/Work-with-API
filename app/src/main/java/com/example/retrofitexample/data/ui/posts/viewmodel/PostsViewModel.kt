package com.example.retrofitexample.data.ui.posts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitexample.data.local.database.entity.PostEntity
import com.example.retrofitexample.data.model.DataState
import com.example.retrofitexample.data.model.Post
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {
    private var _postLiveData = MutableLiveData<DataState<List<PostDTO>?>>()
    val postLiveData: LiveData<DataState<List<PostDTO>?>>
        get() = _postLiveData

    private val _eventStateLiveData = MutableLiveData<PostViewEvent>()
    val eventStateLiveData: LiveData<PostViewEvent>
        get() = _eventStateLiveData
    private var _postCacheData = MutableLiveData<List<PostDTO>?>()
    private val postCacheData: LiveData<List<PostDTO>?>
        get() = _postCacheData
    init {
        getPosts()
    }

    private fun getPosts() {
        _postLiveData.postValue(DataState.Loading())
        postRepository.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let {

                        _postLiveData.postValue(DataState.Success(it.map { safePost ->
                            PostDTO(
                                id = safePost.id,
                                title = safePost.title,
                                body = safePost.body,
                                userId = safePost.userId,
                                isFavorite = isExists(safePost.id)
                            )
                        }))

                    } ?: kotlin.run {
                        _postLiveData.postValue(DataState.Error("Data Empty"))
                    }
                } else {
                    _postLiveData.postValue(DataState.Error(response.message()))
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                _postLiveData.postValue(DataState.Error(t.message.toString()))
                _eventStateLiveData.postValue(PostViewEvent.ShowMessage(t.message.toString()))
            }
        })
    }

    fun onFavoritePost(post: PostDTO) {
        postRepository.getPostByIdForFavorite(post.id ?: 0)?.let {
            postRepository.deleteFavoritePost(
                post.id.toString()
            )
            updateFavoriteState( post.id,false)
        } ?: kotlin.run {
            postRepository.insertFavoritePost(
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
        when(val current= _postLiveData.value){
            is DataState.Success -> {
                val currentList= current.data?.map {
                    if (it.id==id){
                        it.copy(isFavorite = isFavorite)

                    }
                    else it
                }
                _postLiveData.value=DataState.Success(currentList)
            }
            is DataState.Error -> {}
            is DataState.Loading -> {}

            null -> {}
        }
    }
    private fun isExists(postId: Int?): Boolean {
        postId?.let {
            postRepository.getPostById(it)?.let {
                return false
            }
        }
        return true
    }

    fun getPostById(id: Int): PostDTO? {
        return postCacheData.value?.find { it.id == id }
    }
}

sealed class PostViewEvent {
    object NavigateToDetail : PostViewEvent()
    class ShowMessage(val message: String?) : PostViewEvent()
}