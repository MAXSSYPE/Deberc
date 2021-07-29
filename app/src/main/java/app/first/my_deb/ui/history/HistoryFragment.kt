package app.first.my_deb.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.database.GameWithGamers
import com.jaredrummler.cyanea.app.CyaneaFragment
import kotlinx.coroutines.runBlocking

class HistoryFragment : CyaneaFragment() {

    private var columnCount = 1
    private lateinit var view: LinearLayout
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = activity as MainActivity
        view = inflater.inflate(R.layout.fragment_history, container, false) as LinearLayout
        return view
    }

    override fun onResume() {
        super.onResume()
        with(view.getChildAt(0) as RecyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
        }

        var games: List<GameWithGamers> = ArrayList<GameWithGamers>()
        runBlocking {
            games = mainActivity.dao.getInactiveGames()
        }

        view.findViewById<RecyclerView>(R.id.list).adapter = setAdapter(games)
        if (games.isNullOrEmpty()) {
            requireActivity().findViewById<TextView>(R.id.empty).visibility = View.VISIBLE
        }
    }

    private fun setAdapter(games: List<GameWithGamers>): HistoryRecyclerViewAdapter {
        return HistoryRecyclerViewAdapter(requireContext(), mainActivity, games) {}
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<TextView>(R.id.empty).visibility = View.INVISIBLE
    }
}