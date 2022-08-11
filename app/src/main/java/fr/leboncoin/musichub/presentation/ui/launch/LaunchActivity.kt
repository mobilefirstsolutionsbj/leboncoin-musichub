package fr.leboncoin.musichub.presentation.ui.launch

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import fr.leboncoin.musichub.BuildConfig
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.databinding.ActivityLaunchBinding
import fr.leboncoin.musichub.presentation.base.BaseActivity
import fr.leboncoin.musichub.presentation.ui.albums.AlbumsActivity

class LaunchActivity : BaseActivity() {

    companion object {
        const val DELAY: Long = 3000
    }

    private var binding: ActivityLaunchBinding? = null

    override fun getTag() = "[ ${LaunchActivity::class.java.simpleName} ]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        binding?.let { binding ->
            setContentView(binding.root)
            setupViews(binding)
        }
    }

    private fun setupViews(binding: ActivityLaunchBinding) {
        val versionCode: Int = BuildConfig.VERSION_CODE
        val versionName: String = BuildConfig.VERSION_NAME
        binding.footerText.text = resources.getString(
            R.string.activity_launch_footer_text,
            versionName,
            versionCode.toString()
        )
    }

    private fun launch() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@LaunchActivity, AlbumsActivity::class.java))
            finish()
        }, DELAY)
    }

    override fun onStart() {
        super.onStart()
        launch()
    }
}
