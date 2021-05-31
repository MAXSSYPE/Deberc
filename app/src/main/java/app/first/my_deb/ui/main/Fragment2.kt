package app.first.my_deb.ui.main

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.first.my_deb.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shawnlin.numberpicker.NumberPicker
import id.ionbit.ionalert.IonAlert
import java.util.*

class Fragment2 : Fragment() {

    private var arrPlayer1 = ArrayList<String>()
    private var arrPlayer2 = ArrayList<String>()
    private lateinit var resultField1: TextView
    private lateinit var resultField2: TextView
    private lateinit var numberField1: EditText
    private lateinit var numberField2: EditText
    private lateinit var name1: TextView
    private lateinit var name2: TextView
    private lateinit var newButton: Button
    private lateinit var addButton: Button
    private lateinit var numberPicker: NumberPicker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_2, container, false)

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
        loadText(requireContext())
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
                resultField1.text = (prev1 + now1).toString()
                resultField2.text = (prev2 + now2).toString()
                val scoreSharPref: SharedPreferences = requireActivity().getSharedPreferences("Score2.txt", Context.MODE_PRIVATE)
                if (scoreSharPref.getString("pl1", "") != null && scoreSharPref.getString("pl1", "") != "" && scoreSharPref.getString("pl2", "") != null && scoreSharPref.getString("pl2", "") != "") {
                    arrPlayer1 = Gson().fromJson(scoreSharPref.getString("pl1", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
                    arrPlayer2 = Gson().fromJson(scoreSharPref.getString("pl2", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
                }

                val editor = scoreSharPref.edit()
                arrPlayer1.add(numberField1.text.toString())
                arrPlayer2.add(numberField2.text.toString())
                val gson = Gson()
                val listStr1 = gson.toJson(arrPlayer1)
                val listStr2 = gson.toJson(arrPlayer2)
                editor.putString("pl1", listStr1)
                editor.putString("pl2", listStr2)
                editor.apply()
                numberField1.setText("")
                numberField2.setText("")
                numberPicker.value = 1


            } catch (ignored: Exception) {
            }
        }
    }

    private fun onNewClick() {
        IonAlert(requireContext(), IonAlert.WARNING_TYPE)
                .setTitleText(resources.getString(R.string.sure))
                .setContentText(resources.getString(R.string.new_game))
                .setCancelText(resources.getString(R.string.no))
                .setConfirmText(resources.getString(R.string.yes))
                .showCancelButton(true)
                .setConfirmClickListener { sDialog: IonAlert ->
                    resultField1.text = "0"
                    resultField2.text = "0"
                    numberField1.setText("")
                    numberField2.setText("")
                    val scoreSharPref: SharedPreferences = requireActivity().getSharedPreferences("Score2.txt", Context.MODE_PRIVATE)
                    val editor = scoreSharPref.edit().clear()
                    editor.apply()
                    arrPlayer1.clear()
                    arrPlayer2.clear()
                    sDialog.cancel()
                }
                .show()
    }

    override fun onStop() {
        super.onStop()
        saveText()
    }

    override fun onPause() {
        super.onPause()
        saveText()
    }

    private fun saveText() {
        val sPref: SharedPreferences = requireActivity().getSharedPreferences("Save2.txt", Context.MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putString("res1", resultField1.text.toString())
        ed.putString("res2", resultField2.text.toString())
        ed.putString("name1", name1.text.toString())
        ed.putString("name2", name2.text.toString())
        ed.apply()
    }

    private fun loadText(context: Context) {
        val sPref = context.getSharedPreferences("Save2.txt", Context.MODE_PRIVATE)
        name1.text = sPref.getString("name1", "")
        name2.text = sPref.getString("name2", "")
        if (sPref.getString("res1", "0") == "") resultField1.text = "0" else resultField1.text = sPref.getString("res1", "0")
        if (sPref.getString("res2", "0") == "") resultField2.text = "0" else resultField2.text = sPref.getString("res2", "0")
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

    }
}