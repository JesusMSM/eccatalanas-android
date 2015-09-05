package es.elconfidencial.eleccionesec.chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.text.TextPaint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * Created by JesúsManuel on 05/09/2015.
 */
public class PieChartRendererEC extends PieChartRenderer {

    public PieChartRendererEC(PieChart chart, ChartAnimator animator,
                            ViewPortHandler viewPortHandler) {
        super(chart,animator, viewPortHandler);

    }
    @Override
    public void drawValues(Canvas c) {

        PointF center = mChart.getCenterCircleBox();

        // get whole the radius
        float r = mChart.getRadius();
        float rotationAngle = mChart.getRotationAngle();
        float[] drawAngles = mChart.getDrawAngles();
        float[] absoluteAngles = mChart.getAbsoluteAngles();

        float off = r / 10f * 3.6f;

        if (mChart.isDrawHoleEnabled()) {
            off = (r - (r / 100f * mChart.getHoleRadius())) / 2f;
        }

        r -= off; // offset to keep things inside the chart

        PieData data = mChart.getData();
        List<PieDataSet> dataSets = data.getDataSets();
        boolean drawXVals = mChart.isDrawSliceTextEnabled();

        int cnt = 0;

        for (int i = 0; i < dataSets.size(); i++) {

            PieDataSet dataSet = dataSets.get(i);

            if (!dataSet.isDrawValuesEnabled() && !drawXVals)
                continue;

            // apply the text-styling defined by the DataSet
            applyValueTextStyle(dataSet);

            List<Entry> entries = dataSet.getYVals();

            for (int j = 0, maxEntry = Math.min(
                    (int) Math.ceil(entries.size() * mAnimator.getPhaseX()), entries.size()); j < maxEntry; j++) {

                // offset needed to center the drawn text in the slice
                float offset = drawAngles[cnt] / 2;

                // calculate the text position
                float x = (float) (r
                        * Math.cos(Math.toRadians((rotationAngle + absoluteAngles[cnt] - offset)
                        * mAnimator.getPhaseY())) + center.x);
                float y = (float) (r
                        * Math.sin(Math.toRadians((rotationAngle + absoluteAngles[cnt] - offset)
                        * mAnimator.getPhaseY())) + center.y);

                float value = mChart.isUsePercentValuesEnabled() ? entries.get(j).getVal()
                        / mChart.getYValueSum() * 100f : entries.get(j).getVal();

                String val = dataSet.getValueFormatter().getFormattedValue(value);

                float lineHeight = Utils.calcTextHeight(mValuePaint, val)
                        + Utils.convertDpToPixel(4f);

                boolean drawYVals = dataSet.isDrawValuesEnabled();

                // draw everything, depending on settings
                if (drawXVals && drawYVals) {

                    c.drawText(val, x, y, mValuePaint);
                    if (j < data.getXValCount())
                        c.drawText(data.getXVals().get(j), x, y + lineHeight,
                                mValuePaint);

                } else if (drawXVals && !drawYVals) {
                    if (j < data.getXValCount())
                        c.drawText(data.getXVals().get(j), x, y + lineHeight / 2f, mValuePaint);
                } else if (!drawXVals && drawYVals) {

                    c.drawText(val, x, y + lineHeight / 2f, mValuePaint);
                }

                cnt++;
            }
        }
    }
}
