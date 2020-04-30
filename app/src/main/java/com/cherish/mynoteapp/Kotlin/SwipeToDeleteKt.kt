package com.cherish.mynoteapp.Kotlin

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.cherish.mynoteapp.R

abstract class SwipeToDeleteKt(myContext: Context) : ItemTouchHelper.Callback() {
    private var paint:Paint
    private lateinit var  mBackground :ColorDrawable
     lateinit  var icon :Drawable
    private var backgroundColor: Int
    private  var intrinsicWidth :Int
    private  var intrinsicHeight: Int
    lateinit  var mContext :Context

    init {
        this.mBackground = ColorDrawable()
        this.backgroundColor = Color.parseColor("#1E70D1")
        this.paint = Paint()
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        icon = ContextCompat.getDrawable(myContext, R.drawable.ic_delete_black_24dp)!!
        intrinsicWidth = icon.intrinsicWidth
        intrinsicHeight = icon.intrinsicHeight

    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
       return makeMovementFlags(0,ItemTouchHelper.RIGHT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
       return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        var itemView = viewHolder.itemView
        var itemHeight = itemView.height
        var isCancelled  = dX == 0f && !isCurrentlyActive
        if (isCancelled){
            clearCanvas(c, itemView.right + dX , itemView.top.toFloat(), itemView.right.toFloat(),itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        mBackground.color= backgroundColor
        mBackground.setBounds(itemView.left + dX.toInt(),itemView.top, itemView.left,itemView.bottom)
        mBackground.draw(c)


        val deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = itemHeight - intrinsicHeight
        val deleteIconLeft = itemView.left + deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.left + deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        icon.setBounds(deleteIconLeft, deleteIconTop,deleteIconRight, deleteIconBottom)
        icon.draw(c)


    }

    fun clearCanvas(c:Canvas,left:Float, top:Float,right:Float,bottom:Float){
        c.drawRect(left,top,right,bottom,paint)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }


}