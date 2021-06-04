package app.first.my_deb

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.viewpager.widget.ViewPager
import app.first.my_deb.database.*
import app.first.my_deb.ui.main.SectionsPagerAdapter
import app.first.my_deb.utils.ContextWrapper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity
import com.maltaisn.calcdialog.CalcDialog
import id.ionbit.ionalert.IonAlert
import kotlinx.coroutines.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(coroutineContext).launch { initGame() }
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
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            onCalcClick()
        }
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
        IonAlert(this, IonAlert.WARNING_TYPE)
                .setTitleText(resources.getString(R.string.exitTitle))
                .setContentText(resources.getString(R.string.exit))
                .setCancelText(resources.getString(R.string.no))
                .setConfirmText(resources.getString(R.string.yes))
                .showCancelButton(true)
                .setConfirmClickListener { sDialog: IonAlert ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finishAffinity()
                    sDialog.cancel()
                }
                .show()
    }

    fun initGame() = runBlocking {
        databaseHelper = DatabaseHelper(this@MainActivity)
        dao = DatabaseHelper.instance.getDao()
        //dao.nukeTable()
        val sPref = this@MainActivity.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
        val type = sPref.getString("type", "1")

        CoroutineScope(coroutineContext).launch {
            gameWithGamers = if (dao.activeGameExists(type!!))
                dao.getActiveGame(type)
            else
                createDefaultGameAndGamers(type)

            dao.upsertByReplacementGame(gameWithGamers)
            gameWithGamers = dao.getActiveGame(type)
        }.join()
    }

    private fun createDefaultGameAndGamers(gameType: String): GameWithGamers {
        val game = GameEntity()
        game.apply {
            type = gameType
            isActive = true
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

    override fun onStop() {
        super.onStop()
        saveText()
    }

    override fun onPause() {
        super.onPause()
        saveText()
    }

    private fun saveText() {
        CoroutineScope(coroutineContext).launch { dao.upsertByReplacementGame(gameWithGamers) }
    }
}