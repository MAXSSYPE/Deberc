package app.first.my_deb.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.database.GameWithGamers
import app.first.my_deb.utils.fadInAnimation
import com.jaredrummler.cyanea.app.CyaneaFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        loadGames()
    }

    private fun loadGames() {
        viewLifecycleOwner.lifecycleScope.launch {
            val games = withContext(Dispatchers.IO) {
                mainActivity.dao.getInactiveGames()
            }
            if (view.findViewById<RecyclerView>(R.id.list).adapter == null) {
                view.fadInAnimation(500)
            }
            view.findViewById<RecyclerView>(R.id.list).adapter = setAdapter(games)
            requireActivity().findViewById<TextView>(R.id.empty).isVisible = games.isEmpty()
        }
    }

    private fun setAdapter(games: List<GameWithGamers>): HistoryRecyclerViewAdapter {
        return HistoryRecyclerViewAdapter(requireContext(), mainActivity, games) {}
    }

    override fun onPause() {
        super.onPause()
        requireActivity().findViewById<TextView>(R.id.empty).isVisible = false
    }
}
