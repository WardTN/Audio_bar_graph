package lbstest.example.com.pptest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chen on 17-12-18.
 */

public class AudioBarGraph extends View {
    //渐变顶部颜色
    private int mTopColor;
    //渐变底部颜色
    private int mBottomColor;
    //View重绘延时时间
    private  int mDelayTime;

    //小矩形的数目
    private int mRectCount;
    //每个小矩形的宽度
    private int mRectWidth;
    //每个小矩形的高度
    private int mRectHeight;
    //每个小矩形之间的偏移量
    private float mOffset;
    //绘制小矩形的画笔
    private Paint mPaint;
    //View的宽度
    private int mViewWidth;
    //产生渐变效果
    private LinearGradient mLinearGradient;
    //每个小矩形的高度
    private float[] mCurrentHeight;


    public AudioBarGraph(Context context) {
        this(context,null);
    }

    public AudioBarGraph(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AudioBarGraph(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //画笔初始化及资源的初始化
        mOffset = 5;
        mRectCount = 10;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.AudioBarGraph);
        mRectCount = ta.getInt(R.styleable.AudioBarGraph_rectCount,10); //矩形数目
        mOffset = ta.getFloat(R.styleable.AudioBarGraph_rectOffset,3);  //偏移量
        mDelayTime = ta.getInt(R.styleable.AudioBarGraph_delayTime,300);    //延迟时间
        mTopColor = ta.getColor(R.styleable.AudioBarGraph_bottomColor,Color.RED); //渐变的顶部颜色
        mBottomColor = ta.getColor(R.styleable.AudioBarGraph_bottomColor,Color.BLUE);   //渐变的底部颜色
        ta.recycle();   //资源回收
    }

    @Override   //在Wrap_Content 上有个默认的宽高
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 200;
        int height = 200;

        setMeasuredDimension(widthMode ==  MeasureSpec.EXACTLY ?widthSize:width,heightMeasureSpec == MeasureSpec.EXACTLY?heightSize:height);

    }

    @Override   //获取View的宽高，及初始化一个LinerGradient ,为OnDraw做准备
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth();    //
        mRectHeight = getMeasuredHeight();
        mRectWidth = (int) (mViewWidth * 0.6 / mRectCount);
        mLinearGradient = new LinearGradient(0,0,mRectWidth,mRectHeight,mTopColor,mBottomColor, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }
    //公开方法设置小矩形高度
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCurrentHeight != null){
            //使用者指定了每个小矩形当前的高度则使用
            for(int i=0;i<mRectCount;i++){
                int random = (int)(Math.random()*50);

                canvas.drawRect((float) (mViewWidth * 0.4 / 2 + mRectWidth * i + mOffset), mCurrentHeight[i]+random,
                        (float) (mViewWidth * 0.4 / 2 + mRectWidth * (i + 1)), mRectHeight, mPaint);    //left,top,right,bottom
                   }
        }else {
            //没有指定则使用随机数的高度
            for(int i=0;i<mRectCount;i++){
                int currentHeight = 0;
                canvas.drawRect((float) (mViewWidth*0.4/2+mRectWidth*i+mOffset),currentHeight,(float)(mViewWidth * 0.4/2+mRectWidth * (i+1)),mRectHeight,mPaint);
            }
        }
        postInvalidateDelayed(mDelayTime);//延时
    }

    public void setmCurrentHeight(float[] currentHeight){
        mCurrentHeight = currentHeight;
    }   //设置高度
}
