package com.sbuddy.sbdApp.util

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        var height = 0

        var lineWidth = 0
        var lineHeight = 0

        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (lineWidth + childWidth > widthSize) {
                width = maxOf(width, lineWidth)
                height += lineHeight
                lineWidth = childWidth
                lineHeight = childHeight
            } else {
                lineWidth += childWidth
                lineHeight = maxOf(lineHeight, childHeight)
            }
        }

        width = maxOf(width, lineWidth)
        height += lineHeight

        setMeasuredDimension(
            if (widthMode == MeasureSpec.EXACTLY) widthSize else width,
            if (heightMode == MeasureSpec.EXACTLY) heightSize else height
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = width
        var lineWidth = 0
        var lineHeight = 0
        var top = 0

        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            if (lineWidth + childWidth > width) {
                lineWidth = 0
                top += lineHeight
                lineHeight = childHeight
            } else {
                lineHeight = maxOf(lineHeight, childHeight)
            }

            val left = lineWidth + lp.leftMargin
            val topOffset = top + lp.topMargin
            child.layout(left, topOffset, left + child.measuredWidth, topOffset + child.measuredHeight)
            lineWidth += childWidth
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    class LayoutParams : MarginLayoutParams {
        constructor(width: Int, height: Int) : super(width, height)
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    }

    companion object {
        // Define WRAP_CONTENT and MATCH_PARENT for FlowLayout
        val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT
        val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    }
}
