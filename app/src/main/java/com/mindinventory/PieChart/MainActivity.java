package com.mindinventory.PieChart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mindinventory.PieChart.model.PieData;
import com.mindinventory.PieChart.utils.CustomPieChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.custPieChart)
    CustomPieChart custPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<PieData> pieDataList = new ArrayList<>();
        pieDataList.add(getPieChartPrepared(android.R.color.holo_green_dark, "Green", 30f));
        pieDataList.add(getPieChartPrepared(android.R.color.holo_orange_dark, "Orange", 20f));
        pieDataList.add(getPieChartPrepared(android.R.color.holo_purple, "Purple", 40f));
        pieDataList.add(getPieChartPrepared(android.R.color.holo_red_light, "Red", 10f));

        //Set colors to pie chart using this method
        custPieChart.setPieDataList(pieDataList, "My Custom Chart");

        //Set Stroke[Border] size of PieChart with visibility of enable or disable stroke.
        custPieChart.setStroke(true, 10f);
    }


    //Set data to Object for displaying Piechart
    private PieData getPieChartPrepared(int color, String name, float percentage) {
        PieData pieData = new PieData();
        pieData.setColor(color);
        pieData.setName(name);
        pieData.setPercentage(percentage);
        return pieData;
    }
}
