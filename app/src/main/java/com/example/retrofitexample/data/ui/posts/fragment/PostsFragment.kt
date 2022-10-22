package com.example.retrofitexample.data.ui.posts.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.retrofitexample.R
import com.example.retrofitexample.data.model.DataState
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.data.ui.loadingprogress.LoadingProgressBar
import com.example.retrofitexample.data.ui.posts.adapter.OnPostClickListener
import com.example.retrofitexample.data.ui.posts.adapter.PostsAdapter
import com.example.retrofitexample.data.ui.posts.viewmodel.PostViewEvent
import com.example.retrofitexample.data.ui.posts.viewmodel.PostsViewModel
import com.example.retrofitexample.databinding.FragmentPostsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(), OnPostClickListener {
    lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var binding: FragmentPostsBinding
    private val viewModel by viewModels<PostsViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentPostsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel    // Attach your view model here
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        loadingProgressBar = LoadingProgressBar(requireContext())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.postLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    it.data?.let { safeData ->
                        binding.rvPostsList.adapter = PostsAdapter(this@PostsFragment).apply {
                            submitList(safeData)
                        }
                    } ?: run {
                        Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Error -> {
                    loadingProgressBar.hide()
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }
            }
        }
        viewModel.eventStateLiveData.observe(viewLifecycleOwner){
            when (it) {
                is PostViewEvent.ShowMessage -> {}
                is PostViewEvent.NavigateToDetail -> {}
            }
        }
    }
    override fun onPostDetailClick(post: PostDTO,view: View) {
        post.id?.let { viewModel.getPostById(it) }
        navController.navigate(R.id.action_postsFragment_to_postDetailFragment, Bundle().apply {
            post.userId?.let { post.id?.let { it1 -> putInt("userId", it1) } }
        })
        Log.d("DENEME2", "GÖNDERİLEN: ${post.id}")

    }

    override fun onPostFavoriteClick(post: PostDTO) {
viewModel.onFavoritePost(post)
        }





}


