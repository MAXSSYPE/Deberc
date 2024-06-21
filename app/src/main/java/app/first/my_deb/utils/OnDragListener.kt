package app.first.my_deb.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.DragEvent
import android.view.View
import android.widget.*
import androidx.lifecycle.lifecycleScope
import app.first.my_deb.MainActivity
import app.first.my_deb.R
import com.andremion.counterfab.CounterFab
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import kotlin.math.roundToInt

class OnDragListener(
    var mainActivity: MainActivity,
    var view: View,
    val boxes: List<TextFieldBoxes>,
    var func: (List<TextFieldBoxes>) -> Unit
) : View.OnDragListener {

    override fun onDrag(v: View, event: DragEvent): Boolean {
        val results = view.findViewById<LinearLayout>(R.id.results)
        val names = view.findViewById<LinearLayout>(R.id.names)
        val numbers = view.findViewById<LinearLayout>(R.id.numbers)
        var indexOfAboveView = results.getIndexOfViewByCoordinates(v.x, v.y)!!
        return when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                v.invalidate()
                true
            }

            DragEvent.ACTION_DROP -> {
                val dropped = results.getViewByCoordinates(
                    event.clipData.getItemAt(0).text.toString().toFloat(), v.y
                )
                if (dropped != null) {
                    var indexOfDroppedView = results.getIndexOfViewByCoordinates(
                        event.clipData.getItemAt(0).text.toString().toFloat(), v.y
                    )
                    if (indexOfDroppedView != null && results.getChildAt(indexOfDroppedView) != null) {
                        mainActivity.gameWithGamers.gamers[indexOfAboveView].id =
                            mainActivity.gameWithGamers.gamers[indexOfDroppedView].id.also {
                                mainActivity.gameWithGamers.gamers[indexOfDroppedView].id =
                                    mainActivity.gameWithGamers.gamers[indexOfAboveView].id
                            }

                        mainActivity.gameWithGamers.gamers[indexOfAboveView].bolts =
                            mainActivity.gameWithGamers.gamers[indexOfDroppedView].bolts.also {
                                mainActivity.gameWithGamers.gamers[indexOfDroppedView].bolts =
                                    mainActivity.gameWithGamers.gamers[indexOfAboveView].bolts
                            }

                        mainActivity.gameWithGamers.gamers[indexOfAboveView].gameScore =
                            mainActivity.gameWithGamers.gamers[indexOfDroppedView].gameScore.also {
                                mainActivity.gameWithGamers.gamers[indexOfDroppedView].gameScore =
                                    mainActivity.gameWithGamers.gamers[indexOfAboveView].gameScore
                            }

                        mainActivity.gameWithGamers.gamers[indexOfAboveView].lastRoundScore =
                            mainActivity.gameWithGamers.gamers[indexOfDroppedView].lastRoundScore.also {
                                mainActivity.gameWithGamers.gamers[indexOfDroppedView].lastRoundScore =
                                    mainActivity.gameWithGamers.gamers[indexOfAboveView].lastRoundScore
                            }

                        mainActivity.gameWithGamers.gamers[indexOfAboveView].name =
                            mainActivity.gameWithGamers.gamers[indexOfDroppedView].name.also {
                                mainActivity.gameWithGamers.gamers[indexOfDroppedView].name =
                                    mainActivity.gameWithGamers.gamers[indexOfAboveView].name
                            }

                        mainActivity.gameWithGamers.gamers[indexOfAboveView].score =
                            mainActivity.gameWithGamers.gamers[indexOfDroppedView].score.also {
                                mainActivity.gameWithGamers.gamers[indexOfDroppedView].score =
                                    mainActivity.gameWithGamers.gamers[indexOfAboveView].score
                            }

                        mainActivity.lifecycleScope.launch {
                            mainActivity.saveGame()
                        }

                        (results.getChildAt(indexOfDroppedView) as TextView).text =
                            (results.getChildAt(indexOfAboveView) as TextView).text.also {
                                (results.getChildAt(indexOfAboveView) as TextView).text =
                                    (results.getChildAt(indexOfDroppedView) as TextView).text
                            }

                        val aboveName = names.getChildAt(indexOfAboveView)
                        val droppedName = names.getChildAt(indexOfDroppedView)
                        if (aboveName is TextFieldBoxes && droppedName is TextFieldBoxes) {
                            val score1 =
                                ((droppedName.findViewById<RelativeLayout>(R.id.text_field_boxes_input_layout)).getChildAt(
                                    0
                                ) as ExtendedEditText)
                            val score2 =
                                ((aboveName.findViewById<RelativeLayout>(R.id.text_field_boxes_input_layout)).getChildAt(
                                    0
                                ) as ExtendedEditText)
                            score1.text = score2.text.also {
                                score2.text = score1.text
                            }
                        } else if (aboveName is EditText && droppedName is EditText) {
                            aboveName.text = droppedName.text.also {
                                droppedName.text = aboveName.text
                            }
                        }

                        func(boxes)
                        if ((numbers.getChildAt(1) is NumberPicker)) {
                            if (indexOfAboveView > 0) indexOfAboveView++
                            if (indexOfDroppedView > 0) indexOfDroppedView++
                        }

                        val aboveNumber =
                            ((numbers.getChildAt(indexOfAboveView) as LinearLayout).getChildAt(0) as EditText)
                        val droppedNumber =
                            ((numbers.getChildAt(indexOfDroppedView) as LinearLayout).getChildAt(0) as EditText)
                        if (!((aboveNumber.text.isNullOrEmpty() && droppedNumber.text.isNullOrEmpty()) || (!aboveNumber.text.isNullOrEmpty() && !droppedNumber.text.isNullOrEmpty()))) {
                            if (aboveNumber.text.isNotEmpty()) {
                                droppedNumber.text = aboveNumber.text.also {
                                    aboveNumber.text = droppedNumber.text
                                }
                            } else {
                                aboveNumber.text = droppedNumber.text.also {
                                    droppedNumber.text = aboveNumber.text
                                }
                            }
                        } else {
                            aboveNumber.text = droppedNumber.text.also {
                                droppedNumber.text = aboveNumber.text
                            }
                        }

                        ((numbers.getChildAt(indexOfAboveView) as LinearLayout).getChildAt(1) as CounterFab).count =
                            ((numbers.getChildAt(indexOfDroppedView) as LinearLayout).getChildAt(1) as CounterFab).count.also {
                                ((numbers.getChildAt(indexOfDroppedView) as LinearLayout).getChildAt(
                                    1
                                ) as CounterFab).count =
                                    ((numbers.getChildAt(indexOfAboveView) as LinearLayout).getChildAt(
                                        1
                                    ) as CounterFab).count
                            }

                        runBlocking {
                            println("treeeee " + mainActivity.dao.getActiveGame("1"))
                        }
                    }
                }

                v.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                v.invalidate()
                when (event.result) {
                    true -> {
                    }

                    else -> {
                    }
                }
                true
            }

            else -> {
                false
            }
        }

    }

    fun getTouchPositionFromDragEvent(item: View, event: DragEvent): Point {
        val rItem = Rect()
        item.getGlobalVisibleRect(rItem)
        return Point(rItem.left + event.x.roundToInt(), rItem.top + event.y.roundToInt())
    }
}

class MyDragShadowBuilder(v: View) : View.DragShadowBuilder(v) {

    private val shadow = ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(size: Point, touch: Point) {
        val width: Int = view.width / 2
        val height: Int = view.height / 2
        shadow.setBounds(0, 0, width, height)
        size.set(width, height)
        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {
        shadow.draw(canvas)
    }
}