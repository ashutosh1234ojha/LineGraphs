package com.example.graphs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.NotNull;

public class LineGraphView extends View {
    private float[] values;
    private String[] horLabels;
    private String[] verLabels;
    private Paint paint;

    public LineGraphView(Context context) {
        super(context);
        paint = new Paint();

    }

    public LineGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public LineGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    public LineGraphView(Context context, float[] values, String[] horLabels, String[] verLabels) {
        super(context);
        if (values == null)
            values = new float[0];
        else
            this.values = values;
        if (horLabels == null)
            this.horLabels = new String[0];
        else
            this.horLabels = horLabels;
        if (verLabels == null)
            this.verLabels = new String[0];
        else
            this.verLabels = verLabels;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float border = 20;
        float horstart = border * 2;
        float height = getHeight();
        float width = getWidth() - 1;
        float max = getMax();
        float min = getMin();
        float diff = max - min;
        float graphheight = height - (2 * border);
        float graphwidth = width - (2 * border);

        paint.setTextAlign(Paint.Align.LEFT);
        int vers = verLabels.length - 1;
        for (int i = 0; i < verLabels.length; i++) {
            paint.setColor(Color.DKGRAY);
            float y = ((graphheight / vers) * i) + border;
            canvas.drawLine(horstart, y, width, y, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText(verLabels[i], 0, y, paint);
        }
        int hors = horLabels.length - 1;
        for (int i = 0; i < horLabels.length; i++) {
            float x = ((graphwidth / hors) * i) + horstart;
            paint.setColor(Color.DKGRAY);
            canvas.drawLine(x, height - border, x, border, paint);
            paint.setTextAlign(Paint.Align.CENTER);
            if (i == horLabels.length - 1)
                paint.setTextAlign(Paint.Align.RIGHT);
            if (i == 0)
                paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.BLACK);
            canvas.drawText(horLabels[i], x, height - 4, paint);
        }
    }

    private float getMax() {
        float largest = Integer.MIN_VALUE;
        for (int i = 0; i < values.length; i++)
            if (values[i] > largest)
                largest = values[i];
        return largest;
    }

    private float getMin() {
        float smallest = Integer.MAX_VALUE;
        for (int i = 0; i < values.length; i++)
            if (values[i] < smallest)
                smallest = values[i];
        return smallest;
    }

    public void setHorLabels(String[] horLabels) {
        this.horLabels = horLabels;
        invalidate();
    }

    public void setValues(@NotNull float[] values) {
        this.values = values;
        invalidate();
    }

    public void setVerLabels(@NotNull String[] verLabels) {
        this.verLabels = verLabels;
        invalidate();
    }

}
