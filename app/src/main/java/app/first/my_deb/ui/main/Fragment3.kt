package app.first.my_deb.ui.main

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
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
import id.ionbit.ionalert.IonAlert
import java.util.*

class Fragment3 : Fragment() {

    private lateinit var resultField1: TextView
    private lateinit var resultField2: TextView
    private lateinit var resultField3: TextView
    private lateinit var numberField1: EditText
    private lateinit var numberField2: EditText
    private lateinit var numberField3: EditText
    private lateinit var name1: TextView
    private lateinit var name2: TextView
    private lateinit var name3: TextView
    private var arrPlayer1 = ArrayList<String>()
    private var arrPlayer2 = ArrayList<String>()
    private var arrPlayer3 = ArrayList<String>()
    private lateinit var newButton: Button
    private lateinit var addButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        resultField1 = view.findViewById(R.id.resultField1)
        resultField2 = view.findViewById(R.id.resultField2)
        resultField3 = view.findViewById(R.id.resultField3)
        numberField1 = view.findViewById(R.id.numberField1)
        numberField2 = view.findViewById(R.id.numberField2)
        numberField3 = view.findViewById(R.id.numberField3)
        name1 = view.findViewById(R.id.name1)
        name2 = view.findViewById(R.id.name2)
        name3 = view.findViewById(R.id.name3)
        newButton = view.findViewById(R.id.button_new)
        newButton.setOnClickListener {
            onNewClick()
        }
        addButton = view.findViewById(R.id.button_add)
        addButton.setOnClickListener {
            onClick()
        }

        numberField1!!.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }

        numberField2!!.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }

        numberField3!!.setOnEditorActionListener { _: TextView?, _: Int, _: KeyEvent? ->
            onClick()
            val inputMethodManager = requireActivity().getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    requireActivity().currentFocus!!.windowToken, 0)
        }
        loadText(requireContext())
        return view
    }

    fun onClick() {
        val imm = requireActivity().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            assert(imm != null)
            imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        if (!(numberField1!!.text.toString() == "" && numberField2!!.text.toString() == "" && numberField3!!.text.toString() == "")) try {
            val prev1 = resultField1!!.text.toString().toInt()
            val prev2 = resultField2!!.text.toString().toInt()
            val prev3 = resultField3!!.text.toString().toInt()
            var now1 = 0
            var now2 = 0
            var now3 = 0
            if (numberField1!!.text.toString() != "") now1 = numberField1!!.text.toString().toInt() else numberField1!!.setText("0")
            if (numberField2!!.text.toString() != "") now2 = numberField2!!.text.toString().toInt() else numberField2!!.setText("0")
            if (numberField3!!.text.toString() != "") now3 = numberField3!!.text.toString().toInt() else numberField3!!.setText("0")
            resultField1!!.text = (prev1 + now1).toString()
            resultField2!!.text = (prev2 + now2).toString()
            resultField3!!.text = (prev3 + now3).toString()
            val scoreSharPref: SharedPreferences = requireActivity().getSharedPreferences("Score3.txt", Context.MODE_PRIVATE)
            if (scoreSharPref.getString("pl1", "") != null && scoreSharPref.getString("pl1", "") != "" && scoreSharPref.getString("pl2", "") != null && scoreSharPref.getString("pl2", "") != "" && scoreSharPref.getString("pl3", "") != null && scoreSharPref.getString("pl3", "") != "") {
                arrPlayer1 = Gson().fromJson(scoreSharPref.getString("pl1", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
                arrPlayer2 = Gson().fromJson(scoreSharPref.getString("pl2", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
                arrPlayer3 = Gson().fromJson(scoreSharPref.getString("pl3", ""), object : TypeToken<ArrayList<String?>?>() {}.type)
            }
            val editor = scoreSharPref.edit()
            arrPlayer1.add(numberField1!!.text.toString())
            arrPlayer2.add(numberField2!!.text.toString())
            arrPlayer3.add(numberField3!!.text.toString())
            val gson = Gson()
            val listStr1 = gson.toJson(arrPlayer1)
            val listStr2 = gson.toJson(arrPlayer2)
            val listStr3 = gson.toJson(arrPlayer3)
            editor.putString("pl1", listStr1)
            editor.putString("pl2", listStr2)
            editor.putString("pl3", listStr3)
            editor.apply()
            numberField1!!.setText("")
            numberField2!!.setText("")
            numberField3!!.setText("")
        } catch (ignored: Exception) {
        }
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
        val sPref: SharedPreferences = requireActivity().getSharedPreferences("Save3.txt", Context.MODE_PRIVATE)
        val ed = sPref.edit()
        ed.putString("res1", resultField1!!.text.toString())
        ed.putString("res2", resultField2!!.text.toString())
        ed.putString("res3", resultField3!!.text.toString())
        ed.putString("name1", name1!!.text.toString())
        ed.putString("name2", name2!!.text.toString())
        ed.putString("name3", name3!!.text.toString())
        ed.apply()
    }

    private fun loadText(context: Context) {
        val sPref = context.getSharedPreferences("Save3.txt", Context.MODE_PRIVATE)
        name1!!.text = sPref.getString("name1", "")
        name2!!.text = sPref.getString("name2", "")
        name3!!.text = sPref.getString("name3", "")
        if (sPref.getString("res1", "0") == "") resultField1!!.text = "0" else resultField1!!.text = sPref.getString("res1", "0")
        if (sPref.getString("res2", "0") == "") resultField2!!.text = "0" else resultField2!!.text = sPref.getString("res2", "0")
        if (sPref.getString("res3", "0") == "") resultField3!!.text = "0" else resultField3!!.text = sPref.getString("res3", "0")
    }

    fun onNewClick() {
        IonAlert(requireContext(), IonAlert.WARNING_TYPE)
                .setTitleText(resources.getString(R.string.sure))
                .setContentText(resources.getString(R.string.new_game))
                .setCancelText(resources.getString(R.string.no))
                .setConfirmText(resources.getString(R.string.yes))
                .showCancelButton(true)
                .setConfirmClickListener { sDialog: IonAlert ->
                    resultField1!!.text = "0"
                    resultField2!!.text = "0"
                    resultField3!!.text = "0"
                    numberField1!!.setText("")
                    numberField2!!.setText("")
                    numberField3!!.setText("")
                    val scoreSharPref: SharedPreferences = requireActivity().getSharedPreferences("Score3.txt", Context.MODE_PRIVATE)
                    val editor = scoreSharPref.edit().clear()
                    editor.apply()
                    arrPlayer1.clear()
                    arrPlayer2.clear()
                    arrPlayer3.clear()
                    sDialog.cancel()
                }
                .show()
    }


    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): Fragment3 {
            return Fragment3().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}