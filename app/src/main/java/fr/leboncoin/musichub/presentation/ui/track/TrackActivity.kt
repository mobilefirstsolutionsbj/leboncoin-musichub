package fr.leboncoin.musichub.presentation.ui.track

import android.os.Bundle
import fr.leboncoin.musichub.R
import fr.leboncoin.musichub.presentation.base.BaseActivity

class TrackActivity : BaseActivity() {

    override fun getTag() = "[ ${TrackActivity::class.java.simpleName} ]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)
    }
}