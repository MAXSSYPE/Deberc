package app.first.my_deb

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import app.first.my_deb.database.Dao
import app.first.my_deb.database.DatabaseHelper
import app.first.my_deb.database.GameEntity
import app.first.my_deb.database.GameWithGamers
import app.first.my_deb.database.GamerEntity
import app.first.my_deb.ui.ftu.OnBoardingActivity
import app.first.my_deb.ui.main.SectionsPagerAdapter
import app.first.my_deb.utils.AppFontManager
import app.first.my_deb.utils.ContextWrapper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.maltaisn.calcdialog.CalcDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import java.math.BigDecimal

class MainActivity : CyaneaAppCompatActivity(), CalcDialog.CalcDialogCallback {

    private lateinit var viewPager: ViewPager
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var tabs: TabLayout
    private var value: BigDecimal? = null
    private val calcDialog = CalcDialog()
    private lateinit var databaseHelper: DatabaseHelper
    internal lateinit var dao: Dao
    internal var gameWithGamers: GameWithGamers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Handle window insets to prevent navigation bar overlap
        val rootView = findViewById<View>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Apply padding to account for system bars (navigation buttons, status bar)
            view.setPadding(
                insets.left,
                insets.top,
                insets.right,
                insets.bottom
            )

            WindowInsetsCompat.CONSUMED
        }

        lifecycleScope.launch {
            setupFont()
            setupDatabase()
            initGame()
            setupViewPagerAndTabs()
        }
        checkOnboarding()
    }

    private suspend fun setupFont() {
        withContext(Dispatchers.IO) {
            val pref = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
            val font = pref.getString("fonts", "@font/roboto")
            AppFontManager(this@MainActivity).setFont(font)
        }
    }

    private suspend fun setupDatabase() {
        withContext(Dispatchers.IO) {
            databaseHelper = DatabaseHelper(this@MainActivity)
            dao = DatabaseHelper.instance.getDao()
        }
    }

    private fun checkOnboarding() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (!sharedPreferences.getBoolean("completed_onboarding", false)) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }

    private fun setupViewPagerAndTabs() {
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        tabs = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        tabs.getTabAt(1)?.select()
    }

    override fun attachBaseContext(newBase: Context) {
        val pref = PreferenceManager.getDefaultSharedPreferences(newBase)
        val lang = pref.getString("langs", "")
        val context = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            ContextWrapper.wrap(newBase, lang)
        } else {
            newBase
        }
        super.attachBaseContext(context)
    }

    fun onCalcClick() {
        calcDialog.settings.setExpressionShown(true).initialValue = value
        calcDialog.show(supportFragmentManager, "calc_dialog")
    }

    override fun onValueEntered(requestCode: Int, value: BigDecimal?) {
        this.value = value
    }

    override fun onBackPressed() {
        showMessageBoxExit()
    }

    internal suspend fun initGame() {
        withContext(Dispatchers.IO) {
            val sPref = getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
            val type = sPref.getString("type", "1")

            type?.let {
                gameWithGamers = if (dao.activeGameExists(it)) {
                    dao.getActiveGame(it).apply {
                        gamers = gamers.sortedBy { gamer -> gamer.number }
                    }
                } else {
                    createDefaultGameAndGamers(it).also { game ->
                        dao.upsertByReplacementGame(game)
                    }.let {
                        dao.getActiveGame(type).apply {
                            gamers = gamers.sortedBy { gamer -> gamer.number }
                        }
                    }
                }
            }
        }
    }

    private fun createDefaultGameAndGamers(gameType: String): GameWithGamers {
        val game = GameEntity().apply {
            type = gameType
            isActive = true
            startTimestamp = System.currentTimeMillis()
        }

        val gamers = mutableListOf<GamerEntity>().apply {
            add(createGamer(0))
            add(createGamer(1))
            if (gameType == "3") add(createGamer(2))
            if (gameType == "4") {
                add(createGamer(2))
                add(createGamer(3))
            }
        }

        return GameWithGamers(game, gamers)
    }

    private fun createGamer(gamerNumber: Int): GamerEntity {
        return GamerEntity().apply {
            number = gamerNumber
            name = ""
            score = 0
            gameScore = ArrayList()
        }
    }

    override fun onPause() {
        super.onPause()
        saveGame()
    }

    internal fun saveGame() {
        gameWithGamers?.let {
            lifecycleScope.launch(Dispatchers.IO) {
                dao.upsertByReplacementGame(it)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val handleReturn = super.dispatchTouchEvent(ev)
        currentFocus?.let { view ->
            val x = ev?.x?.toInt() ?: 0
            val y = ev?.y?.toInt() ?: 0

            if (view is EditText || view is ExtendedEditText) {
                if (ev?.action == MotionEvent.ACTION_UP && !getLocationOnScreen(view as EditText).contains(
                        x,
                        y
                    )
                ) {
                    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
        return handleReturn
    }

    private fun getLocationOnScreen(mEditText: EditText): Rect {
        val location = IntArray(2)
        mEditText.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + mEditText.width,
            location[1] + mEditText.height
        )
    }

    private fun showMessageBoxExit() {
        val messageBoxView = LayoutInflater.from(this).inflate(R.layout.message_box, null)
        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)

        messageBoxView.findViewById<TextView>(R.id.message_box_header).text =
            getString(R.string.exitTitle)
        messageBoxView.findViewById<TextView>(R.id.message_box_content).text =
            getString(R.string.exit)

        val messageBoxInstance = messageBoxBuilder.show()
        messageBoxInstance.window?.setBackgroundDrawableResource(R.drawable.message_background)

        messageBoxView.findViewById<Button>(R.id.message_box_yes).setOnClickListener {
            startActivity(Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
            finishAffinity()
            messageBoxInstance.dismiss()
        }

        messageBoxView.findViewById<Button>(R.id.message_box_no).setOnClickListener {
            messageBoxInstance.dismiss()
        }
    }

    fun showRateDialog(context: Context) {
        val manager = ReviewManagerFactory.create(context)
        manager.requestReviewFlow().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                manager.launchReviewFlow(this, task.result)
            } else {
                val reviewErrorCode = (task.exception as? ReviewException)?.errorCode
            }
        }
    }
}
