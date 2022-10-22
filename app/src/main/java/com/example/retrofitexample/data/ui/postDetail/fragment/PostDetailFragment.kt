package com.example.retrofitexample.data.ui.postDetail.fragment

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
import com.example.retrofitexample.data.model.DataState
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.data.ui.loadingprogress.LoadingProgressBar
import com.example.retrofitexample.data.ui.postDetail.adapter.OnPostLikeClickListener
import com.example.retrofitexample.data.ui.postDetail.viewmodel.PostDetailViewModel
import com.example.retrofitexample.data.ui.postDetail.adapter.PostDetailAdapter
import com.example.retrofitexample.databinding.FragmentPostDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
 class PostDetailFragment : Fragment() , OnPostLikeClickListener {
 private lateinit var binding : FragmentPostDetailBinding
    private lateinit var navController: NavController
    private val viewHolderModel by viewModels<PostDetailViewModel>()
    lateinit var loadingProgressBar: LoadingProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewHolderModel = viewHolderModel    // Attach your view model here
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewHolderModel = viewHolderModel
        loadingProgressBar = LoadingProgressBar(requireContext())
        viewHolderModel.DetailPostLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    Log.d("DENEME2", "FRAGMENT GELEN ${it.data}")
                    loadingProgressBar.hide()
                    it.data?.let { safeData ->
                        binding.rvPostsList.adapter = PostDetailAdapter(this@PostDetailFragment,this@PostDetailFragment).apply {
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
        }}

    override fun onDetailFavoriteClick(post: PostDTO) {
        viewHolderModel.onFavoritePost(post)
    }


}