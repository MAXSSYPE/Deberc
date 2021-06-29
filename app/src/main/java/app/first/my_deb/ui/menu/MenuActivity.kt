package app.first.my_deb.ui.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.utils.AppFontManager
import app.first.my_deb.utils.ContextWrapper
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.jaredrummler.cyanea.prefs.CyaneaSettingsActivity

class MenuActivity : CyaneaAppCompatActivity() {

    private lateinit var settingsButton: Button
    private lateinit var button2x2: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences?, key: String? ->
        if (key == "langs" || key == "fonts") {
            this.finish()
            startActivity(Intent(this, MainActivity::class.java))
            this.overridePendingTransition(R.anim.appear, R.anim.disappear)
        }}
    override fun onCreate(savedInstanceState: Bundle?) {
        val sPref = PreferenceManager.getDefaultSharedPreferences(this)
        val font = sPref.getString("fonts", "@font/roboto")
        AppFontManager(this).setFont(font);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        settingsButton = findViewById(R.id.settings)
        settingsButton.setOnClickListener {
            val intent = Intent(this, CyaneaSettingsActivity::class.java)
            startActivity(intent)
        }
        button2x2 = findViewById(R.id.game2x2)
        button2x2.setOnClickListener {
            try {
                val sPref: SharedPreferences = this.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "1")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }
        button2 = findViewById(R.id.game2)
        button2.setOnClickListener {
            try {
                val sPref: SharedPreferences = this.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "2")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }
        button3 = findViewById(R.id.game3)
        button3.setOnClickListener {
            try {
                val sPref: SharedPreferences = this.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "3")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }
        button4 = findViewById(R.id.game4)
        button4.setOnClickListener {
            try {
                val sPref: SharedPreferences = this.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                val ed = sPref.edit()
                ed.putString("type", "4")
                ed.apply()
                restart()
            } catch (ignored: Exception) {
            }
        }

        this.supportFragmentManager
            .beginTransaction()
            .replace(R.id.lang, SettingsFragment())
            .commit()

        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        pref.registerOnSharedPreferenceChangeListener(listener)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.menu)
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    private fun restart() {
        this.finish()
        startActivity(Intent(this, MainActivity::class.java))
        this.overridePendingTransition(R.anim.appear, R.anim.disappear)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.appear, R.anim.disappear)
    }

    override fun attachBaseContext(newBase: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = pref.getString("langs", "")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            super.attachBaseContext(ContextWrapper.wrap(newBase, lang))
        } else {
            super.attachBaseContext(newBase)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}