package app.first.my_deb


import android.app.AlertDialog
import android.content.*
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import app.first.my_deb.database.*
import app.first.my_deb.ui.ftu.OnBoardingActivity
import app.first.my_deb.ui.main.SectionsPagerAdapter
import app.first.my_deb.ui.menu.MenuActivity
import app.first.my_deb.utils.AppFontManager
import app.first.my_deb.utils.ContextWrapper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.maltaisn.calcdialog.CalcDialog
import kotlinx.coroutines.*
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import java.math.BigDecimal
import kotlin.coroutines.CoroutineContext


class MainActivity : CyaneaAppCompatActivity(), CalcDialog.CalcDialogCallback {

    lateinit var viewPager: ViewPager
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var tabs: TabLayout
    private var value: BigDecimal? = null
    private val calcDialog = CalcDialog()
    lateinit var databaseHelper: DatabaseHelper
    lateinit var dao: Dao
    lateinit var gameWithGamers: GameWithGamers
    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        val pref = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        val font = pref.getString("fonts", "@font/roboto")
        AppFontManager(this@MainActivity).setFont(font)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this@MainActivity)
        dao = DatabaseHelper.instance.getDao()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        if (!sharedPreferences.getBoolean("completed_onboarding", false)) {
           startActivity(Intent(this@MainActivity, OnBoardingActivity::class.java))
        }
        sectionsPagerAdapter = SectionsPagerAdapter(this@MainActivity, supportFragmentManager)
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
        tabs.getTabAt(1)!!.select()

        val calculatorFab: FloatingActionButton = findViewById(R.id.calculator_fab)
        calculatorFab.setOnClickListener {
            onCalcClick()
        }
        val menuFab: FloatingActionButton = findViewById(R.id.menu_fab)
        menuFab.setOnClickListener {
            startActivity(Intent(this@MainActivity, MenuActivity::class.java))
            this@MainActivity.overridePendingTransition(R.anim.appear, R.anim.disappear)
        }
        initGame()
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

    private fun onCalcClick() {
        calcDialog.settings.setExpressionShown(true).initialValue = value
        calcDialog.show(supportFragmentManager, "calc_dialog")
    }

    override fun onValueEntered(requestCode: Int, value: BigDecimal?) {
        this.value = value
    }

    override fun onBackPressed() {
        showMessageBoxExit()
    }

    suspend fun initGame() {
        //runBlocking { dao.nukeTable() }
        val sPref = this@MainActivity.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
        val type = sPref.getString("type", "1")

        CoroutineScope(coroutineContext).launch {
            if (dao.activeGameExists(type!!)) {
                gameWithGamers = dao.getActiveGame(type)
                gameWithGamers.gamers = gameWithGamers.gamers.sortedBy { it.number }
            } else {
                gameWithGamers = createDefaultGameAndGamers(type)
                dao.upsertByReplacementGame(gameWithGamers)
                gameWithGamers = dao.getActiveGame(type)
                gameWithGamers.gamers = gameWithGamers.gamers.sortedBy { it.number }
            }

            println("test " + dao.getInactiveGames())
            println("active " + dao.getActiveGame("1"))
            println("game " + gameWithGamers)
        }.join()
    }

    private fun createDefaultGameAndGamers(gameType: String): GameWithGamers {
        val game = GameEntity()
        game.apply {
            type = gameType
            isActive = true
            startTimestamp =  System.currentTimeMillis()
        }

        val gamers = ArrayList<GamerEntity>(2)
        gamers.add(createGamer(0))
        gamers.add(createGamer(1))

        if (gameType == "3")
            gamers.add(createGamer(2))
        if (gameType == "4") {
            gamers.add(createGamer(2))
            gamers.add(createGamer(3))
        }


        return GameWithGamers(game, gamers)
    }

    private fun createGamer(gamerNumber: Int): GamerEntity {
        val gamer = GamerEntity()
        gamer.apply {
            number = gamerNumber
            name = ""
            score = 0
            gameScore = ArrayList()
        }
        return gamer
    }

    override fun onPause() {
        super.onPause()
        saveText()
    }

    fun saveText() {
        CoroutineScope(coroutineContext).launch { dao.upsertByReplacementGame(gameWithGamers) }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val handleReturn = super.dispatchTouchEvent(ev)

        val view: View? = currentFocus

        val x = ev!!.x.toInt()
        val y = ev.y.toInt()

        if (view is EditText || view is ExtendedEditText) {
            val innerView: View? = currentFocus
            if (ev.action == MotionEvent.ACTION_UP &&
                !getLocationOnScreen(innerView as EditText).contains(x, y)
            ) {
                val input = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                input.hideSoftInputFromWindow(
                    window.currentFocus!!
                        .windowToken, 0
                )
            }
        }

        return handleReturn
    }

    private fun getLocationOnScreen(mEditText: EditText): Rect {
        val mRect = Rect()
        val location = IntArray(2)
        mEditText.getLocationOnScreen(location)
        mRect.left = location[0]
        mRect.top = location[1]
        mRect.right = location[0] + mEditText.width
        mRect.bottom = location[1] + mEditText.height
        return mRect
    }

    fun showMessageBoxExit() {
        val messageBoxView = LayoutInflater.from(this).inflate(R.layout.message_box, null)

        val messageBoxBuilder = AlertDialog.Builder(this).setView(messageBoxView)

        val header = messageBoxView.findViewById<TextView>(R.id.message_box_header)
        val content = messageBoxView.findViewById<TextView>(R.id.message_box_content)
        header.text = getString(R.string.exitTitle)
        content.text = getString(R.string.exit)

        val  messageBoxInstance = messageBoxBuilder.show()

        val buttonYes = messageBoxView.findViewById<Button>(R.id.message_box_yes)
        buttonYes.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finishAffinity()
            messageBoxInstance.dismiss()
        }

        val buttonNo = messageBoxView.findViewById<Button>(R.id.message_box_no)
        buttonNo.setOnClickListener {
            messageBoxInstance.dismiss()
        }

        messageBoxInstance.window?.setBackgroundDrawableResource(R.drawable.message_background)

        /*messageBoxView.setOnClickListener(){
            messageBoxInstance.dismiss()
        }*/
    }

    fun showRateDialog() {
        val manager = ReviewManagerFactory.create(this)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            try {
                if (task.isSuccessful) {
                    val reviewInfo: ReviewInfo = task.result
                    val flow: Task<Void> = manager.launchReviewFlow(this, reviewInfo)
                    flow.addOnCompleteListener {

                    }
                } else {

                }
            } catch (ex: Exception) {
            }
        }
    }
}