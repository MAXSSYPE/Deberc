package app.first.my_deb.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import app.first.my_deb.utils.setTextAnimation
import com.andremion.counterfab.CounterFab
import com.jaredrummler.cyanea.app.CyaneaFragment
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Fragment2 : CyaneaFragment() {

    private lateinit var resultField1: TextView
    private lateinit var resultField2: TextView
    private lateinit var numberField1: EditText
    private lateinit var numberField2: EditText
    private lateinit var name1: TextView
    private lateinit var name2: TextView
    private lateinit var newButton: Button
    private lateinit var addButton: Button
    private lateinit var numberPicker: NumberPicker
    private lateinit var mainActivity: MainActivity
    private lateinit var counterBolt1: CounterFab
    private lateinit var counterBolt2: CounterFab

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)

        mainActivity = activity as MainActivity
        numberField1 = view.findViewById(R.id.numberField1)
        numberField2 = view.findViewById(R.id.numberField2)
        resultField1 = view.findViewById(R.id.resultField1)
        resultField2 = view.findViewById(R.id.resultField2)
        name1 = view.findViewById(R.id.name1)
        name2 = view.findViewById(R.id.name2)
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
        counterBolt1 = view.findViewById(R.id.counter_bolt1) as CounterFab
        counterBolt2 = view.findViewById(R.id.counter_bolt2) as CounterFab
        numberPicker = view.findViewById(R.id.number_picker)
        val data = arrayOf("162", "182", "202", "212", "222", "232", "242", "252", "262", "272", "282", "292", "302", "312", "322", "332", "342", "362", "382")
        numberPicker.minValue = 1
        numberPicker.maxValue = 19
        numberPicker.displayedValues = data
        numberPicker.setOnClickListener {
            try {
                if (!(numberField1.text.toString().isEmpty() && numberField2.text.toString().isEmpty()) || !(numberField1.text.toString().isNotEmpty() && numberField2.text.toString().isNotEmpty())) {
                    if (numberField1.text.toString().isNotEmpty() && numberField1.text.toString().toInt() > 0) {
                        if (numberField1.text.toString() != "" && numberField1.text.toString() != "-" && numberField1.text.toString().toInt() >= 0 && numberField1.text.toString().toInt() <= data[numberPicker.value - 1].toInt()) {
                            numberField2.hint = (data[numberPicker.value - 1].toInt() - numberField1.text.toString().toInt()).toString()
                        } else {
                            numberField1.hint = "0"
                            numberField2.hint = "0"
                        }
                    }
                    if (numberField2.text.toString().isNotEmpty() && numberField2.text.toString().toInt() > 0) {
                        if (numberField2.text.toString() != "" && numberField2.text.toString() != "-" && numberField2.text.toString().toInt() >= 0 && numberField2.text.toString().toInt() <= data[numberPicker.value - 1].toInt()) {
                            numberField1.hint = (data[numberPicker.value - 1].toInt() - numberField2.text.toString().toInt()).toString()
                        } else {
                            numberField1.hint = "0"
                            numberField2.hint = "0"
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
        numberPicker.setOnValueChangedListener { picker: NumberPicker, _: Int, _: Int ->
            try {
                if (!(numberField1.text.toString().isEmpty() && numberField2.text.toString().isEmpty()) || !(numberField1.text.toString().isNotEmpty() && numberField2.text.toString().isNotEmpty())) {
                    if (numberField1.text.toString().isNotEmpty() && numberField1.text.toString().toInt() > 0) {
                        if (numberField1.text.toString() != "" && numberField1.text.toString() != "-" && numberField1.text.toString().toInt() >= 0 && numberField1.text.toString().toInt() <= data[picker.value - 1].toInt()) {
                            numberField2.hint = (data[picker.value - 1].toInt() - numberField1.text.toString().toInt()).toString()
                        } else {
                            numberField1.hint = "0"
                            numberField2.hint = "0"
                        }
                    }
                    if (numberField2.text.toString().isNotEmpty() && numberField2.text.toString().toInt() > 0) {
                        if (numberField2.text.toString() != "" && numberField2.text.toString() != "-" && numberField2.text.toString().toInt() >= 0 && numberField2.text.toString().toInt() <= data[picker.value - 1].toInt()) {
                            numberField1.hint = (data[picker.value - 1].toInt() - numberField2.text.toString().toInt()).toString()
                        } else {
                            numberField1.hint = "0"
                            numberField2.hint = "0"
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }
        numberPicker.setOnScrollListener { view: NumberPicker, _: Int ->
            try {
                if (numberField1.text.toString() == "0") {
                    numberField2.hint = data[view.value - 1].toInt().toString()
                } else if (numberField2.text.toString() == "0") {
                    numberField1.hint = data[view.value - 1].toInt().toString()
                } else if (!(numberField1.text.toString().isEmpty() && numberField2.text.toString().isEmpty()) || !(numberField1.text.toString().isNotEmpty() && numberField2.text.toString().isNotEmpty())) {
                    if (numberField1.text.toString().isNotEmpty() && numberField1.text.toString().toInt() > 0) {
                        if (numberField1.text.toString() != "" && numberField1.text.toString() != "-" && numberField1.text.toString().toInt() >= 0 && numberField1.text.toString().toInt() <= data[view.value - 1].toInt()) {
                            numberField2.hint = (data[view.value - 1].toInt() - numberField1.text.toString().toInt()).toString()
                        } else {
                            numberField1.hint = "0"
                            numberField2.hint = "0"
                        }
                    }
                    if (numberField2.text.toString().isNotEmpty() && numberField2.text.toString().toInt() > 0) {
                        if (numberField2.text.toString() != "" && numberField2.text.toString() != "-" && numberField2.text.toString().toInt() >= 0 && numberField2.text.toString().toInt() <= data[view.value - 1].toInt()) {
                            numberField1.hint = (data[view.value - 1].toInt() - numberField2.text.toString().toInt()).toString()
                        } else {
                            numberField1.hint = "0"
                            numberField2.hint = "0"
                        }
                    }
                }
            } catch (ignored: Exception) {
            }
        }

        numberField1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                try {
                    if (numberField1.text.toString() != "" && numberField1.text.toString() != "-" && numberField1.text.toString().toInt() >= 0 && numberField1.text.toString().toInt() <= data[numberPicker.value - 1].toInt()) {
                        numberField2.hint = (data[numberPicker.value - 1].toInt() - numberField1.text.toString().toInt()).toString()
                    } else {
                        numberField1.hint = "0"
                        numberField2.hint = "0"
                    }
                } catch (ignored: java.lang.Exception) {
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        numberField2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                try {
                    if (numberField2.text.toString() != "" && numberField2.text.toString() != "-" && numberField2.text.toString().toInt() >= 0 && numberField2.text.toString().toInt() <= data[numberPicker.value - 1].toInt()) {
                        numberField1.hint = (data[numberPicker.value - 1].toInt() - numberField2.text.toString().toInt()).toString()
                    } else {
                        numberField1.hint = "0"
                        numberField2.hint = "0"
                    }
                } catch (ignored: java.lang.Exception) {
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        numberField1.setOnEditorActionListener { _, _, _ ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        numberField2.setOnEditorActionListener { _, _, _ ->
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
        loadText()
        val pref = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val font = pref.getString("fonts", "font/roboto.ttf")
        numberPicker.setSelectedTypeface(Typeface.createFromAsset(requireContext().assets, font))
        numberPicker.typeface = Typeface.createFromAsset(requireContext().assets, font)
        return view
    }

    private fun onClick() {
        val imm = requireActivity().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            assert(imm != null)
            imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        if (!(numberField1.text.toString() == "" && numberField2.text.toString() == "")) {
            try {
                val prev1 = resultField1.text.toString().toInt()
                val prev2 = resultField2.text.toString().toInt()
                val now1: Int
                val now2: Int
                if (numberField1.text.toString() != "") now1 = numberField1.text.toString().toInt() else {
                    now1 = numberField1.hint.toString().toInt()
                    numberField1.setText(numberField1.hint.toString())
                }
                if (numberField2.text.toString() != "") now2 = numberField2.text.toString().toInt() else {
                    now2 = numberField2.hint.toString().toInt()
                    numberField2.setText(numberField2.hint.toString())
                }
                if (now1 != 0)
                    resultField1.setTextAnimation((prev1 + now1).toString(), 200)
                if (now2 != 0)
                    resultField2.setTextAnimation((prev2 + now2).toString(), 200)
                mainActivity.gameWithGamers.gamers[0].gameScore!!.add(numberField1.text.toString())
                mainActivity.gameWithGamers.gamers[1].gameScore!!.add(numberField2.text.toString())
                mainActivity.gameWithGamers.gamers[0].score = resultField1.text.toString().toInt()
                mainActivity.gameWithGamers.gamers[1].score = resultField2.text.toString().toInt()

                numberField1.setText("")
                numberField2.setText("")
                numberPicker.value = 1


            } catch (ignored: Exception) {
            }
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
                if (numberField1.text.toString() != "")
                    numberField1.setTextAnimation("")
                if (numberField2.text.toString() != "")
                    numberField2.setTextAnimation("")
                counterBolt1.count = 0
                counterBolt2.count = 0

                CoroutineScope(mainActivity.coroutineContext).launch {
                    mainActivity.dao.setEndTime(mainActivity.gameWithGamers.game.id!!, System.currentTimeMillis())
                    mainActivity.dao.addGameToInactive(mainActivity.gameWithGamers.game.id!!)
                    mainActivity.initGame()
                }
                messageBoxInstance.dismiss()
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
        mainActivity.gameWithGamers.gamers[0].score = resultField1.text.toString().toInt()
        mainActivity.gameWithGamers.gamers[1].score = resultField2.text.toString().toInt()
        mainActivity.gameWithGamers.gamers[0].lastRoundScore = numberField1.text.toString()
        mainActivity.gameWithGamers.gamers[1].lastRoundScore = numberField2.text.toString()
        mainActivity.gameWithGamers.gamers[0].bolts = counterBolt1.count
        mainActivity.gameWithGamers.gamers[1].bolts = counterBolt2.count
        CoroutineScope(mainActivity.coroutineContext).launch {
            mainActivity.dao.upsertByReplacementGame(mainActivity.gameWithGamers)
        }
    }

    private fun loadText() {
        name1.text = mainActivity.gameWithGamers.gamers[0].name
        name2.text = mainActivity.gameWithGamers.gamers[1].name
        resultField1.text = mainActivity.gameWithGamers.gamers[0].score.toString()
        resultField2.text = mainActivity.gameWithGamers.gamers[1].score.toString()
        numberField1.setText(mainActivity.gameWithGamers.gamers[0].lastRoundScore)
        numberField2.setText(mainActivity.gameWithGamers.gamers[1].lastRoundScore)
        counterBolt1.count = if (mainActivity.gameWithGamers.gamers[0].bolts == null)
            0
        else
            mainActivity.gameWithGamers.gamers[0].bolts!!

        counterBolt2.count = if (mainActivity.gameWithGamers.gamers[1].bolts == null)
            0
        else
            mainActivity.gameWithGamers.gamers[1].bolts!!

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
                }
            }
        }
    }

    private fun setVisibleBolt(hasBolt: Boolean) {
        counterBolt1.isVisible = hasBolt
        counterBolt2.isVisible = hasBolt
    }
}