package com.example.miniproject

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class CustomDecoration(
    private val height: Int, @ColorInt private val color: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        myDivider(c, parent, color)
    }

    private fun myDivider(c: Canvas, parent: RecyclerView, color: Int) {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val param = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + param.bottomMargin
            val dividerBottom = dividerTop + height
            c.drawRect(
                child.left.toFloat(),
                dividerTop.toFloat(),
                child.right.toFloat(),
                dividerBottom.toFloat(),
                paint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = height
    }
}
