package fr.leboncoin.musichub.presentation.base

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    abstract var progressBar: ProgressBar?

    fun showSnackBar(parent: View, message: String, duration: Int) {
        Snackbar.make(parent, message, duration).show()
    }
}