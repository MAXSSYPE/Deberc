package app.first.my_deb.ui.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import com.jaredrummler.cyanea.prefs.CyaneaSettingsActivity


class MenuFragment : Fragment() {

    private lateinit var settingsButton: Button
    private lateinit var button2x2: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        settingsButton = view.findViewById(R.id.settings)
        settingsButton.setOnClickListener {
            val intent = Intent(activity, CyaneaSettingsActivity::class.java)
            startActivity(intent)
        }
        button2x2 = view.findViewById(R.id.game2x2)
        button2x2.setOnClickListener {
            try {
                val sPref: SharedPreferences = requireActivity().getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "1")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }
        button2 = view.findViewById(R.id.game2)
        button2.setOnClickListener {
            try {
                val sPref: SharedPreferences = requireActivity().getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "2")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }
        button3 = view.findViewById(R.id.game3)
        button3.setOnClickListener {
            try {
                val sPref: SharedPreferences = requireActivity().getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "3")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }
        button4 = view.findViewById(R.id.game4)
        button4.setOnClickListener {
            try {
                val sPref: SharedPreferences = requireActivity().getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "4")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }

        requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.lang, SettingsFragment())
                .commit()

        val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        pref.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "langs" && isAdded) {
                val act = requireActivity() as MainActivity
                act.finish()
                startActivity(Intent(activity, MainActivity::class.java))
                act.overridePendingTransition(R.anim.appear, R.anim.disappear)
            }
        }
        return view
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    private fun restart() {
        requireActivity().finish()
        startActivity(Intent(activity, MainActivity::class.java))
        requireActivity().overridePendingTransition(R.anim.appear, R.anim.disappear)
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): MenuFragment {
            return MenuFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
