# Pie Chart Custom View
Simple implementation of PieChart

  ![image](/media/pie.png)

# Usage

* CustomPieChart.java is the PieChart component, add it to your xml file.

```
<com.mindinventory.PieChart.utils.CustomPieChart
        android:id="@+id/custPieChart"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="32dp"/>
```

* Create instances of PieData and add them as follows
 
 ```
    //you can set color and percentage as follows.
    List<PieData> pieDataList = new ArrayList<>();
    pieDataList.add(getPieChartPrepared(android.R.color.holo_green_dark, "Green", 30f));
    pieDataList.add(getPieChartPrepared(android.R.color.holo_orange_dark, "Orange", 20f));
    pieDataList.add(getPieChartPrepared(android.R.color.holo_purple, "Purple", 40f));
    pieDataList.add(getPieChartPrepared(android.R.color.holo_red_light, "Red", 10f));

    //Set colors to pie chart using this method
    custPieChart.setPieDataList(pieDataList, "My Custom Chart");
    
    //Set Stroke(Border) size of PieChart and visibility
    custPieChart.setStroke(true, 10f);
```

## Let us know!
We’d be really happy if you sent us links to your projects where you use our component. Just send an email to sales@mindinventory.com And do let us know if you have any questions or suggestion regarding our work.
