package app.first.my_deb.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

fun TextView.setTextAnimation(text: String, duration: Long = 300, completion: (() -> Unit)? = null) {
    fadOutAnimation(duration) {
        this.text = text
        fadInAnimation(duration) {
            completion?.let {
                it()
            }
        }
    }
}

fun View.fadOutAnimation(duration: Long = 300, visibility: Int = View.INVISIBLE, completion: (() -> Unit)? = null) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            this.visibility = visibility
            completion?.let {
                it()
            }
        }
}

fun View.fadInAnimation(duration: Long = 300, completion: (() -> Unit)? = null) {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            completion?.let {
                it()
            }
        }
}

fun LinearLayout.getViewByCoordinates(x: Float, y: Float) : View? {
    (childCount - 1 downTo 0)
        .map { this.getChildAt(it) }
        .forEach {
            val bounds = Rect()
            it.getHitRect(bounds)
            if (bounds.contains(x.toInt(), y.toInt())) {
                return it
            }
        }
    return null
}

fun LinearLayout.getIndexOfViewByCoordinates(x: Float, y: Float) : Int? {
    for (i in 0 until childCount) {
        val bounds = Rect()
        this.getChildAt(i).getHitRect(bounds)
        if (bounds.contains(x.toInt(), y.toInt())) {
            return i
        }
    }
    return null
}