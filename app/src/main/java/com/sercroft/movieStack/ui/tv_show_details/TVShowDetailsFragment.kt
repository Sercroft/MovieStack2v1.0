package com.sercroft.movieStack.ui.tv_show_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sercroft.movieStack.data.db.remote.TheMovieDatabaseAPI
import com.sercroft.movieStack.data.model.EventObserver
import com.sercroft.movieStack.databinding.FragmentTvShowDetailsBinding
import com.sercroft.movieStack.ui.BaseFragment
import com.sercroft.movieStack.util.extension.openUrl
import com.sercroft.movieStack.util.extension.showSnackBar

class TvShowDetailsFragment : BaseFragment(true) {

    private val args: TvShowDetailsFragmentArgs by navArgs()
    private val viewModel: TVShowDetailsViewModel by viewModels { TVShowDetailsViewModelFactory(args.tvShowIdArg) }
    private lateinit var viewDataBinding: FragmentTvShowDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentTvShowDetailsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@TvShowDetailsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToCastDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToPersonDetails(it.id, it.name) })
        viewModel.goToVideoEvent.observe(
            viewLifecycleOwner,
            EventObserver { openUrl(TheMovieDatabaseAPI.getYoutubeWatchUrl(it.key)) })
    }

    private fun navigateToPersonDetails(personId: Int, personName: String) {
        val action =
            TvShowDetailsFragmentDirections.actionTvShowDetailsFragmentToPersonDetailsFragment(
                personId,
                personName
            )
        findNavController().navigate(action)
    }
}