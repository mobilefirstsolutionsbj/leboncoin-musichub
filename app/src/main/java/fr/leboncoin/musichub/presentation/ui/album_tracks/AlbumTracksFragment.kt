package fr.leboncoin.musichub.presentation.ui.album_tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.databinding.FragmentAlbumTracksBinding
import fr.leboncoin.musichub.domain.model.Album
import fr.leboncoin.musichub.domain.model.Track
import fr.leboncoin.musichub.presentation.base.BaseFragment
import fr.leboncoin.musichub.presentation.ui.albums.AlbumsFragment


@AndroidEntryPoint
class AlbumTracksFragment : BaseFragment() {

    companion object {
        const val ALBUM_TRACKS = "ALBUM_TRACKS"
    }

    private lateinit var navController: NavController
    private val albumTracks: ArrayList<Track> = arrayListOf()
    private var rootView: View? = null
    private var binding: FragmentAlbumTracksBinding? = null
    private var trackItemAdapter: TrackItemAdapter? = null

    override var progressBar: ProgressBar?
        get() = binding?.albumTracksFragmentProgressBar
        set(_) { }

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
        } else rootView!!
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            restoreFragmentState(savedInstanceState)
        }
    }

    private fun restoreFragmentState(savedInstanceState: Bundle) {
        savedInstanceState.getParcelableArrayList<Track>(ALBUM_TRACKS)
            ?.let { savedAlbumTracks ->
                this.albumTracks.clear()
                this.albumTracks.addAll(savedAlbumTracks)
                this.trackItemAdapter?.updateDataSet(savedAlbumTracks)
            }
    }

    override fun onResume() {
        super.onResume()
        if (activity?.resources?.getBoolean(R.bool.isLandscape) == true) {
            navController.navigateUp()
        }
        arguments?.getParcelable<Album>(AlbumsFragment.ALBUM)?.let { album ->
            trackItemAdapter?.updateDataSet(album.tracks)
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
            savedInstanceState?.let {
                restoreFragmentState(it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ALBUM_TRACKS, this.albumTracks)
    }

    private fun setupViews(binding: FragmentAlbumTracksBinding) {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.albumTracksFragmentList.let { recyclerView ->
            recyclerView.layoutManager = layoutManager
            recyclerView.setHasFixedSize(true)
            trackItemAdapter = TrackItemAdapter(this.requireActivity())
            recyclerView.adapter = trackItemAdapter
        }
    }
}