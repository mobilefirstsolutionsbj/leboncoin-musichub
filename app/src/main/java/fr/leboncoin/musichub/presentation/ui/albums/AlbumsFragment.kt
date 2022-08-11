package fr.leboncoin.musichub.presentation.ui.albums

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.databinding.FragmentAlbumsBinding
import fr.leboncoin.musichub.domain.model.Album
import fr.leboncoin.musichub.presentation.base.BaseFragment

@AndroidEntryPoint
class AlbumsFragment : BaseFragment(), AlbumItemClickListener {

    companion object {
        const val ALBUMS = "ALBUMS"
        const val ALBUM = "ALBUM"
        const val LIST_STATE_KEY = "LIST_STATE_KEY"
        const val ITEM_VIEW_CACHE_SIZE = 20
    }

    private val albums: ArrayList<Album> = arrayListOf()
    private val viewModel: AlbumsViewModel by viewModels()
    private var albumItemAdapter: AlbumItemAdapter? = null
    private var binding: FragmentAlbumsBinding? = null
    private var rootView: View? = null
    private var btnRetry: Button? = null
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var mListState: Parcelable? = null
    private var savedInstanceState: Parcelable? = null
    private lateinit var navController: NavController

    override var progressBar: ProgressBar?

        get() = binding?.albumsFragmentProgressBar
        set(_) {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (rootView == null) {
            rootView = inflater.inflate(
                R.layout.fragment_albums, container,
                false
            )
            rootView!!
        } else {
            return rootView!!
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            restoreFragmentState(savedInstanceState)
        } else {
            viewModel.fetchLocalAlbums()
        }
    }

    private fun restoreFragmentState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            savedInstanceState.getParcelableArrayList<Album>(ALBUMS)?.let { albums ->
                albumItemAdapter?.updateDataSet(albums)
            }
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY)
            recyclerView?.layoutManager?.onRestoreInstanceState(mListState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.binding = FragmentAlbumsBinding.bind(view)
        navController = Navigation.findNavController(
            requireActivity(),
            R.id.main_fragment_container
        )
        binding?.let { binding ->
            bindViews(binding)
            setupViews()
            kotlin.run {
                if (!viewModel.state.hasActiveObservers()) {
                    observeTracks()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (mListState != null) {
            recyclerView?.let { recyclerView ->
                recyclerView.layoutManager?.onRestoreInstanceState(mListState)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ALBUMS, this.albums)
        recyclerView?.let { recyclerView ->
            this.mListState = recyclerView.layoutManager?.onSaveInstanceState()
            outState.putParcelable(LIST_STATE_KEY, mListState)
            this.savedInstanceState = outState
        }
    }

    private fun bindViews(binding: FragmentAlbumsBinding) {
        progressBar = binding.albumsFragmentProgressBar
        btnRetry = binding.albumsFragmentBtnRetry
        swipeRefresh = binding.albumsFragmentSwipeRefresh
        recyclerView = binding.albumsFragmentList
    }

    private fun setupViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), AlbumItemAdapter.SPAN_SIZE)

        recyclerView?.let { recyclerView ->
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.setHasFixedSize(true)
            recyclerView.setItemViewCacheSize(ITEM_VIEW_CACHE_SIZE)
            albumItemAdapter = AlbumItemAdapter(this)
            recyclerView.adapter = albumItemAdapter
        }

        swipeRefresh?.setOnRefreshListener {
            if (!viewModel.state.hasActiveObservers()) {
                observeTracks().also {
                    viewModel.fetchLocalAlbums()
                }
            }
        }
    }

    private val stateObserver = Observer<FetchItemListState<Album>> { state ->
        if (state.isLoading) {
            progressBar?.isIndeterminate = true
            progressBar?.visibility = View.VISIBLE
        }

        state.items?.let { albums ->
            progressBar?.visibility = View.GONE
            swipeRefresh?.isRefreshing = false
            if (albums.isNotEmpty()) {
                didFetchAlbums(albums)
            } else {
                // Try fetching from local DB
                viewModel.fetchAlbums()
                println("// EMPTY Try fetching from remote DB")
            }
        }

        if (state.error != null) {
            swipeRefresh?.isRefreshing = false
            rootView?.let { rootView -> showSnackBar(rootView, getString(state.error), Snackbar.LENGTH_LONG) }
            progressBar?.visibility = View.GONE
        }
    }

    private fun observeTracks() {
        viewModel.state.observe(requireActivity(), stateObserver)
    }

    private fun didFetchAlbums(list: List<Album>) {
        viewModel.state.removeObserver(stateObserver)
        this.albums.clear()
        this.albums.addAll(list)
        if (list.isEmpty()) {
            return
        }
        albumItemAdapter?.updateDataSet(albums)
    }

    override fun onAlbumItemClick(position: Int, item: Album) {
        val bundle = Bundle()
        bundle.putParcelable(ALBUM, item)
        if (activity?.resources?.getBoolean(R.bool.isLandscape) == false) {
            navController.navigate(
                R.id.action_albums_fragment_to_album_tracks_fragment,
                bundle
            )
        } else {
            val argument = NavArgument.Builder().setDefaultValue(item).build()
            navController.findDestination(R.id.album_tracks_fragment)?.addArgument(ALBUM, argument)
            println("isLandscape")
        }
    }

    override fun onDestroy() {
        albumItemAdapter = null
        binding = null
        rootView = null
        progressBar = null
        btnRetry = null
        swipeRefresh = null
        recyclerView = null
        mListState = null
        savedInstanceState = null
        super.onDestroy()
    }
}