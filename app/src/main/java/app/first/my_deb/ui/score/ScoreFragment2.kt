package app.first.my_deb.ui.score

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.first.my_deb.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlin.collections.ArrayList

class ScoreFragment2 : Fragment() {

    private lateinit var dataTable: DataTable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_score, container, false)
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

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            loadTable()
        }
    }

    private fun loadTable() {
        val sPref = requireContext().getSharedPreferences("Score2.txt", Context.MODE_PRIVATE)
        val pl1 = Gson().fromJson<ArrayList<String>>(sPref.getString("pl1", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
        val pl2 = Gson().fromJson<ArrayList<String>>(sPref.getString("pl2", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
        val header = DataTableHeader.Builder()
                .item(getString(R.string.gamer1), 1)
                .item(getString(R.string.gamer2), 1).build()
        val rows = java.util.ArrayList<DataTableRow>()
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

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

    }
}