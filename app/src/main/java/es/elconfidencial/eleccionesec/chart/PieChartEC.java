package es.elconfidencial.eleccionesec.chart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by JesúsManuel on 05/09/2015.
 */
public class PieChartEC extends PieChart {
    public PieChartEC(Context context) {
        super(context);
    }

    public PieChartEC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartEC(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();

        mRenderer = new PieChartRendererEC(this, mAnimator, mViewPortHandler);
    }
}
