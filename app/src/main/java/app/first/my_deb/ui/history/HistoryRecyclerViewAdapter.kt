package app.first.my_deb.ui.history

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.database.GameWithGamers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HistoryRecyclerViewAdapter(
    private val context: Context,
    private val activity: MainActivity,
    private var values: List<GameWithGamers>,
    private val listener: (GameWithGamers) -> Unit
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewprice: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        when (item.game.type) {
            "1" -> {
                holder.name1.text = if (item.gamers[0].name == null || item.gamers[0].name == "")
                    context.getString(R.string.team1)
                else
                    item.gamers[0].name

                holder.name2.text = if (item.gamers[1].name == null || item.gamers[1].name == "")
                    context.getString(R.string.team2)
                else
                    item.gamers[1].name

                holder.score1.text = item.gamers[0].score.toString()
                holder.score2.text = item.gamers[1].score.toString()

                holder.linearLayoutMain.weightSum = 2f
                holder.linearLayout3 = null
                holder.linearLayout4 = null
            }

            "2" -> {
                holder.name1.text = if (item.gamers[0].name == null || item.gamers[0].name == "")
                    context.getString(R.string.gamer1)
                else
                    item.gamers[0].name

                holder.name2.text = if (item.gamers[1].name == null || item.gamers[1].name == "")
                    context.getString(R.string.gamer2)
                else
                    item.gamers[1].name

                holder.score1.text = item.gamers[0].score.toString()
                holder.score2.text = item.gamers[1].score.toString()

                holder.linearLayoutMain.weightSum = 2f
                holder.linearLayout3 = null
                holder.linearLayout4 = null
            }

            "3" -> {
                holder.name1.text = if (item.gamers[0].name == null || item.gamers[0].name == "")
                    context.getString(R.string.gamer1)
                else
                    item.gamers[0].name

                holder.name2.text = if (item.gamers[1].name == null || item.gamers[1].name == "")
                    context.getString(R.string.gamer2)
                else
                    item.gamers[1].name

                holder.name3.text = if (item.gamers[2].name == null || item.gamers[2].name == "")
                    context.getString(R.string.gamer3)
                else
                    item.gamers[2].name

                holder.score1.text = item.gamers[0].score.toString()
                holder.score2.text = item.gamers[1].score.toString()
                holder.score3.text = item.gamers[2].score.toString()

                holder.linearLayoutMain.weightSum = 3f
                holder.linearLayout3 = null
            }

            "4" -> {
                holder.name1.text = if (item.gamers[0].name == null || item.gamers[0].name == "")
                    context.getString(R.string.gamer1)
                else
                    item.gamers[0].name

                holder.name2.text = if (item.gamers[1].name == null || item.gamers[1].name == "")
                    context.getString(R.string.gamer2)
                else
                    item.gamers[1].name

                holder.name3.text = if (item.gamers[2].name == null || item.gamers[2].name == "")
                    context.getString(R.string.gamer3)
                else
                    item.gamers[2].name

                holder.name4.text = if (item.gamers[3].name == null || item.gamers[3].name == "")
                    context.getString(R.string.gamer4)
                else
                    item.gamers[3].name

                holder.score1.text = item.gamers[0].score.toString()
                holder.score2.text = item.gamers[1].score.toString()
                holder.score3.text = item.gamers[2].score.toString()
                holder.score4.text = item.gamers[3].score.toString()
            }
        }


        holder.timeStart.text = getDateTime(item.game.startTimestamp!!)
        if (item.game.endTimestamp == null) {
            holder.timeEnd.text = getDateTime(System.currentTimeMillis())
        } else {
            holder.timeEnd.text = getDateTime(item.game.endTimestamp!!)
        }
        holder.itemView.setOnClickListener { listener(item) }
        holder.restore.setOnClickListener {
            showMessageBoxRestoreGame(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name1: TextView = view.findViewById(R.id.name1)
        val name2: TextView = view.findViewById(R.id.name2)
        val name3: TextView = view.findViewById(R.id.name3)
        val name4: TextView = view.findViewById(R.id.name4)
        val score1: TextView = view.findViewById(R.id.score1)
        val score2: TextView = view.findViewById(R.id.score2)
        val score3: TextView = view.findViewById(R.id.score3)
        val score4: TextView = view.findViewById(R.id.score4)
        val timeStart: TextView = view.findViewById(R.id.time_start)
        val timeEnd: TextView = view.findViewById(R.id.time_end)
        val restore: Button = view.findViewById(R.id.button_restore)
        var linearLayout3: LinearLayout? = view.findViewById(R.id.lin3)
        var linearLayout4: LinearLayout? = view.findViewById(R.id.lin4)
        val linearLayoutMain: LinearLayout = view.findViewById(R.id.main)

    }
    fun removeItem(position: Int) {
        values.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: GameWithGamers, position: Int) {
        values.toMutableList().add(position, item)
        notifyItemInserted(position)
    }

    fun getData(): List<GameWithGamers> {
        return values
    }

    fun updateList(list: List<GameWithGamers>) {
        values = list
        notifyDataSetChanged()
    }

    private fun getDateTime(epochMillis: Long): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            sdf.format(Date(epochMillis))
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun showMessageBoxRestoreGame(gameWithGamers: GameWithGamers) {
        val messageBoxView = LayoutInflater.from(context).inflate(R.layout.message_box, null)

        val messageBoxBuilder = AlertDialog.Builder(context).setView(messageBoxView)

        val header = messageBoxView.findViewById<TextView>(R.id.message_box_header)
        header.text = context.getString(R.string.restore_game)

        val  messageBoxInstance = messageBoxBuilder.show()

        val buttonYes = messageBoxView.findViewById<Button>(R.id.message_box_yes)
        buttonYes.setOnClickListener {
            CoroutineScope(activity.coroutineContext).launch {
                activity.gameWithGamers.game.isActive = false
                activity.gameWithGamers.game.endTimestamp =  System.currentTimeMillis()
                activity.dao.upsertByReplacementGame(activity.gameWithGamers)
                activity.dao.makeGameActive(gameWithGamers.game.id!!)
                activity.dao.setEndTime(
                    gameWithGamers.game.id!!,
                    System.currentTimeMillis()
                )

                when (gameWithGamers.game.type) {
                    "1" -> {
                        val sPref: SharedPreferences = activity.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                        val ed = sPref.edit()
                        ed.putString("type", "1")
                        ed.apply()
                    }
                    "2" -> {
                        val sPref: SharedPreferences = activity.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                        val ed = sPref.edit()
                        ed.putString("type", "2")
                        ed.apply()
                    }
                    "3" -> {
                        val sPref: SharedPreferences = activity.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                        val ed = sPref.edit()
                        ed.putString("type", "3")
                        ed.apply()
                    }
                    "4" -> {
                        val sPref: SharedPreferences = activity.getSharedPreferences("Save.txt", Context.MODE_PRIVATE)
                        val ed = sPref.edit()
                        ed.putString("type", "4")
                        ed.apply()
                    }
                }
            }

            activity.finish()
            messageBoxInstance.dismiss()
            activity.startActivity(Intent(activity, MainActivity::class.java))
            activity.overridePendingTransition(R.anim.appear, R.anim.disappear)
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
}
