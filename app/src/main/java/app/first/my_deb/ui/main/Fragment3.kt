package app.first.my_deb.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import app.first.my_deb.ui.menu.MenuActivity
import app.first.my_deb.utils.activateNames
import app.first.my_deb.utils.fadInAnimation
import app.first.my_deb.utils.loadText
import app.first.my_deb.utils.saveText
import app.first.my_deb.utils.setupButtons
import app.first.my_deb.utils.setupNamesAndResults
import app.first.my_deb.utils.setupNumberFields
import com.andremion.counterfab.CounterFab
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jaredrummler.cyanea.app.CyaneaFragment
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

class Fragment3 : CyaneaFragment() {

    private val resultFields = ArrayList<TextView>()
    private val names = ArrayList<TextView>()
    private val numberFields = ArrayList<EditText>()
    private val counterBolts = ArrayList<CounterFab>()
    private val boxes = ArrayList<TextFieldBoxes>()
    private lateinit var newButton: Button
    private lateinit var addButton: Button
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3, container, false)

        mainActivity = activity as MainActivity

        resultFields.add(view.findViewById(R.id.resultField1))
        resultFields.add(view.findViewById(R.id.resultField2))
        resultFields.add(view.findViewById(R.id.resultField3))

        numberFields.add(view.findViewById(R.id.numberField1))
        numberFields.add(view.findViewById(R.id.numberField2))
        numberFields.add(view.findViewById(R.id.numberField3))

        names.add(view.findViewById(R.id.name1))
        names.add(view.findViewById(R.id.name2))
        names.add(view.findViewById(R.id.name3))

        counterBolts.add(view.findViewById(R.id.counter_bolt1) as CounterFab)
        counterBolts.add(view.findViewById(R.id.counter_bolt2) as CounterFab)
        counterBolts.add(view.findViewById(R.id.counter_bolt3) as CounterFab)

        boxes.add(view.findViewById(R.id.box1) as TextFieldBoxes)
        boxes.add(view.findViewById(R.id.box2) as TextFieldBoxes)
        boxes.add(view.findViewById(R.id.box3) as TextFieldBoxes)

        newButton = view.findViewById(R.id.button_new)
        addButton = view.findViewById(R.id.button_add)

        setupButtons(
            requireActivity(),
            mainActivity,
            resultFields,
            numberFields,
            names,
            counterBolts,
            newButton,
            addButton,
            viewLifecycleOwner.lifecycleScope
        )
        setupNumberFields(requireActivity(), mainActivity, resultFields, numberFields)
        setupNamesAndResults(
            requireActivity(),
            mainActivity,
            view,
            resultFields,
            numberFields,
            names,
            boxes
        )

        loadText(
            mainActivity,
            requireContext(),
            resultFields,
            numberFields,
            names,
            counterBolts
        )
        view.fadInAnimation(700)
        activateNames(boxes)

        val calculatorFab: FloatingActionButton = view.findViewById(R.id.calculator_fab)
        calculatorFab.setOnClickListener {
            mainActivity.onCalcClick()
        }
        val menuFab: FloatingActionButton = view.findViewById(R.id.menu_fab)
        menuFab.setOnClickListener {
            startActivity(Intent(mainActivity, MenuActivity::class.java))
            mainActivity.overridePendingTransition(R.anim.appear, R.anim.disappear)
            mainActivity.finish()
        }
        return view
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
        loadText(
            mainActivity,
            requireContext(),
            resultFields,
            numberFields,
            names,
            counterBolts
        )
    }

    private fun saveTextWithLifecycleScope() {
        if (view != null && isAdded) {
            saveText(
                mainActivity,
                resultFields,
                numberFields,
                names,
                counterBolts,
                viewLifecycleOwner.lifecycleScope
            )
        }
    }
}
