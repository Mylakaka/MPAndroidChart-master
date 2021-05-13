
package com.xxmassdeveloper.mpchartexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.MyX;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;
import java.util.List;

public class CubicLineChartActivity extends DemoBase implements OnSeekBarChangeListener {

    private LineChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);


        setTitle("CubicLineChartActivity");

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);


        //显示x轴数据的密集度
        seekBarX = findViewById(R.id.seekBar1);

        //切换x轴的位置
        seekBarY = findViewById(R.id.seekBar2);

        chart = findViewById(R.id.chart1);

        //设置流线在view中的偏移（默认值是有一定的偏移的）
        chart.setViewPortOffsets(30, 20, 30, 30);

        //设置背景色
        chart.setBackgroundColor(Color.rgb(34, 92, 110));

        //描述的文本
        chart.getDescription().setEnabled(false);
        //chart.setHighlightPerTapEnabled(false);

        //触控手势
        chart.setTouchEnabled(true);

        //启动拖动和缩放
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        //如果是false，则可以分别在x轴和y轴进行缩放
        chart.setPinchZoom(false);

        //隐藏绘制区域的背景，默认灰色
        chart.setDrawGridBackground(false);

        //最大高亮距离（dp）,点击位置距离数据点的距离超过这个距离不会高亮，默认500dp
        //chart.setMaxHighlightDistance(20);


        final MyX myX = new MyX(chart.getViewPortHandler(), chart.getXAxis(), chart.getTransformer(null), highlight);
        chart.setXAxisRenderer(myX);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(CubicLineChartActivity.this,"点击了："+e.getX()+":"+e.getY(),Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onValueSelected: 点击率可");



                myX.h=h;
                chart.invalidate();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                Log.d("TAG", "onChartTranslate: 拖拽");



                //myX.h1+=dX;

                chart.invalidate();
            }
        });


        //获取x轴
        XAxis x = chart.getXAxis();
        //禁止x轴
        //x.setEnabled(false);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);

       x. setLabelCount(6, true);


        YAxis y = chart.getAxisLeft();


        y.setTypeface(tfLight); //标签字体
        //y.setLabelCount(6, false);//标签的数量，值自动计算
        y.setTextColor(Color.RED);
        //y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(true);//是否显示网格线
        y.setAxisLineColor(Color.RED);  //y轴颜色
        y.setDrawAxisLine(false);

        //y.enableGridDashedLine(10f, 10f, 0f);
        //y.setDrawZeroLine(true);

        //隐藏右侧轴
        chart.getAxisRight().setEnabled(false);

        // add data
        seekBarY.setOnSeekBarChangeListener(this);



        // lower max, as cubic runs significantly slower than linear
        seekBarX.setMax(700);

        //默认值
        seekBarX.setProgress(45);
        seekBarY.setProgress(100);

        //显示每条线的描述
        chart.getLegend().setEnabled(false);

        //动画，xy轴一起执行
        chart.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        chart.invalidate();

        seekBarX.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("TAG", "onProgressChanged: "+progress);

                highlight=new Highlight(progress,0,0);

                Log.d("TAG", "onProgressChanged: "+highlight);
                Log.d("TAG", "onProgressChanged: "+chart);

                chart.highlightValue(highlight, false);

                Entry entry = chart.getLineData().getEntryForHighlight(highlight);

               setText(entry);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
private void setText(Entry entry){
    tvX.setText(entry.getX()+":"+entry.getY()+":"+entry.getData());

}

    private void setData(int count, float range) {

        //声明一个数组
        ArrayList<Entry> values = new ArrayList<>();

        //第一个进度条的数值（显示的数据数）
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
            set1.setCubicIntensity(0.2f);

            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setValueTextSize(8f);//当lineData不设置字体大小的话，这个生效
            set1.setCircleRadius(8f); //折线中每一点的半径和颜色
            set1.setCircleColor(Color.GREEN);

            set1.setHighLightColor(Color.rgb(240, 141, 73));//高亮线的颜色

            set1.setHighlightLineWidth(3f);

            set1.setColor(Color.RED);       //设置线条颜色
            set1.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.fade_red));    //设置填充颜色
            set1.setFillAlpha(233);         //填充颜色的透明度
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setHighlightEnabled(true);





            //set1.setGradientColor(Color.RED, Color.BLUE);

            set1.setFillFormatter(new IFillFormatter() {//用于控制填充线的位置以Y轴的最小值为准，而不再是默认的以0为准
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTypeface(tfLight);
            //data.setValueTextSize(5f);     //设置其上各点数据字体大小（不包括x、y轴）

            data.setDrawValues(true); //是否在点上绘制Value

            // set data
            chart.setData(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/CubicLineChartActivity.java"));
                startActivity(i);
                break;
            }
            case R.id.actionToggleValues: {
                for (IDataSet set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if(chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            case R.id.actionToggleFilled: {

                List<ILineDataSet> sets = chart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;

                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                chart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = chart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                chart.invalidate();
                break;
            }
            case R.id.actionToggleCubic: {
                //每个DataSet=new LineDataSet(list<Entry<key,value>>,"第一条线");
                List<ILineDataSet> sets = chart.getData().getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.CUBIC_BEZIER);
                }
                chart.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> sets = chart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.STEPPED);
                }
                chart.invalidate();
                break;
            }
            case R.id.actionToggleHorizontalCubic: {
                List<ILineDataSet> sets = chart.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.HORIZONTAL_BEZIER);
                }
                chart.invalidate();
                break;
            }
            case R.id.actionTogglePinch: {
                if (chart.isPinchZoomEnabled())
                    chart.setPinchZoom(false);
                else
                    chart.setPinchZoom(true);

                chart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                chart.setAutoScaleMinMaxEnabled(!chart.isAutoScaleMinMaxEnabled());
                chart.notifyDataSetChanged();
                break;
            }
            case R.id.animateX: {
                chart.animateX(2000);
                break;
            }
            case R.id.animateY: {
                chart.animateY(2000);
                break;
            }
            case R.id.animateXY: {
                chart.animateXY(2000, 2000);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }
    Highlight highlight;
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("45");  //第一各进度条作用是 数据集合的数量
        tvY.setText(String.valueOf(seekBarY.getProgress()));  //第二个进度条作用是 根据进度的值随机生成一个y键


        setData(25, seekBarY.getProgress());

        // redraw
        chart.invalidate();


    }



    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "CubicLineChartActivity");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
