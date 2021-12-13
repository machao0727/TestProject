package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * create by：mc on 2018/9/25 14:53
 * email:
 * 平均分配child view
 */
public class ImageLinesView extends ViewGroup {
    public static final int DEFAULT_SPACING = 0;
    public static final int TYPE_SAMEWIDTH = 1;//高度跟随宽度
    public static final int TYPE_SELF = 2;//高度跟随自身

    private int heightType = TYPE_SAMEWIDTH;//默认跟随宽度
    /**
     * 横向间隔
     */
    private int mHorizontalSpacing = 30;
    /**
     * 纵向间隔
     */
    private int mVerticalSpacing = DEFAULT_SPACING;
    /**
     * 图片宽度
     */
    private int ImageWidth = 130;
    /**
     * 图片高度
     */
    private int ImageHeigth = 130;

    /**
     * 每行图片显示数量,默认为0
     * =0根据图片宽高自行自适应显示图片数量
     * >0根据图片数量自适应图片宽高
     */
    private int OneLineCount = 0;
    /**
     * 是否需要布局，只用于第一次
     */
    boolean mNeedLayout = true;
    /**
     * 当前行已用的宽度，由子View宽度加上横向间隔
     */
    private int mUsedWidth = 0;
    /**
     * 代表每一行的集合
     */
    private final List<Line> mLines = new ArrayList<Line>();
    private Line mLine = null;
    /**
     * 最大的行数
     */
    private int mMaxLinesCount = Integer.MAX_VALUE;
    private int surplusWidth;
    private int splitSpacing;
    private int ChildWidth;

    public ImageLinesView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public ImageLinesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public ImageLinesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageLinesView, defStyle, 0);
        mHorizontalSpacing = a.getDimensionPixelOffset(R.styleable.ImageLinesView_HorizontalSpacing, 20);
        mVerticalSpacing = a.getDimensionPixelOffset(R.styleable.ImageLinesView_VerticalSpacing, 30);
        ImageHeigth = a.getDimensionPixelOffset(R.styleable.ImageLinesView_ImageHeight, 130);
        ImageWidth = a.getDimensionPixelOffset(R.styleable.ImageLinesView_ImageWidth, 130);
        OneLineCount = a.getInteger(R.styleable.ImageLinesView_OneLineCounts, 0);
        a.recycle();
    }

    public void setMaxLines(int count) {
        if (mMaxLinesCount != count) {
            mMaxLinesCount = count;
            requestLayoutInner();
        }
    }

    /**
     * 设置单行高度
     */
    public void setSingleHeight(int Type) {
        heightType = Type;
    }

    private void requestLayoutInner() {
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        restoreLine();// 还原数据，以便重新记录
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth, modeWidth == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeWidth);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeHeight, modeHeight == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : modeHeight);
            // 测量child
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            if (mLine == null) {
                mLine = new Line();
            }
            if (OneLineCount == 0) {//根据图片宽高决定一行显示图片数量
                ChildWidth = ImageWidth;
            } else {//根据图片数量决定图片显示宽高
                ChildWidth = (sizeWidth - mHorizontalSpacing * (OneLineCount - 1)) / OneLineCount;
            }
            mUsedWidth += ChildWidth;// 增加使用的宽度
            if (mUsedWidth <= sizeWidth) {// 使用宽度小于总宽度，该child属于这一行。
                mLine.addView(child);// 添加child
                mUsedWidth += mHorizontalSpacing;// 加上间隔
                if (mUsedWidth >= sizeWidth) {// 加上间隔后如果大于等于总宽度，需要换行
                    if (!newLine()) {
                        break;
                    }
                }
            } else {// 使用宽度大于总宽度。需要换行
                if (mLine.getViewCount() == 0) {// 如果这行一个child都没有，即使占用长度超过了总长度，也要加上去，保证每行都有至少有一个child
                    mLine.addView(child);// 添加child
                    if (!newLine()) {// 换行
                        break;
                    }
                } else {// 如果该行有数据了，就直接换行
                    if (!newLine()) {// 换行
                        break;
                    }
                    // 在新的一行，不管是否超过长度，先加上去，因为这一行一个child都没有，所以必须满足每行至少有一个child
                    mLine.addView(child);
                    mUsedWidth += ChildWidth + mHorizontalSpacing;
                }
            }
        }

        if (mLine != null && mLine.getViewCount() > 0 && !mLines.contains(mLine)) {
            // 由于前面采用判断长度是否超过最大宽度来决定是否换行，则最后一行可能因为还没达到最大宽度，所以需要验证后加入集合中
            mLines.add(mLine);
        }

        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int totalHeight = 0;
        final int linesCount = mLines.size();
        for (int i = 0; i < linesCount; i++) {// 加上所有行的高度
            totalHeight += mLines.get(i).mHeight;
        }
        totalHeight += mVerticalSpacing * (linesCount - 1);// 加上所有间隔的高度
        totalHeight += getPaddingTop() + getPaddingBottom();// 加上padding
        // 设置布局的宽高，宽度直接采用父view传递过来的最大宽度，而不用考虑子view是否填满宽度，因为该布局的特性就是填满一行后，再换行
        // 高度根据设置的模式来决定采用所有子View的高度之和还是采用父view传递过来的高度
        setMeasuredDimension(totalWidth, resolveSize(totalHeight, heightMeasureSpec));
//        setMeasuredDimension(totalWidth, totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!mNeedLayout || changed) {//没有发生改变就不重新布局
            mNeedLayout = false;
            int left = getPaddingLeft();//获取最初的左上点
            int top = getPaddingTop();
            final int linesCount = mLines.size();
            for (int i = 0; i < linesCount; i++) {
                final Line oneLine = mLines.get(i);
                if (i == 0) {
                    oneLine.layoutView(left, top, true, linesCount);//布局第一行时计算剩余宽度
                } else {
                    oneLine.layoutView(left, top, false, linesCount);//布局每一行
                }

                top += oneLine.mHeight + mVerticalSpacing;//为下一行的top赋值
            }
        }
        invalidate();
    }

    /**
     * 还原所有数据
     */
    private void restoreLine() {
        mLines.clear();
        mLine = new Line();
        mUsedWidth = 0;
    }

    /**
     * 新增加一行
     */
    private boolean newLine() {
        mLines.add(mLine);
        if (mLines.size() < mMaxLinesCount) {
            mLine = new Line();
            mUsedWidth = 0;
            return true;
        }
        return false;
    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================

    /**
     * 代表着一行，封装了一行所占高度，该行子View的集合，以及所有View的宽度总和
     */
    class Line {
        int mWidth = 0;// 该行中所有的子View累加的宽度
        int mHeight = 0;// 该行中所有的子View中高度的那个子View的高度
        List<View> views = new ArrayList<View>();

        public void addView(View view) {// 往该行中添加一个
            views.add(view);
            mWidth += ChildWidth;
            int childHeight;
            if (heightType == TYPE_SAMEWIDTH) childHeight = ChildWidth;
            else childHeight = view.getMeasuredHeight();//注意要添加测量规则才能测量出正确高度
            mHeight = mHeight < childHeight ? childHeight : mHeight;//高度等于一行中最高的View
        }

        public int getViewCount() {
            return views.size();
        }

        public void layoutView(int l, int t, boolean b, int linescount) {// 布局
            int left = l;
            int top = t;
            int count = getViewCount();
            //总宽度
            int layoutWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            //剩余的宽度，是除了View和间隙的剩余空间
            if (b) {//使每排的间隔都跟第一排相同
                if (count > 1) {//防止divide by zero
                    surplusWidth = layoutWidth - mWidth - mHorizontalSpacing * (count - 1);
                    splitSpacing = (int) (surplusWidth / count);
                }
            }
            if (linescount == 1) {//整个布局只有一行，不进行平均分配强制把剩余间隔变为0
                splitSpacing = 0;
            }
            if (splitSpacing >= 0) {// 剩余空间
                // 采用float类型数据计算后四舍五入能减少int类型计算带来的误差
                for (int i = 0; i < count; i++) {
                    final View view = views.get(i);
                    int childWidth = ChildWidth;
                    int childHeight = mHeight;
                    //计算出每个View的顶点，是由最高的View和该View高度的差值除以2
                    int topOffset = (int) ((mHeight - childHeight) / 2.0 + 0.5);
                    if (topOffset < 0) {
                        topOffset = 0;
                    }
                    //把剩余空间平均到每个View上
                    ChildWidth = ChildWidth + splitSpacing;
                    view.getLayoutParams().width = childWidth;
                    //如果view仅为一个imageview则不需要重新测量，但是如果view是一个布局，里面还有子布局，那么就需要重新测量，使子布局也要进行重新布局
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                    view.measure(widthMeasureSpec, heightMeasureSpec);
                    //布局View
                    view.layout(left, top + topOffset, left + childWidth, top + topOffset + childHeight);
                    left += childWidth + splitSpacing + mHorizontalSpacing; //为下一个View的left赋值
                }
            } else {
                if (count == 1) {
                    View view = views.get(0);
                    view.layout(left, top, left + view.getMeasuredWidth(), top + mHeight);
                } else {
                    // 走到这里来，应该是代码出问题了，目前按照逻辑来看，是不可能走到这一步
                }
            }
        }
    }
}
