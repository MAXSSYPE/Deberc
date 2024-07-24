package app.first.my_deb.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import com.andremion.counterfab.CounterFab
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

fun onNewClick(
    activity: Activity,
    mainActivity: MainActivity,
    resultFields: List<TextView>,
    numberFields: List<EditText>,
    names: List<TextView>,
    counterBolts: List<CounterFab>,
    coroutineScope: CoroutineScope
) {
    val messageBoxView = LayoutInflater.from(activity).inflate(R.layout.message_box, null)
    val messageBoxBuilder = AlertDialog.Builder(activity).setView(messageBoxView)

    val header = messageBoxView.findViewById<TextView>(R.id.message_box_header)
    val content = messageBoxView.findViewById<TextView>(R.id.message_box_content)
    header.text = activity.getString(R.string.sure)
    content.text = activity.getString(R.string.new_game)

    val messageBoxInstance = messageBoxBuilder.show()

    val buttonYes = messageBoxView.findViewById<Button>(R.id.message_box_yes)
    buttonYes.setOnClickListener {
        try {
            saveText(mainActivity, resultFields, numberFields, names, counterBolts, coroutineScope)
            resultFields.filter { it.text != "0" }.forEach { it.setTextAnimation("0") }
            numberFields.filter { it.text.toString() != "" }.forEach { it.setTextAnimation("") }
            counterBolts.forEach { it.count = 0 }

            coroutineScope.launch {
                mainActivity.dao.setEndTime(
                    mainActivity.gameWithGamers!!.game.id!!,
                    System.currentTimeMillis()
                )
                mainActivity.dao.addGameToInactive(mainActivity.gameWithGamers!!.game.id!!)
                mainActivity.initGame()
            }
            messageBoxInstance.dismiss()
            mainActivity.showRateDialog(activity.applicationContext)
        } catch (ignored: Exception) {
        }
    }

    val buttonNo = messageBoxView.findViewById<Button>(R.id.message_box_no)
    buttonNo.setOnClickListener {
        messageBoxInstance.dismiss()
    }

    messageBoxInstance.window?.setBackgroundDrawableResource(R.drawable.message_background)
}

fun onClick(
    activity: Activity,
    mainActivity: MainActivity,
    resultFields: List<TextView>,
    numberFields: List<EditText>
) {
    val imm =
        activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isAcceptingText && activity.currentFocus != null) {
        imm.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    if (numberFields.any { it.text.toString().isNotEmpty() }) {
        try {
            for (i in numberFields.indices) {
                var nowInt = 0
                val prevInt = resultFields[i].text.toString().toIntOrNull() ?: 0
                if (numberFields[i].text.toString().isNotEmpty())
                    nowInt = numberFields[i].text.toString().toInt()

                if (nowInt != 0)
                    resultFields[i].setTextAnimation((prevInt + nowInt).toString(), 200)

                mainActivity.gameWithGamers!!.gamers[i].gameScore?.add(nowInt.toString())
                mainActivity.gameWithGamers!!.gamers[i].score =
                    resultFields[i].text.toString().toInt()
                numberFields[i].setText("")
            }
        } catch (ignored: Exception) {
        }
    }
}

fun saveText(
    mainActivity: MainActivity,
    resultFields: List<TextView>,
    numberFields: List<EditText>,
    names: List<TextView>,
    counterBolts: List<CounterFab>,
    coroutineScope: CoroutineScope
) {
    for (i in resultFields.indices) {
        mainActivity.gameWithGamers!!.gamers[i].apply {
            name = names[i].text.toString()
            score = resultFields[i].text.toString().toIntOrNull() ?: 0
            lastRoundScore = numberFields[i].text.toString()
            bolts = counterBolts[i].count
        }
    }
    coroutineScope.launch {
        mainActivity.dao.upsertByReplacementGame(mainActivity.gameWithGamers!!)
    }
}

fun loadText(
    mainActivity: MainActivity,
    context: Context,
    resultFields: List<TextView>,
    numberFields: List<EditText>,
    names: List<TextView>,
    counterBolts: List<CounterFab>
) {
    val gameWithGamers = mainActivity.gameWithGamers
    if (gameWithGamers != null && gameWithGamers.gamers.size >= resultFields.size) {
        for (i in resultFields.indices) {
            val gamer = gameWithGamers.gamers[i]
            names[i].text = gamer.name
            resultFields[i].text = gamer.score.toString()
            numberFields[i].setText(gamer.lastRoundScore)
            counterBolts[i].count = gamer.bolts ?: 0
        }
    }

    setBoltButtons(
        PreferenceManager.getDefaultSharedPreferences(context).getBoolean("hasBolt", false),
        context,
        mainActivity,
        counterBolts,
        resultFields
    )
}

fun setBoltButtons(
    hasBolt: Boolean,
    context: Context,
    mainActivity: MainActivity,
    counterBolts: List<CounterFab>,
    resultFields: List<TextView>
) {
    setVisibleBolt(hasBolt, counterBolts)
    if (hasBolt) {
        for (i in counterBolts.indices) {
            counterBolts[i].setOnClickListener {
                val valueOfMinus = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("valueOfMinus", "-100")!!.toInt()
                counterBolts[i].increase()
                if (counterBolts[i].count == PreferenceManager.getDefaultSharedPreferences(context)
                        .getString("countOfNails", "3")!!.toInt()
                ) {
                    counterBolts[i].count = 0
                    val resultField = resultFields[i]
                    resultField.setTextAnimation(
                        ((resultField.text.toString().toIntOrNull()
                            ?: (0 + valueOfMinus))).toString(),
                        200
                    )

                    for (j in mainActivity.gameWithGamers!!.gamers.indices) {
                        if (i == j)
                            mainActivity.gameWithGamers!!.gamers[j].gameScore?.add(valueOfMinus.toString())
                        else
                            mainActivity.gameWithGamers!!.gamers[j].gameScore?.add("0")
                    }
                }
            }
        }
    }
}

fun setVisibleBolt(hasBolt: Boolean, counterBolts: List<CounterFab>) =
    counterBolts.forEach { it.isVisible = hasBolt }

fun setupNumberFields(
    activity: Activity,
    mainActivity: MainActivity,
    resultFields: List<TextView>,
    numberFields: List<EditText>
) = numberFields.forEach {
    it.setOnEditorActionListener { _, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE ||
            (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
        ) {
            onClick(activity, mainActivity, resultFields, numberFields)
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
            true
        } else {
            false
        }
    }
}

fun setupButtons(
    activity: Activity,
    mainActivity: MainActivity,
    resultFields: List<TextView>,
    numberFields: List<EditText>,
    names: List<TextView>,
    counterBolts: List<CounterFab>,
    newButton: Button,
    addButton: Button,
    coroutineScope: CoroutineScope
) {
    newButton.setOnClickListener {
        onNewClick(
            activity,
            mainActivity,
            resultFields,
            numberFields,
            names,
            counterBolts,
            coroutineScope
        )
    }
    addButton.setOnClickListener {
        onClick(activity, mainActivity, resultFields, numberFields)
    }
}

fun setupNamesAndResults(
    activity: Activity,
    mainActivity: MainActivity,
    view: View,
    resultFields: List<TextView>,
    numberFields: List<EditText>,
    names: List<TextView>,
    boxes: List<TextFieldBoxes>
) {
    names.forEach {
        it.setOnEditorActionListener { _, _, _ ->
            onClick(activity, mainActivity, resultFields, numberFields)
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }

    resultFields.forEach {
        it.setOnLongClickListener(longClickListener)
        it.setOnDragListener(OnDragListener(mainActivity, view, boxes, activateNames))
        it.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(mainActivity, R.anim.rotate)
            if (it.animation == null)
                it.startAnimation(animation)
        }
    }
}

val longClickListener = View.OnLongClickListener { v: View ->
    val item = ClipData.Item((v as TextView).x.toString() as CharSequence)
    val dragData = ClipData(v as? CharSequence, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
    val myShadow = MyDragShadowBuilder(v)

    if (android.os.Build.VERSION.SDK_INT <= 23) {
        v.startDrag(dragData, myShadow, null, 0)
    } else {
        v.startDragAndDrop(dragData, myShadow, null, 0)
    }
}

val activateNames = { boxes: List<TextFieldBoxes> ->
    boxes.forEach { it.hasFocus = true }
    if (boxes.isNotEmpty())
        boxes.last().clearFocus()
}
