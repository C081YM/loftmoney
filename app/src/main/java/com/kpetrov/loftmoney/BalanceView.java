package com.kpetrov.loftmoney;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class BalanceView extends View {

    private float expenses = 35000;
    private float incomes = 74000;

    private Paint expensePaint = new Paint();
    private Paint incomePaint = new Paint();

    private int colorExpense;
    private int colorIncome;

    public BalanceView(Context context) {
        super(context);
        init(null);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void update (float expenses, float incomes) {
        this.expenses = expenses;
        this.incomes = incomes;

        invalidate();                                                         //заставляет view перерисовываться
    }

    private void init(@Nullable AttributeSet set) {

        if (set == null)
            return;

        TypedArray typedArray = getContext().obtainStyledAttributes(set,R.styleable.BalanceView);

        colorExpense = typedArray.getColor(R.styleable.BalanceView_diagramColorExpense,0);
        colorIncome = typedArray.getColor(R.styleable.BalanceView_diagramColorIncome,0);

        expensePaint.setColor(colorExpense);
        incomePaint.setColor(colorIncome);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = expenses + incomes;

        float expenseAngle = 360f * expenses / total;
        float incomeAngle = 360f * incomes / total;

        int space = 15;

        int size = Math.min(getWidth(),getHeight()) - space * 2;

        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin, getWidth() - xMargin - space,
                getHeight() - yMargin, 180 - expenseAngle / 2, expenseAngle, true, expensePaint);

        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space,
                getHeight() - yMargin, 360 - incomeAngle / 2, incomeAngle, true, incomePaint);
    }
}