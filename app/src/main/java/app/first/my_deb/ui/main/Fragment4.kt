package app.first.my_deb.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.preference.PreferenceManager
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.utils.MyDragShadowBuilder
import app.first.my_deb.utils.OnDragListener
import app.first.my_deb.utils.fadInAnimation
import app.first.my_deb.utils.setTextAnimation
import com.andremion.counterfab.CounterFab
import com.jaredrummler.cyanea.app.CyaneaFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

class Fragment4 : CyaneaFragment() {

    private lateinit var resultField1: TextView
    private lateinit var resultField2: TextView
    private lateinit var resultField3: TextView
    private lateinit var resultField4: TextView
    private lateinit var numberField1: EditText
    private lateinit var numberField2: EditText
    private lateinit var numberField3: EditText
    private lateinit var numberField4: EditText
    private lateinit var name1: TextView
    private lateinit var name2: TextView
    private lateinit var name3: TextView
    private lateinit var name4: TextView
    private lateinit var newButton: Button
    private lateinit var addButton: Button
    private lateinit var mainActivity: MainActivity
    private lateinit var counterBolt1: CounterFab
    private lateinit var counterBolt2: CounterFab
    private lateinit var counterBolt3: CounterFab
    private lateinit var counterBolt4: CounterFab

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_4, container, false)

        mainActivity = activity as MainActivity
        resultField1 = view.findViewById(R.id.resultField1)
        resultField2 = view.findViewById(R.id.resultField2)
        resultField3 = view.findViewById(R.id.resultField3)
        resultField4 = view.findViewById(R.id.resultField4)
        numberField1 = view.findViewById(R.id.numberField1)
        numberField2 = view.findViewById(R.id.numberField2)
        numberField3 = view.findViewById(R.id.numberField3)
        numberField4 = view.findViewById(R.id.numberField4)
        name1 = view.findViewById(R.id.name1)
        name2 = view.findViewById(R.id.name2)
        name3 = view.findViewById(R.id.name3)
        name4 = view.findViewById(R.id.name4)

        resultField1.setOnLongClickListener(longClickListener)
        resultField2.setOnLongClickListener(longClickListener)
        resultField3.setOnLongClickListener(longClickListener)
        resultField4.setOnLongClickListener(longClickListener)
        resultField1.setOnDragListener(OnDragListener(mainActivity, view, activateNames))
        resultField2.setOnDragListener(OnDragListener(mainActivity, view, activateNames))
        resultField3.setOnDragListener(OnDragListener(mainActivity, view, activateNames))
        resultField4.setOnDragListener(OnDragListener(mainActivity, view, activateNames))

        newButton = view.findViewById(R.id.button_new)
        newButton.setOnClickListener {
            onNewClick()
        }
        addButton = view.findViewById(R.id.button_add)
        addButton.setOnClickListener {
            onClick()
        }
        resultField1.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            resultField1.startAnimation(animation)
        }
        resultField2.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            resultField2.startAnimation(animation)
        }
        resultField3.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            resultField3.startAnimation(animation)
        }
        resultField4.setOnClickListener {
            val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            resultField4.startAnimation(animation)
        }
        counterBolt1 = view.findViewById(R.id.counter_bolt1) as CounterFab
        counterBolt2 = view.findViewById(R.id.counter_bolt2) as CounterFab
        counterBolt3 = view.findViewById(R.id.counter_bolt3) as CounterFab
        counterBolt4 = view.findViewById(R.id.counter_bolt4) as CounterFab

        numberField1.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }

        numberField2.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }

        numberField3.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }

        numberField4.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        name1.setOnEditorActionListener { _, _, _ ->
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        name2.setOnEditorActionListener { _, _, _ ->
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        name3.setOnEditorActionListener { _, _, _ ->
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        name4.setOnEditorActionListener { _, _, _ ->
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        loadText()
        view.fadInAnimation(700)
        return view
    }

    private fun onClick() {
        val imm = requireActivity().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            if (imm != null && requireActivity().currentFocus != null) {
                imm.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
        if (!(numberField1.text.toString() == "" && numberField2.text.toString() == "" && numberField3.text.toString() == "" && numberField4.text.toString() == "")) try {
            val prev1 = resultField1.text.toString().toInt()
            val prev2 = resultField2.text.toString().toInt()
            val prev3 = resultField3.text.toString().toInt()
            val prev4 = resultField4.text.toString().toInt()
            var now1 = 0
            var now2 = 0
            var now3 = 0
            var now4 = 0
            if (numberField1.text.toString() != "") now1 = numberField1.text.toString().toInt() else numberField1.setText("0")
            if (numberField2.text.toString() != "") now2 = numberField2.text.toString().toInt() else numberField2.setText("0")
            if (numberField3.text.toString() != "") now3 = numberField3.text.toString().toInt() else numberField3.setText("0")
            if (numberField4.text.toString() != "") now4 = numberField4.text.toString().toInt() else numberField4.setText("0")
            if (now1 != 0)
                resultField1.setTextAnimation((prev1 + now1).toString(), 200)
            if (now2 != 0)
                resultField2.setTextAnimation((prev2 + now2).toString(), 200)
            if (now3 != 0)
                resultField3.setTextAnimation((prev3 + now3).toString(), 200)
            if (now4 != 0)
                resultField4.setTextAnimation((prev4 + now4).toString(), 200)
            mainActivity.gameWithGamers.gamers[0].gameScore!!.add(numberField1.text.toString())
            mainActivity.gameWithGamers.gamers[1].gameScore!!.add(numberField2.text.toString())
            mainActivity.gameWithGamers.gamers[2].gameScore!!.add(numberField3.text.toString())
            mainActivity.gameWithGamers.gamers[3].gameScore!!.add(numberField4.text.toString())
            mainActivity.gameWithGamers.gamers[0].score = resultField1.text.toString().toInt()
            mainActivity.gameWithGamers.gamers[1].score = resultField2.text.toString().toInt()
            mainActivity.gameWithGamers.gamers[2].score = resultField3.text.toString().toInt()
            mainActivity.gameWithGamers.gamers[3].score = resultField4.text.toString().toInt()

            numberField1.setText("")
            numberField2.setText("")
            numberField3.setText("")
            numberField4.setText("")
        } catch (ignored: Exception) {
        }
    }

    private fun onNewClick() {
        val messageBoxView =
            LayoutInflater.from(requireActivity()).inflate(R.layout.message_box, null)

        val messageBoxBuilder = AlertDialog.Builder(requireActivity()).setView(messageBoxView)

        val header = messageBoxView.findViewById<TextView>(R.id.message_box_header)
        val content = messageBoxView.findViewById<TextView>(R.id.message_box_content)
        header.text = getString(R.string.sure)
        content.text = getString(R.string.new_game)

        val messageBoxInstance = messageBoxBuilder.show()

        val buttonYes = messageBoxView.findViewById<Button>(R.id.message_box_yes)
        buttonYes.setOnClickListener {
            try {
                saveText()
                if (resultField1.text != "0")
                    resultField1.setTextAnimation("0")
                if (resultField2.text != "0")
                    resultField2.setTextAnimation("0")
                if (resultField3.text != "0")
                    resultField3.setTextAnimation("0")
                if (resultField4.text != "0")
                    resultField4.setTextAnimation("0")
                if (numberField1.text.toString() != "")
                    numberField1.setTextAnimation("")
                if (numberField2.text.toString() != "")
                    numberField2.setTextAnimation("")
                if (numberField3.text.toString() != "")
                    numberField3.setTextAnimation("")
                if (numberField4.text.toString() != "")
                    numberField4.setTextAnimation("")

                counterBolt1.count = 0
                counterBolt2.count = 0
                counterBolt3.count = 0
                counterBolt4.count = 0
                CoroutineScope(mainActivity.coroutineContext).launch {
                    mainActivity.dao.setEndTime(mainActivity.gameWithGamers.game.id!!, System.currentTimeMillis())
                    mainActivity.dao.addGameToInactive(mainActivity.gameWithGamers.game.id!!)
                    mainActivity.initGame()
                }
                messageBoxInstance.dismiss()
                mainActivity.showRateDialog()
            } catch (ignored: Exception) {
            }
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

    override fun onDestroy() {
        super.onDestroy()
        saveText()
    }

    override fun onPause() {
        super.onPause()
        saveText()
    }

    override fun onResume() {
        super.onResume()
        loadText()
        setBoltButtons(PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean("hasBolt", false))
    }

    private fun saveText() {
        mainActivity.gameWithGamers.gamers[0].name = name1.text.toString()
        mainActivity.gameWithGamers.gamers[1].name = name2.text.toString()
        mainActivity.gameWithGamers.gamers[2].name = name3.text.toString()
        mainActivity.gameWithGamers.gamers[3].name = name4.text.toString()
        mainActivity.gameWithGamers.gamers[0].score = resultField1.text.toString().toInt()
        mainActivity.gameWithGamers.gamers[1].score = resultField2.text.toString().toInt()
        mainActivity.gameWithGamers.gamers[2].score = resultField3.text.toString().toInt()
        mainActivity.gameWithGamers.gamers[3].score = resultField4.text.toString().toInt()
        mainActivity.gameWithGamers.gamers[0].lastRoundScore = numberField1.text.toString()
        mainActivity.gameWithGamers.gamers[1].lastRoundScore = numberField2.text.toString()
        mainActivity.gameWithGamers.gamers[2].lastRoundScore = numberField3.text.toString()
        mainActivity.gameWithGamers.gamers[3].lastRoundScore = numberField4.text.toString()
        mainActivity.gameWithGamers.gamers[0].bolts = counterBolt1.count
        mainActivity.gameWithGamers.gamers[1].bolts = counterBolt2.count
        mainActivity.gameWithGamers.gamers[2].bolts = counterBolt3.count
        mainActivity.gameWithGamers.gamers[3].bolts = counterBolt4.count
        CoroutineScope(mainActivity.coroutineContext).launch {
            mainActivity.dao.upsertByReplacementGame(mainActivity.gameWithGamers)
        }
    }

    private fun loadText() {
        name1.text = mainActivity.gameWithGamers.gamers[0].name
        name2.text = mainActivity.gameWithGamers.gamers[1].name
        name3.text = mainActivity.gameWithGamers.gamers[2].name
        name4.text = mainActivity.gameWithGamers.gamers[3].name
        resultField1.text = mainActivity.gameWithGamers.gamers[0].score.toString()
        resultField2.text = mainActivity.gameWithGamers.gamers[1].score.toString()
        resultField3.text = mainActivity.gameWithGamers.gamers[2].score.toString()
        resultField4.text = mainActivity.gameWithGamers.gamers[3].score.toString()
        numberField1.setText(mainActivity.gameWithGamers.gamers[0].lastRoundScore)
        numberField2.setText(mainActivity.gameWithGamers.gamers[1].lastRoundScore)
        numberField3.setText(mainActivity.gameWithGamers.gamers[2].lastRoundScore)
        numberField4.setText(mainActivity.gameWithGamers.gamers[3].lastRoundScore)
        counterBolt1.count = if (mainActivity.gameWithGamers.gamers[0].bolts == null)
            0
        else
            mainActivity.gameWithGamers.gamers[0].bolts!!

        counterBolt2.count = if (mainActivity.gameWithGamers.gamers[1].bolts == null)
            0
        else
            mainActivity.gameWithGamers.gamers[1].bolts!!
        counterBolt3.count = if (mainActivity.gameWithGamers.gamers[2].bolts == null)
            0
        else
            mainActivity.gameWithGamers.gamers[2].bolts!!
        counterBolt4.count = if (mainActivity.gameWithGamers.gamers[3].bolts == null)
            0
        else
            mainActivity.gameWithGamers.gamers[3].bolts!!

        setBoltButtons(PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean("hasBolt", false))
    }

    private fun setBoltButtons(hasBolt: Boolean) {
        setVisibleBolt(hasBolt)
        if (hasBolt) {
            counterBolt1.setOnClickListener {
                val valueOfMinus = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("valueOfMinus", "-100")!!
                counterBolt1.increase()
                if (counterBolt1.count == PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("countOfNails", "3")!!.toInt()) {
                    counterBolt1.count = 0
                    if (resultField1.text.toString() == "")
                        resultField1.setTextAnimation(valueOfMinus, 200)
                    else
                        resultField1.setTextAnimation((resultField1.text.toString().toInt() + valueOfMinus.toInt()).toString(), 200)

                    mainActivity.gameWithGamers.gamers[0].gameScore!!.add(valueOfMinus)
                    mainActivity.gameWithGamers.gamers[1].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[2].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[3].gameScore!!.add("0")
                }
            }

            counterBolt2.setOnClickListener {
                val valueOfMinus = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("valueOfMinus", "-100")!!
                counterBolt2.increase()
                if (counterBolt2.count == PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("countOfNails", "3")!!.toInt()) {
                    counterBolt2.count = 0
                    if (resultField2.text.toString() == "")
                        resultField2.setTextAnimation(valueOfMinus, 200)
                    else
                        resultField2.setTextAnimation((resultField2.text.toString().toInt() + valueOfMinus.toInt()).toString(), 200)

                    mainActivity.gameWithGamers.gamers[0].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[1].gameScore!!.add(valueOfMinus)
                    mainActivity.gameWithGamers.gamers[2].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[3].gameScore!!.add("0")
                }
            }

            counterBolt3.setOnClickListener {
                val valueOfMinus = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("valueOfMinus", "-100")!!
                counterBolt3.increase()
                if (counterBolt3.count == PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("countOfNails", "3")!!.toInt()) {
                    counterBolt3.count = 0
                    if (resultField3.text.toString() == "")
                        resultField3.setTextAnimation(valueOfMinus, 200)
                    else
                        resultField3.setTextAnimation((resultField3.text.toString().toInt() + valueOfMinus.toInt()).toString(), 200)

                    mainActivity.gameWithGamers.gamers[0].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[1].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[2].gameScore!!.add(valueOfMinus)
                    mainActivity.gameWithGamers.gamers[3].gameScore!!.add("0")
                }
            }

            counterBolt4.setOnClickListener {
                val valueOfMinus = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("valueOfMinus", "-100")!!
                counterBolt4.increase()
                if (counterBolt4.count == PreferenceManager.getDefaultSharedPreferences(requireContext()).getString("countOfNails", "3")!!.toInt()) {
                    counterBolt4.count = 0
                    if (resultField4.text.toString() == "")
                        resultField4.setTextAnimation(valueOfMinus, 200)
                    else
                        resultField4.setTextAnimation((resultField4.text.toString().toInt() + valueOfMinus.toInt()).toString(), 200)

                    mainActivity.gameWithGamers.gamers[0].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[1].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[2].gameScore!!.add("0")
                    mainActivity.gameWithGamers.gamers[3].gameScore!!.add(valueOfMinus)
                }
            }
        }
    }

    private fun setVisibleBolt(hasBolt: Boolean) {
        counterBolt1.isVisible = hasBolt
        counterBolt2.isVisible = hasBolt
        counterBolt3.isVisible = hasBolt
        counterBolt4.isVisible = hasBolt
    }

    val activateNames = { _: View ->
    }

    val longClickListener = View.OnLongClickListener { v: View ->
        val item = ClipData.Item((v as TextView).x.toString() as CharSequence)
        val dragData = ClipData(
            v as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item)

        val myShadow = MyDragShadowBuilder(v)

        if (android.os.Build.VERSION.SDK_INT <= 23) {
            v.startDrag(
                dragData,
                myShadow,
                null,
                0
            )
        } else {
            v.startDragAndDrop(
                dragData,
                myShadow,
                null,
                0
            )
        }
    }
}