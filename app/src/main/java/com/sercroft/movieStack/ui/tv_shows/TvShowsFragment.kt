package com.sercroft.movieStack.ui.tv_shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sercroft.movieStack.data.model.EventObserver
import com.sercroft.movieStack.databinding.FragmentTvShowsBinding
import com.sercroft.movieStack.ui.BaseFragment
import com.sercroft.movieStack.util.extension.showSnackBar

class TvShowsFragment : BaseFragment(false) {

    private val viewModel: TvShowsViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentTvShowsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentTvShowsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@TvShowsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToTvShowEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToTvShowDetails(it.id, it.title) })
    }

    private fun navigateToTvShowDetails(tvShowId: Int, tvShowTitle: String) {
        val action = TvShowsFragmentDirections.actionNavigationTvShowsToTVShowDetailsFragment(
            tvShowId, tvShowTitle
        )
        findNavController().navigate(action)
    }
}