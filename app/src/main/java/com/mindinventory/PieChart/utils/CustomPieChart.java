package com.mindinventory.PieChart.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import com.mindinventory.PieChart.R;
import com.mindinventory.PieChart.model.PieData;

import java.util.List;

/**
 * Created by MindInventory.
 * CustomPieChart : custom view to show a pie chart on a display.
 */

public class CustomPieChart extends View {

    RectF pieBound;
    Context mContext;
    float total_values_provided;
    float width_available = 0f;
    float height_available = 0f;
    float diameter = 0f;
    Bitmap myBitmap;
    private List<PieData> pieDataList;
    private String tag = CustomPieChart.class.getCanonicalName();
    private float angleAlreadyCovered;
    private int mGravity = Gravity.START | Gravity.TOP;
    private int paddingBetweenChartAndTitle = 0;
    private float textSize = 40f;
    private String chartName;
    private float strokeWidth = 0f;
    private boolean isStrokeEnable = true;

    /**
     * Constructor with add context value
     * @param context
     */
    public CustomPieChart(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor with add context value & AttributeSet
     * @param context
     * @param attrs
     */
    public CustomPieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Constructor with add context value & AttributeSet defStyle
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomPieChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeEnable(boolean strokeEnable) {
        isStrokeEnable = strokeEnable;
    }


    public void setStroke(boolean isStrokeEnable, float strokeWidth) {
        this.isStrokeEnable = isStrokeEnable;
        this.strokeWidth = strokeWidth;
        invalidate();
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setPaddingBetweenChartAndTitle(int paddingBetweenChartAndTitle) {
        this.paddingBetweenChartAndTitle = paddingBetweenChartAndTitle;
    }


    /**
     * we are setting data into PieChart.
     * @param pieDataList
     * @param chartName : Name of PieChart
     */
    public void setPieDataList(List<PieData> pieDataList, String chartName) {
        resetData();
        this.pieDataList = pieDataList;
        setChartName(chartName);
        //to refresh the data using invalidate();
        invalidate();
    }

    /**
     * Method for set size of Pie Chart as initialized by user
     * @param width : width of PieChart
     * @param height : height of PieChart
     * @param oldw : Old Width of PieChart
     * @param oldh : Old Height of PieChart
     */
    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        // Account for padding
        int leftPadding = getPaddingLeft();
        int topPadding = getPaddingTop();
        int rightPadding = getPaddingRight();
        int bottomPadding = getPaddingBottom();

        float horizontalPadding = (float) (leftPadding + rightPadding);
        float verticalPadding = (float) (topPadding + bottomPadding);

        width_available = width - horizontalPadding;
        height_available = height - verticalPadding;

        diameter = Math.min(width_available, height_available);

        pieBound = new RectF(0f, 0f, diameter, diameter);

        // here is the example of how to handle gravity
        // for the record base we have checked for the center gravity
        // please note that we have kept START|TOP as gravity.
        if (mGravity == Gravity.CENTER) {
            leftPadding += (int) ((width_available - diameter) / 2);
            topPadding += (int) ((height_available - diameter) / 2);
        }
        // here we are moving our rect with reference to the left and top padding
        // and rest of the things we have managed while finding diameter.
        pieBound.offsetTo(leftPadding, topPadding);

        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        myBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        // resetting data which we may used for previous draw.
        resetData();

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.CustomView, 0, 0);
            try {
                mGravity = a.getInteger(R.styleable.CustomView_android_gravity, Gravity.TOP | Gravity.START);
            } finally {
                a.recycle();
            }
        }
    }


    /**
     * we are resetting values which we have used for every data has been set.
     */
    private void resetData() {
        this.total_values_provided = 0f;
        this.angleAlreadyCovered = 0f;
        this.width_available = 0f;
        this.height_available = 0f;
    }

    /**
     * We are drawing a Pie chart from this method as per given data.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw background circle anyway
        Paint background = getColoredPaint(android.R.color.holo_blue_bright);
        canvas.drawArc(pieBound, 0, 360, true, background);

        // here we are calculating total count of values shared by user.
        // please note that we need to calculate this if below condition satisfy otherwise it will calculate many times.
        if (total_values_provided == 0f) {
            for (PieData pieData : pieDataList) {
                total_values_provided += pieData.getPercentage();
            }
        }

        // ============================== ARC ==============================
        if (pieDataList != null && !pieDataList.isEmpty()) {
            int size = pieDataList.size();
            for (int i = 0; i < size; i++) {
                PieData pieData = pieDataList.get(i);
                if (pieData != null) {
                    float swipeAngle = getSwipeAngleFromPercentage(pieData.getPercentage());
                    canvas.drawArc(pieBound,
                            angleAlreadyCovered,
                            swipeAngle,
                            true,
                            getColoredPaint(pieData.getColor()));

                    // we are managing this variable so that we will draw next block,
                    // we can come to know from where to start drawing arc exactly.
                    angleAlreadyCovered += swipeAngle;
                }
            }
        }

        // here we are drawing stroke.
        // ============================== STROKE ==============================
        background.setStyle(Paint.Style.STROKE);
        background.setStrokeWidth(strokeWidth);
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, (diameter / 2), background);


        // here we are drawing rectangle.
        // ============================== RECTANGLE ==============================
        background.setStyle(Paint.Style.FILL);
        background.setColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
        canvas.drawRect(0f, canvas.getHeight() - 100f, canvas.getWidth(), canvas.getHeight(), background);


        // Here we are drawing text which we required.
        // ============================== TEXT ==============================
        if (chartName != null && !chartName.isEmpty()) {
            background.setTextSize(textSize);
            Paint.FontMetrics fm = background.getFontMetrics();
            float height = (fm.descent - fm.ascent) * 1.25f;

            float yPos = canvas.getHeight() - ((100f - height));
            float xPos = (canvas.getWidth() / 2);

            background.setTextAlign(Paint.Align.CENTER);
            background.setColor(mContext.getResources().getColor(android.R.color.white));
            background.setTextSize(textSize);
            canvas.drawText(chartName, xPos, yPos, background);
        }

        // here we are drawing bitmap.
        // ============================== BITMAP ==============================
        if (myBitmap != null) {
            float bitmapWidth = myBitmap.getWidth();
            canvas.drawBitmap(myBitmap, (canvas.getWidth() - bitmapWidth) / 2, (canvas.getHeight() - bitmapWidth) / 2, null);
        }
    }

    /**
     * this method is used to get Swipe Angle from the percentage.
     * @param percentage
     * @return
     */
    private float getSwipeAngleFromPercentage(float percentage) {
        if (total_values_provided == 0f) return 0f;

        return (float) (((double) 360 * (double) percentage) / total_values_provided);
    }


    /**
     * it provides Paint according to passed color and paint style in the params.
     * @param color
     * @param style
     * @return
     */
    private Paint getColoredPaint(int color, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(mContext.getResources().getColor(color));
        paint.setAntiAlias(true);
        paint.setStyle(style);
        return paint;
    }

    /**
     * it provides Paint according to passed color in the params. By default we will create paint with FILL paint style.
     * @param color
     * @return
     */
    private Paint getColoredPaint(int color) {
        return getColoredPaint(color, Paint.Style.FILL);
    }
}
