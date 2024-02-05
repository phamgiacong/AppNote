package com.cong.noteapp.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint
import android.graphics.Path
import androidx.core.content.ContextCompat
import com.cong.noteapp.R

class CustomCalendar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val calendarPaint = Paint()
        val calendarPath = Path()

        val xMin = 0f
        val yMin = 0f

        val xMax = width / 1f
        val yMax = height / 1f

        val xCenter = width / 2f

        calendarPaint.color = ContextCompat.getColor(context, R.color.background_dialog)
        calendarPaint.style = Paint.Style.FILL

        calendarPath.reset()
        //first corner
        calendarPath.moveTo(xMin, yMin)

        //second corner
        calendarPath.lineTo(xMax, yMin)

        //third corner
        calendarPath.lineTo(xMax, yMax - 100f)
        calendarPath.quadTo(xMax, yMax - 20f, xMax - 100f, yMax)

        //middle border radius
        calendarPath.cubicTo(xCenter + 140f, yMax, xCenter + 90f, yMax - 2f, xCenter + 70f, yMax - 20f)
        calendarPath.cubicTo(xCenter + 70f, yMax - 20f, xCenter, yMax - 80f, xCenter - 70f, yMax - 20f)
        calendarPath.cubicTo(xCenter - 70f, yMax - 20f, xCenter - 90f, yMax - 2f, xCenter - 140f, yMax)

        //fourth corner
        calendarPath.lineTo(xMin + 100f, yMax)
        calendarPath.quadTo(xMin, yMax - 20f, xMin, yMax - 100f)

        //return to first corner
        calendarPath.lineTo(xMin, yMin)

        canvas.drawPath(calendarPath, calendarPaint)

        canvas.drawOval(
            xCenter - 25f,
            yMax - 30f,
            xCenter + 25f,
            yMax,
            calendarPaint
        )

    }
}