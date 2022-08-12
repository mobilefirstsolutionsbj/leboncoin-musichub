package fr.leboncoin.musichub.presentation.ui.album_tracks

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.databinding.FragmentAlbumTracksBinding
import fr.leboncoin.musichub.domain.model.Album
import fr.leboncoin.musichub.presentation.base.BaseFragment
import fr.leboncoin.musichub.presentation.ui.albums.AlbumsFragment
import fr.leboncoin.musichub.presentation.ui.albums.AlbumsViewModel

@AndroidEntryPoint
class AlbumTracksFragment : BaseFragment() {

    private val viewModel: AlbumsViewModel by activityViewModels()

    private var album: Album? = null
    private var rootView: View? = null
    private var binding: FragmentAlbumTracksBinding? = null
    private var trackItemAdapter: TrackItemAdapter? = null

    private var mListState: Parcelable? = null
    private var savedInstanceState: Parcelable? = null
    override var progressBar: ProgressBar?
        get() = binding?.albumTracksFragmentProgressBar
        set(_) {}

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (rootView == null) {
            rootView = inflater.inflate(
                R.layout.fragment_album_tracks, container,
                false
            )
            rootView!!
        } else {
            rootView!!
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        mListState = savedInstanceState?.getParcelable(AlbumsFragment.LIST_STATE_KEY)
        binding?.albumTracksFragmentList?.let { recyclerView ->
            recyclerView.layoutManager?.onRestoreInstanceState(mListState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.albumTracksFragmentList?.let { recyclerView ->
            this.mListState = recyclerView.layoutManager?.onSaveInstanceState()
            outState.putParcelable(AlbumsFragment.LIST_STATE_KEY, mListState)
            this.savedInstanceState = outState
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity?.resources?.getBoolean(R.bool.isLandscape) == true) {
            navController.navigateUp()
        }
        binding?.let { binding ->
            checkEmptyState(binding)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = FragmentAlbumTracksBinding.bind(view)
        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_fragment_container
        )
        binding?.let { binding ->
            setupViews(binding)
            viewModel.selectedItem.observe(requireActivity()) { selectedAlbum ->
                trackItemAdapter?.updateDataSet(selectedAlbum.tracks)
                this.album = selectedAlbum
                checkEmptyState(binding)
            }
        }
    }

    private fun checkEmptyState(binding: FragmentAlbumTracksBinding) {
        binding.emptyView.visibility = if (this.album?.tracks?.isEmpty() == false) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun setupViews(binding: FragmentAlbumTracksBinding) {
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.albumTracksFragmentList.let { recyclerView ->
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            trackItemAdapter = TrackItemAdapter(this.requireActivity())
            recyclerView.adapter = trackItemAdapter
        }
    }
}
