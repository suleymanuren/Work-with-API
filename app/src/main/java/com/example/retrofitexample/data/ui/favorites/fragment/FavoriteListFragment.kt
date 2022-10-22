package com.example.retrofitexample.data.ui.favorites.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.example.retrofitexample.data.model.PostDTO
import com.example.retrofitexample.data.ui.favorites.adapter.FavoritesAdapter
import com.example.retrofitexample.data.ui.favorites.adapter.OnFavoriteClickListener
import com.example.retrofitexample.data.ui.favorites.viewmodel.FavoritesViewModel
import com.example.retrofitexample.data.ui.loadingprogress.LoadingProgressBar
import com.example.retrofitexample.databinding.FragmentFavoriteListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteListFragment : Fragment(), OnFavoriteClickListener {
    lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var binding: FragmentFavoriteListBinding
    private val viewModel by viewModels<FavoritesViewModel>()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false).apply {
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar = LoadingProgressBar(requireContext())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.postLiveData.observe(viewLifecycleOwner) {
                binding.rvPostsList.adapter = FavoritesAdapter(this).apply {
                    submitList(it)
                }
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getFavoritePosts()
    }

    override fun onFavoriteClick(post: PostDTO) {
        viewModel.onFavoritePost(post)
    }
}


