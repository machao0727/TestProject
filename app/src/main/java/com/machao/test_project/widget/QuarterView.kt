package com.machao.test_project.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec
import androidx.core.content.ContextCompat
import com.machao.test_project.R
import com.machao.test_project.mvp.model.QuarterModel

/**
 * create by：mc on 2021/12/14 20:34
 * email:
 *
 */
class QuarterView: View {
    private var sizeWidth = 0
    private var sizeHeight = 0
    private var padding:Int=20//边距
    private var paint: Paint? = null
    private var textPaint: Paint? = null
    private var quarters:List<QuarterModel> ?=null
    private var mContext: Context?=null
    private var points:MutableList<Point>?=null
    private var laberPoints:MutableList<Point>?=null
    private var drawHeight=0
    private var drawWidth=0

    constructor(context: Context?) : super(context){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context)
    }

    private fun init(context: Context?){
        mContext=context
        paint= Paint()
        paint!!.isAntiAlias=true
        paint!!.style=Paint.Style.STROKE
        paint!!.color=ContextCompat.getColor(mContext!!, R.color.colorPrimary)
        paint!!.strokeWidth=8f

        textPaint= Paint()
        textPaint!!.isAntiAlias=true
        textPaint!!.style=Paint.Style.FILL
        textPaint!!.color=ContextCompat.getColor(mContext!!, R.color.colorPrimary)
        textPaint!!.textAlign=Paint.Align.CENTER
        textPaint!!.textSize=28f
    }

    fun setData(quarters:List<QuarterModel>){
        this.quarters=quarters
        postInvalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingRight - paddingLeft
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        drawHeight=sizeHeight-padding*2
        drawWidth=sizeWidth-padding*2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var max=0.001f//设置最大值为0.001
        quarters!!.forEach {
            max=if (it.volume_of_mobile_data!!.toFloat()>max){
                it.volume_of_mobile_data!!.toFloat()
            }else{
                max
            }
        }
        max+=0.0005f
        points = mutableListOf()
        laberPoints = mutableListOf()
        for (item in quarters!!.withIndex()){
            var point=Point((drawWidth/(quarters!!.size)*(item.index+0.5)).toFloat(),drawHeight-drawHeight*item.value.volume_of_mobile_data!!.toFloat()/max,item.value)
            var laberPoint=Point((drawWidth/(quarters!!.size)*(item.index+0.5)).toFloat(),drawHeight.toFloat(),item.value)
            points!!.add(point)
            laberPoints!!.add(laberPoint)
        }
        var path= Path()
        path.moveTo(points!![0].X,points!![0].Y)
        points!!.forEach {
            path.lineTo(it.X,it.Y)
            canvas!!.drawText(it.data.volume_of_mobile_data!!,it.X
            ,it.Y-40,textPaint!!)
        }
        canvas!!.drawPath(path,paint!!)

    }

    data class Point(var X:Float,var Y:Float,var data:QuarterModel)

}
