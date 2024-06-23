package app.first.my_deb.ui.main

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.ui.menu.MenuActivity
import app.first.my_deb.utils.MyDragShadowBuilder
import app.first.my_deb.utils.OnDragListener
import app.first.my_deb.utils.activateNames
import app.first.my_deb.utils.fadInAnimation
import app.first.my_deb.utils.setTextAnimation
import com.andremion.counterfab.CounterFab
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jaredrummler.cyanea.app.CyaneaFragment
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.coroutines.launch
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

class Fragment2x2 : CyaneaFragment() {

    private lateinit var resultField1: TextView
    private lateinit var resultField2: TextView
    private lateinit var numberField1: EditText
    private lateinit var numberField2: EditText
    private lateinit var box1: TextFieldBoxes
    private lateinit var box2: TextFieldBoxes
    private lateinit var name1: TextView
    private lateinit var name2: TextView
    private lateinit var newButton: Button
    private lateinit var addButton: Button
    private lateinit var numberPicker: NumberPicker
    private lateinit var mainActivity: MainActivity
    private lateinit var counterBolt1: CounterFab
    private lateinit var counterBolt2: CounterFab
    private val data = arrayOf(
        "162", "182", "202", "212", "222", "232", "242", "252",
        "262", "272", "282", "292", "302", "312", "322", "332",
        "342", "362", "382"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_2x2, container, false)

        mainActivity = activity as MainActivity
        numberField1 = view.findViewById(R.id.numberField1)
        numberField2 = view.findViewById(R.id.numberField2)
        resultField1 = view.findViewById(R.id.resultField1)
        resultField2 = view.findViewById(R.id.resultField2)
        name1 = view.findViewById(R.id.name1)
        name2 = view.findViewById(R.id.name2)
        box1 = view.findViewById(R.id.box1) as TextFieldBoxes
        box2 = view.findViewById(R.id.box2) as TextFieldBoxes

        setupViewListeners(view)

        numberPicker = view.findViewById(R.id.number_picker)
        numberPicker.minValue = 1
        numberPicker.maxValue = data.size
        numberPicker.displayedValues = data
        numberPicker.visibility = View.VISIBLE
        numberPicker.setOnValueChangedListener { picker, _, _ -> onPickerValueChange(picker) }
        numberPicker.setOnScrollListener { view, _ -> onPickerScroll(view) }

        numberField1.addTextChangedListener(createTextWatcher {
            updateHints(
                numberField1,
                numberField2,
                numberPicker
            )
        })
        numberField2.addTextChangedListener(createTextWatcher {
            updateHints(
                numberField2,
                numberField1,
                numberPicker
            )
        })

        numberField1.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClick()
                true
            } else {
                false
            }
        }
        numberField2.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClick()
                true
            } else {
                false
            }
        }

        loadText()
        setupFonts()
        view.fadInAnimation(700)
        activateNames(arrayListOf(box1, box2))

        return view
    }

    private fun setupViewListeners(view: View) {
        resultField1.setOnLongClickListener(longClickListener)
        resultField2.setOnLongClickListener(longClickListener)
        resultField1.setOnDragListener(
            OnDragListener(
                mainActivity,
                view,
                arrayListOf(box1, box2),
                activateNames
            )
        )
        resultField2.setOnDragListener(
            OnDragListener(
                mainActivity,
                view,
                arrayListOf(box1, box2),
                activateNames
            )
        )

        newButton = view.findViewById(R.id.button_new)
        newButton.setOnClickListener { onNewClick() }
        addButton = view.findViewById(R.id.button_add)
        addButton.setOnClickListener { onClick() }
        resultField1.setOnClickListener {
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.rotate
                )
            )
        }
        resultField2.setOnClickListener {
            it.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.rotate
                )
            )
        }
        counterBolt1 = view.findViewById(R.id.counter_bolt1) as CounterFab
        counterBolt2 = view.findViewById(R.id.counter_bolt2) as CounterFab

        val calculatorFab: FloatingActionButton = view.findViewById(R.id.calculator_fab)
        calculatorFab.setOnClickListener { mainActivity.onCalcClick() }

        val menuFab: FloatingActionButton = view.findViewById(R.id.menu_fab)
        menuFab.setOnClickListener {
            startActivity(Intent(mainActivity, MenuActivity::class.java))
            mainActivity.overridePendingTransition(R.anim.appear, R.anim.disappear)
            mainActivity.finish()
        }
    }

    private fun createTextWatcher(afterTextChanged: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                afterTextChanged()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    private fun updateHints(
        numberField1: EditText,
        numberField2: EditText,
        numberPicker: NumberPicker
    ) {
        try {
            val value = data[numberPicker.value - 1].toInt()
            if (numberField1.text.toString().isNotEmpty()) {
                val num = numberField1.text.toString().toInt()
                numberField2.hint = (value - num).toString()
            } else if (numberField2.text.toString().isNotEmpty()) {
                val num = numberField2.text.toString().toInt()
                numberField1.hint = (value - num).toString()
            }
        } catch (ignored: Exception) {
        }
    }

    private fun onPickerValueChange(picker: NumberPicker) {
        updateHints(numberField1, numberField2, picker)
        updateHints(numberField2, numberField1, picker)
    }

    private fun onPickerScroll(view: NumberPicker) {
        if (numberField1.text.toString().isNotEmpty() || numberField2.text.toString().isNotEmpty()) {
            try {
                val value = data[view.value - 1].toInt()
                numberField1.hint = value.toString()
                numberField2.hint = value.toString()
            } catch (ignored: Exception) {
            }
        }
    }

    private fun setupFonts() {
        val pref = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val font = pref.getString("fonts", "font/roboto.ttf")
        numberPicker.setSelectedTypeface(Typeface.createFromAsset(requireContext().assets, font))
        numberPicker.typeface = Typeface.createFromAsset(requireContext().assets, font)
    }

    private fun onClick() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
        if (numberField1.text.toString().isNotEmpty() || numberField2.text.toString()
                .isNotEmpty()
        ) {
            try {
                val prev1 = resultField1.text.toString().toInt()
                val prev2 = resultField2.text.toString().toInt()
                val now1 =
                    numberField1.text.toString().toIntOrNull() ?: numberField1.hint.toString()
                        .toInt()
                val now2 =
                    numberField2.text.toString().toIntOrNull() ?: numberField2.hint.toString()
                        .toInt()

                if (now1 != 0) resultField1.setTextAnimation((prev1 + now1).toString(), 200)
                if (now2 != 0) resultField2.setTextAnimation((prev2 + now2).toString(), 200)
                mainActivity.gameWithGamers!!.gamers[0].gameScore!!.add(now1.toString())
                mainActivity.gameWithGamers!!.gamers[1].gameScore!!.add(now2.toString())
                mainActivity.gameWithGamers!!.gamers[0].score = resultField1.text.toString().toInt()
                mainActivity.gameWithGamers!!.gamers[1].score = resultField2.text.toString().toInt()

                numberField1.setText("")
                numberField1.hint = "0"
                numberField2.setText("")
                numberField2.hint = "0"
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
        messageBoxInstance.window?.setBackgroundDrawableResource(R.drawable.message_background)

        messageBoxView.findViewById<Button>(R.id.message_box_yes).setOnClickListener {
            try {
                saveText()
                if (resultField1.text != "0") resultField1.setTextAnimation("0")
                if (resultField2.text != "0") resultField2.setTextAnimation("0")
                if (numberField1.text.toString().isNotEmpty()) numberField1.setTextAnimation("")
                if (numberField2.text.toString().isNotEmpty()) numberField2.setTextAnimation("")
                counterBolt1.count = 0
                counterBolt2.count = 0

                mainActivity.lifecycleScope.launch {
                    mainActivity.dao.setEndTime(
                        mainActivity.gameWithGamers!!.game.id!!,
                        System.currentTimeMillis()
                    )
                    mainActivity.dao.addGameToInactive(mainActivity.gameWithGamers!!.game.id!!)
                    mainActivity.initGame()
                }
                messageBoxInstance.dismiss()
                mainActivity.showRateDialog(requireActivity().applicationContext)
            } catch (ignored: Exception) {
            }
        }

        messageBoxView.findViewById<Button>(R.id.message_box_no).setOnClickListener {
            messageBoxInstance.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveTextWithLifecycleScope()
    }

    override fun onPause() {
        super.onPause()
        saveTextWithLifecycleScope()
    }

    override fun onResume() {
        super.onResume()
        loadText()
        setBoltButtons(
            PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getBoolean("hasBolt", false)
        )
    }

    private fun saveText() {
        mainActivity.gameWithGamers!!.gamers[0].apply {
            name = name1.text.toString()
            score = resultField1.text.toString().toInt()
            lastRoundScore = numberField1.text.toString()
            bolts = counterBolt1.count
        }
        mainActivity.gameWithGamers!!.gamers[1].apply {
            name = name2.text.toString()
            score = resultField2.text.toString().toInt()
            lastRoundScore = numberField2.text.toString()
            bolts = counterBolt2.count
        }
        mainActivity.lifecycleScope.launch {
            mainActivity.dao.upsertByReplacementGame(mainActivity.gameWithGamers!!)
        }
    }

    private fun saveTextWithLifecycleScope() {
        if (view != null && isAdded) {
            saveText()
        }
    }

    private fun loadText() {
        name1.text = mainActivity.gameWithGamers!!.gamers[0].name
        name2.text = mainActivity.gameWithGamers!!.gamers[1].name
        resultField1.text = mainActivity.gameWithGamers!!.gamers[0].score.toString()
        resultField2.text = mainActivity.gameWithGamers!!.gamers[1].score.toString()
        numberField1.setText(mainActivity.gameWithGamers!!.gamers[0].lastRoundScore)
        numberField2.setText(mainActivity.gameWithGamers!!.gamers[1].lastRoundScore)
        counterBolt1.count = mainActivity.gameWithGamers!!.gamers[0].bolts ?: 0
        counterBolt2.count = mainActivity.gameWithGamers!!.gamers[1].bolts ?: 0

        setBoltButtons(
            PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getBoolean("hasBolt", false)
        )
    }

    private fun setBoltButtons(hasBolt: Boolean) {
        setVisibleBolt(hasBolt)
        if (hasBolt) {
            counterBolt1.setOnClickListener {
                handleBoltClick(counterBolt1, 0)
            }
            counterBolt2.setOnClickListener {
                handleBoltClick(counterBolt2, 1)
            }
        }
    }

    private fun handleBoltClick(counterBolt: CounterFab, gamerIndex: Int) {
        val valueOfMinus = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getString("valueOfMinus", "-100")!!.toInt()
        counterBolt.increase()
        if (counterBolt.count == PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getString("countOfNails", "3")!!.toInt()
        ) {
            counterBolt.count = 0
            val resultField = if (gamerIndex == 0) resultField1 else resultField2
            resultField.setTextAnimation(
                (resultField.text.toString().toInt() + valueOfMinus).toString(), 200
            )
            if (PreferenceManager.getDefaultSharedPreferences(requireContext())
                    .getBoolean("addOnBolt", false)
            ) {
                val otherResultField = if (gamerIndex == 0) resultField2 else resultField1
                otherResultField.setTextAnimation(
                    (otherResultField.text.toString()
                        .toInt() + data[numberPicker.value - 1].toInt()).toString(), 200
                )
                mainActivity.gameWithGamers!!.gamers[1 - gamerIndex].gameScore!!.add(data[numberPicker.value - 1])
            } else {
                mainActivity.gameWithGamers!!.gamers[1 - gamerIndex].gameScore!!.add("0")
            }
            mainActivity.gameWithGamers!!.gamers[gamerIndex].gameScore!!.add(valueOfMinus.toString())
        } else if (PreferenceManager.getDefaultSharedPreferences(requireContext())
                .getBoolean("addOnBolt", false)
        ) {
            val otherResultField = if (gamerIndex == 0) resultField2 else resultField1
            otherResultField.setTextAnimation(
                (otherResultField.text.toString()
                    .toInt() + data[numberPicker.value - 1].toInt()).toString(), 200
            )
            mainActivity.gameWithGamers!!.gamers[1 - gamerIndex].gameScore!!.add(data[numberPicker.value - 1])
            mainActivity.gameWithGamers!!.gamers[gamerIndex].gameScore!!.add("0")
        }
    }

    private fun setVisibleBolt(hasBolt: Boolean) {
        counterBolt1.isVisible = hasBolt
        counterBolt2.isVisible = hasBolt
    }

    val longClickListener = View.OnLongClickListener { v ->
        val item = ClipData.Item((v as TextView).x.toString())
        val dragData = ClipData(
            v.text,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )
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