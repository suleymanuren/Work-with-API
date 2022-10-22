package com.example.retrofitexample.data.ui.users.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.retrofitexample.data.model.DataState
import com.example.retrofitexample.data.ui.loadingprogress.LoadingProgressBar
import com.example.retrofitexample.data.ui.users.viewmodel.UserViewEvent
import com.example.retrofitexample.data.ui.users.viewmodel.UserViewModel
import com.example.retrofitexample.databinding.FragmentUserBinding
import com.example.retrofitexample.data.ui.users.adapter.UserAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var binding: FragmentUserBinding
    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar = LoadingProgressBar(requireContext())

        binding.userViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    it.data?.let { safeData ->
                        binding.rvUserList.adapter = UserAdapter(this@UserFragment).apply {
                            submitList(safeData)
                        }
                    } ?: run {
                        Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Error -> {
                    Log.d("deneme2", "onViewCreated: ${it.message}")
                    loadingProgressBar.hide()
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }
            }
        }

        viewModel.eventStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is UserViewEvent.ShowMessage -> {}
                is UserViewEvent.NavigateToDetail -> {}
                else -> {}
            }
        }


    }


}

/*
@BindingAdapter("app:postList")
fun setPostList(recyclerView: RecyclerView, postList: List<Post>?) {
    postList?.let {
        recyclerView.adapter = PostsAdapter().apply {
            submitList(it)
        }
    }
}
 */
