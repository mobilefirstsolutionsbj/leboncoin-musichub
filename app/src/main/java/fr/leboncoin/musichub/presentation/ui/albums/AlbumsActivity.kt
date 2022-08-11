package fr.leboncoin.musichub.presentation.ui.albums

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.musichub.databinding.ActivityAlbumsBinding
import fr.leboncoin.musichub.presentation.base.BaseActivity

@AndroidEntryPoint
class AlbumsActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController

    /**
     * UI - Activity view binding
     */
    private var binding: ActivityAlbumsBinding? = null

    /**
     * <p>Saved Instance State
     */
    private var savedInstanceState: Bundle? = null

    override fun getTag() = "[ ${AlbumsActivity::class.java.simpleName} ]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariables()
        setContentView(binding?.root)
    }

    private fun initVariables() {
        binding = ActivityAlbumsBinding.inflate(layoutInflater)
        binding?.let { binding ->
            bindViews(binding)
            setupViews()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveFragmentsStates(outState)
    }

    private fun saveFragmentsStates(outState: Bundle) {
        this.savedInstanceState = outState
        navController.saveState()
    }

    private fun bindViews(binding: ActivityAlbumsBinding) {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.mainFragmentContainer.id) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    private fun setupViews() {
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        savedInstanceState = null
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        println("[ ${getTag()} ] onDestinationChanged() - arguments: $arguments")
    }
}
