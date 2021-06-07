package app.first.my_deb.ui.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import java.util.*


class ScoreFragment2x2 : Fragment() {

    private lateinit var dataTable: DataTable
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        mainActivity = activity as MainActivity
        dataTable = view.findViewById(R.id.data_table)
        loadTable()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mAdView = requireView().findViewById<AdView>(R.id.adView)
        MobileAds.initialize(requireContext()) { }

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        loadTable()
    }

    private fun loadTable() {
        val pl1 = mainActivity.gameWithGamers.gamers[0].gameScore
        val pl2 = mainActivity.gameWithGamers.gamers[1].gameScore
        val pl1Name: String = if (mainActivity.gameWithGamers.gamers[0].name == null || mainActivity.gameWithGamers.gamers[0].name == "")
            getString(R.string.team1)
        else
            mainActivity.gameWithGamers.gamers[0].name!!
        val pl2Name: String = if (mainActivity.gameWithGamers.gamers[1].name == null || mainActivity.gameWithGamers.gamers[1].name == "")
            getString(R.string.team2)
        else
            mainActivity.gameWithGamers.gamers[1].name!!

        val header = DataTableHeader.Builder()
                .item(pl1Name, 1)
                .item(pl2Name, 1).build()
        val rows = ArrayList<DataTableRow>()
        if (pl1 != null && pl2 != null && pl1.isNotEmpty() && pl2.isNotEmpty()) {
            for (i in pl1.indices) {
                val row = DataTableRow.Builder()
                        .value(pl1[i])
                        .value(pl2[i]).build()
                rows.add(row)
            }
        }
        dataTable.removeAllViews()
        dataTable.header = header
        dataTable.rows = rows
        dataTable.inflate(requireContext())
    }
}