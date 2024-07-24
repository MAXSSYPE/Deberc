package app.first.my_deb.ui.score

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.jaredrummler.cyanea.app.CyaneaFragment
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow

class ScoreFragment2x2 : CyaneaFragment() {

    private lateinit var dataTable: DataTable
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        mainActivity = activity as MainActivity
        dataTable = view.findViewById(R.id.data_table)
        val pref = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val font = pref.getString("fonts", "font/roboto.ttf")
        dataTable.typeface = Typeface.createFromAsset(requireContext().assets, font)
        loadTable()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mAdView = requireView().findViewById<AdView>(R.id.adView)
        MobileAds.initialize(requireContext()) { }

        val extras = Bundle()
        extras.putString("max_ad_content_rating", "MA")

        val adRequest =
            AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
        mAdView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        loadTable()
    }

    private fun loadTable() {
        val gameWithGamers = mainActivity.gameWithGamers
        if (gameWithGamers != null && gameWithGamers.gamers.size >= 2) {
            val gamers = gameWithGamers.gamers
            val pl1 = gamers[0].gameScore
            val pl2 = gamers[1].gameScore
            val pl1Name =
                if (gamers[0].name.isNullOrEmpty()) getString(R.string.team1) else gamers[0].name!!
            val pl2Name =
                if (gamers[1].name.isNullOrEmpty()) getString(R.string.team2) else gamers[1].name!!

            val header = DataTableHeader.Builder()
                .item(pl1Name, 1)
                .item(pl2Name, 1).build()
            val rows = ArrayList<DataTableRow>()
            if (pl1 != null && pl2 != null) {
                val maxLength = maxOf(pl1.size, pl2.size)
                for (i in 0 until maxLength) {
                    val row = DataTableRow.Builder()
                        .value(pl1.getOrNull(i) ?: "")
                        .value(pl2.getOrNull(i) ?: "").build()
                    rows.add(row)
                }
            }
            dataTable.removeAllViews()
            dataTable.header = header
            dataTable.rows = rows
            dataTable.inflate(requireContext())
        }
    }
}
