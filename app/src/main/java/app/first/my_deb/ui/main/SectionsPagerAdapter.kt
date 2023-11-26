package app.first.my_deb.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import app.first.my_deb.R
import app.first.my_deb.ui.history.HistoryFragment
import app.first.my_deb.ui.score.ScoreFragment2
import app.first.my_deb.ui.score.ScoreFragment2x2
import app.first.my_deb.ui.score.ScoreFragment3
import app.first.my_deb.ui.score.ScoreFragment4

private val TAB_TITLES = arrayOf(
        R.string.history,
        R.string.game,
        R.string.count,
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var one: Fragment? = null
    private var two: Fragment? = null
    private var three: Fragment? = null

    override fun getItem(position: Int): Fragment {
        val sPref = context.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
        val type = sPref.getString("type", "1")
        when (position) {
            0 -> {
                one = HistoryFragment()
                return one!!
            }
            1 -> {
                two = if (type == null || type == "" || type == "1") {
                    Fragment2x2()
                } else if (type == "2") {
                    Fragment2()
                } else if (type == "3") {
                    Fragment3()
                } else if (type == "4") {
                    Fragment4()
                } else {
                    Fragment2x2()
                }
                return two!!
            }
            2 -> {
                three = if (type == null || type == "" || type == "1") {
                    ScoreFragment2x2()
                } else if (type == "2") {
                    ScoreFragment2()
                } else if (type == "3") {
                    ScoreFragment3()
                } else if (type == "4") {
                    ScoreFragment4()
                } else {
                    ScoreFragment2x2()
                }
                return three!!
            }
            else -> return HistoryFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}