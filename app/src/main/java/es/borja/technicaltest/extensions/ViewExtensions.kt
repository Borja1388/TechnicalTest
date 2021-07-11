package es.borja.technicaltest.extensions

import android.view.View
import androidx.core.widget.ContentLoadingProgressBar

fun View.visibleIf(value: Boolean) {
    this.visibility = if (value) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun ContentLoadingProgressBar.showIf(value: Boolean) {
    if (value) {
        this.show()
    } else {
        this.hide()
    }
}