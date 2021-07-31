package app.first.my_deb.ui.ftu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.leanback.app.OnboardingSupportFragment
import androidx.preference.PreferenceManager
import app.first.my_deb.R
import java.util.*


class OnBoardingFragment : OnboardingSupportFragment() {

    val COMPLETED_ONBOARDING = "completed_onboarding"

    private val pageTitles = intArrayOf(
        R.string.onboarding_title1,
        R.string.onboarding_title2,
        R.string.onboarding_title3,
        R.string.onboarding_title4,
        R.string.onboarding_title5
    )
    private val pageDescriptions = intArrayOf(
        R.string.onboarding_description1,
        R.string.onboarding_description2,
        R.string.onboarding_description3,
        R.string.onboarding_description4,
        R.string.onboarding_description5
    )
    private val pageImages = intArrayOf(
        R.drawable.onboarding1,
        R.drawable.onboarding2,
        R.drawable.onboarding3,
        R.drawable.onboarding4,
        R.drawable.onboarding5
    )
    private val ANIMATION_DURATION: Long = 500
    private var mContentAnimator: Animator? = null
    private var mContentView: ImageView? = null

    override fun onFinishFragment() {
        super.onFinishFragment()
        val sharedPreferencesEditor: SharedPreferences.Editor =
            PreferenceManager.getDefaultSharedPreferences(
                activity
            ).edit()
        sharedPreferencesEditor.putBoolean(COMPLETED_ONBOARDING, true)
        sharedPreferencesEditor.apply()
        requireActivity().finish()
    }

    override fun getPageCount(): Int {
        return pageTitles.size
    }

    override fun getPageTitle(pageIndex: Int): String {
        return getString(pageTitles[pageIndex])
    }

    override fun getPageDescription(pageIndex: Int): String {
        return getString(pageDescriptions[pageIndex])
    }

    @Nullable
    override fun onCreateBackgroundView(inflater: LayoutInflater?, container: ViewGroup?): View {
        val bgView = View(requireActivity())
        bgView.setBackgroundColor(resources.getColor(R.color.cyanea_primary_light))
        return bgView
    }

    @Nullable
    override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        mContentView = ImageView(requireActivity())
        mContentView!!.scaleType = ImageView.ScaleType.CENTER_INSIDE
        mContentView!!.setPadding(0, 32, 0, 32)
        mContentView!!.setImageResource(pageImages[0])
        return mContentView
    }

    @Nullable
    override fun onCreateForegroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return null
    }

    override fun onPageChanged(newPage: Int, previousPage: Int) {
        if (mContentAnimator != null) {
            mContentAnimator!!.end()
        }
        val animators = ArrayList<Animator>()
        val fadeOut = createFadeOutAnimator(mContentView!!)
        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                mContentView!!.setImageResource(pageImages[newPage])
            }
        })
        animators.add(fadeOut)
        animators.add(createFadeInAnimator(mContentView!!))
        val set = AnimatorSet()
        set.playSequentially(animators)
        set.start()
        mContentAnimator = set
    }

    private fun createFadeInAnimator(view: View): Animator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(ANIMATION_DURATION)
    }

    private fun createFadeOutAnimator(view: View): Animator {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(ANIMATION_DURATION)
    }
}