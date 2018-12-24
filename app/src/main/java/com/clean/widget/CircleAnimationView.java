package com.clean.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cleartool.R;


/**
 * 圆形线框等动画
 * 包含中间的一张图片
 * @author hejianjun
 */
public class CircleAnimationView extends FrameLayout {
    private Paint paintOut;//外圈画笔
    private Paint paintIn,paintIn2;//内圈画笔

    private Bitmap bitmap;
    private int centerDrawableId = R.mipmap.ico_clean;//中心图片ID

    private ImageView im_Center,im_in,im_out;//中心图片控件，


    private static final int ANIMATION_DURATION = 500;//动画间隔
    private RotateAnimation rotateAnimation;//旋转动画
    private ScaleAnimation scaleAnimation;//放大缩小动画

    private int PARENT_WIDTH = 400;//整个View的宽度
    private int imageCenterWidth = 200;//中心图片的宽度
    private int imageCenterHeight = 200;//中心图片的高度

    private int LineStrokeWidth = 8;//线框的宽度


    public CircleAnimationView(Context context) {
        this(context,null);
    }

    public CircleAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CircleAnimationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);

    }

    private void init(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleAnimationView);
        if (typedArray != null){
            centerDrawableId = typedArray.getResourceId(R.styleable.CircleAnimationView_image,R.mipmap.ico_clean);
            typedArray.recycle();
        }
        bitmap = BitmapFactory.decodeResource(getResources(),centerDrawableId);
        initPaint();
        initAnimation();
        initViews();
    }

    /**
     * 初始化Paint
     */
    private void initPaint(){
        paintOut = new Paint();
        paintOut.setAntiAlias(true);//抗锯齿
        paintOut.setDither(true);//防抖
        paintOut.setStyle(Paint.Style.STROKE);//空心圆
        paintOut.setStrokeWidth(LineStrokeWidth);//线的宽度
        paintOut.setColor(Color.parseColor("#1a00aaff")); // 设置圆弧的颜色

        paintIn = new Paint();
        paintIn.setAntiAlias(true);
        paintIn.setDither(true);
        paintIn.setStyle(Paint.Style.STROKE);
        paintIn.setStrokeWidth(LineStrokeWidth);
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 200,
                Color.parseColor("#11d4ff"), Color.parseColor("#0000b3ff"), Shader.TileMode.MIRROR);//渐变色
        paintIn.setShader(linearGradient);

        paintIn2 = new Paint();
        paintIn2.setAntiAlias(true);
        paintIn2.setDither(true);
        paintIn2.setStyle(Paint.Style.STROKE);
        paintIn2.setStrokeWidth(LineStrokeWidth);
        LinearGradient linearGradient2 = new LinearGradient(0, 0, 0, 200,
                Color.parseColor("#0000b3ff"), Color.parseColor("#11d4ff"), Shader.TileMode.MIRROR);//渐变色
        paintIn2.setShader(linearGradient2);
    }

    /**
     * 初始化动画
     */
    private void initAnimation(){
        rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        rotateAnimation.setDuration(ANIMATION_DURATION);//设置动画持续时间
        rotateAnimation.setRepeatCount(-1);//设置重复次数
        rotateAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotateAnimation.setStartOffset(10);//执行前的等待时间


        scaleAnimation = new ScaleAnimation(
                0.8f, 1.2f, 0.8f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setRepeatMode(Animation.REVERSE);//往复模式
        scaleAnimation.setRepeatCount(Animation.INFINITE);//无限次数
        scaleAnimation.setDuration(ANIMATION_DURATION);

    }

    /**
     * 初始化View
     */
    private void initViews(){

        LayoutParams layoutParams = new LayoutParams(imageCenterWidth,imageCenterHeight);
        layoutParams.gravity = Gravity.CENTER;

        //中心的那张图片
        im_Center= new ImageView(getContext());
        im_Center.setImageBitmap(bitmap);
        addView(im_Center,layoutParams);

        //旋转线框（内圈）
        im_in = new ImageView(getContext());
        int inWidth = imageCenterWidth+45;
        int inHeight = imageCenterHeight+45;
        Bitmap bitmapIn = Bitmap.createBitmap(inWidth,inHeight,Bitmap.Config.ARGB_8888);
        Canvas canvasIN = new Canvas(bitmapIn);
        RectF rectF = new RectF(LineStrokeWidth,LineStrokeWidth,inWidth-LineStrokeWidth,inHeight-LineStrokeWidth);
        canvasIN.drawArc(rectF,-90,180,false,paintIn);
        canvasIN.drawArc(rectF,90,180,false,paintIn2);
        im_in.setImageBitmap(bitmapIn);
        LayoutParams layoutParamsIn = new LayoutParams(inWidth,inHeight);
        layoutParamsIn.gravity = Gravity.CENTER;
        addView(im_in,layoutParamsIn);

        //放大缩小线框
        im_out = new ImageView(getContext());
        int outWidth = inWidth +50;
        int outHeight = inWidth + 50;
        Bitmap bitmapOut = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas canvasOut = new Canvas(bitmapOut);
        canvasOut.drawCircle(outWidth/2,outHeight/2,(outWidth-LineStrokeWidth)/2,paintOut);
        im_out.setImageBitmap(bitmapOut);
        LayoutParams layoutParamsOut = new LayoutParams(outWidth,outHeight);
        layoutParamsOut.gravity = Gravity.CENTER;
        addView(im_out,layoutParamsOut);
    }

    /**
     * 替换中心的图片
     * @param drawableId
     */
    public void setCenterImageView(int drawableId){
        if (im_Center != null){
            im_Center.setImageResource(drawableId);
        }
    }

    /**
     * 开始动画
     */
    public void startAnimation(){
        im_in.startAnimation(rotateAnimation);
        im_out.startAnimation(scaleAnimation);
    }

    public void stopAnimation(){
        scaleAnimation.cancel();
        rotateAnimation.cancel();
    }

    public void clear(){
        stopAnimation();
    }

}
