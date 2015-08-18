package com.cxwl.agriculture.chart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 图表View
 * @author kim
 *
 */
public class ChartView extends View {
	
		private int maxValue;
	    private int minValue;
	    private int yAxisLableWith = 0;
	    public int YAxisDataCount;   
	    private int maxY;
	    private int maxX;

	    private int UpOrDownSpace;
	    public int XAxisDataCount;
	    public ArrayList xData;        //x 轴数据
	    public boolean drawXScaleLine = true;
	    public boolean drawYScaleLine = true;
	    public ArrayList<String> xAxisLabels;

	    // 图形  线型图  柱状图
	    public enum ChartViewType{
	        ChartViewLine,
	        ChartViewPost
	    }
	    // 数值点的 标识
	    public enum ChartViewValuePointType{
	        ChartViewValuePoint,
	        ChartViewValueImage
	    }

	    public int paintWidth;                    // 默认  线宽
	    public int paintPostTypeWidth = 6;
	    public  ChartViewType chartViewType;          // 图类型
	    public Boolean showYLabels = true;            // 显示  Ｘ Ｙ 轴标度
	    public Boolean showXLabels = true;
	    public int XYLineColor;

		public String title;

	    // 默认值
	    public int drawValueSpace = 1;
	    public ChartViewValuePointType chartViewValuePointType = ChartViewValuePointType.ChartViewValuePoint;
	    public ArrayList<LineData> dataArray ;

		private double rate;
	    
	    public ChartView(Context context){
	        super(context);
	        chartViewType = ChartViewType.ChartViewLine;
	        YAxisDataCount = 7;
	    }
	    
	    

	    public ChartView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			chartViewType = ChartViewType.ChartViewLine;
	        YAxisDataCount = 7;
		}



		public ChartView(Context context, AttributeSet attrs) {
			super(context, attrs);
			chartViewType = ChartViewType.ChartViewLine;
	        YAxisDataCount = 7;
		}



		public void onDraw(Canvas canvas){
	        super.onDraw(canvas);
	        if (dataArray != null){
	        	 if (xAxisLabels != null && xAxisLabels.size() != 0) {
//	 	        	XAxisDataCount = xAxisLabels.size();
	 	        	maxY = this.getHeight() - 10;
	 			}
	 	        else {
//	 	        	XAxisDataCount = 0;
	 	        	maxY = this.getHeight();
	 			}
	        	 
	            maxX = this.getWidth();
	            maxValue = this.chartViewMaxY();
	            minValue = this.chartViewMinY();

				rate = this.getHeight()/250.0;
				paintWidth = (int)(rate*5);
				UpOrDownSpace = (int)(rate*6);

	            Paint paint = new Paint();
	            paint.setAntiAlias(true);
	            paint.setTextSize((int)(rate*20));
				paint.setColor(Color.parseColor("#009688"));
//	            paint.setShadowLayer(2,2,2, Color.BLACK);

				drawTitle(canvas, paint);

				paint.setColor(Color.WHITE);
	            //
	            drawYlables(canvas,paint); 
	            //
	            drawXAxisLables(canvas, paint);
	            switch (chartViewType){
	                // 线性图
	                case ChartViewLine:
	                    this.chartViewLineType(canvas,paint);
	                    break;
	                // 柱状图
	                case ChartViewPost:
	                    this.chartViewPostType(canvas,paint);
	                    break;
	                default:
	                    break;
	            }
	        }
	    }

	    private int chartViewMaxY(){
	        if (dataArray != null){
	            int temp = dataArray.get(0).lineDataMaxValue();
	            int size = dataArray.size();
	            for (int i = 1; i < size; i++){
	                LineData lineData = dataArray.get(i);
	                int te = lineData.lineDataMaxValue();
	                if (temp < te) temp = te;
	            }
	            temp += 10;
	            int min = chartViewMinY();
	            System.out.println("Min->>" + min + "Max:-->>" + temp);
	            int a = (temp - min)%YAxisDataCount;
	            return temp + (YAxisDataCount - a);
	        }
	        return 0;
	    }

	    private int chartViewMinY(){
	        if (dataArray != null){
	            int temp = dataArray.get(0).lineDataMinValue();
	            int size = dataArray.size();
	            for (int i = 1; i < size; i++){
	                LineData lineData = dataArray.get(i);
	                int te = lineData.lineDataMinValue();
	                if (temp > te) temp = te;
	            }
	            if (temp < 0)
	            {
	                return temp - 10;
	            }
	            else
	            {
	                return 0;
	            }
	        }
	        return 0;
	    }

	    private int getTextWidth(Paint paint, String str) {
	        int iRet = 0;
	        if (str != null && str.length() > 0) {
	            int len = str.length();
	            float[] widths = new float[len];
	            paint.getTextWidths(str, widths);
	            for (int j = 0; j < len; j++) {
	                iRet += (int) Math.ceil(widths[j]);
	            }
	        }
	        return iRet;
	    }
	    private void drawXAxisLables(Canvas canvas, Paint paint){
	        int average = (maxX - yAxisLableWith)/XAxisDataCount;
	        paint.setStrokeWidth(1);
	        for (int i = 0; i < XAxisDataCount; i++){

	            int x = yAxisLableWith + average*i + average/2;
	            if (showXLabels) {
					String str = xAxisLabels.get(i);
		            canvas.drawText(str,x,maxY + UpOrDownSpace * 3,paint);	
				}
	            if (drawXScaleLine) {
	            	if (showXLabels) {
	            		canvas.drawLine(x, 5, x, maxY, paint);	
					}else {
						canvas.drawLine(x, 5, x, maxY + 13, paint);
					}
				}
	        }
	        if (showXLabels) {
	        	paint.setStrokeWidth(3);
		        canvas.drawLine(yAxisLableWith + 2, maxY, maxX, maxY, paint);
			}
	    }
	    private void drawYlables(Canvas canvas,Paint paint){
	        paint.setTextAlign(Paint.Align.CENTER);
	        float average = (maxY) / YAxisDataCount;
	        int averageValue = (maxValue - minValue)/YAxisDataCount;
	        // 计算  最大宽度
	        if (showYLabels) {
	        	 for (int i = 0; i < YAxisDataCount; i++) {
	 	        	String text = String.valueOf(minValue + averageValue * i);
	 	            int width = getTextWidth(paint, text) + 4;
	 	            if (width > yAxisLableWith )  yAxisLableWith = width;
	 			}
			}
	       // 写文字
	        for (int i = 0; i < YAxisDataCount; i++){
	            String text = String.valueOf(minValue + averageValue * i);
	            float y = maxY - average*i;
	            if (showYLabels) {
 	                canvas.drawText(text,yAxisLableWith/2,y + 5,paint);
				}
	            if (drawYScaleLine) {
	            	if (showYLabels) {
	            		canvas.drawLine(yAxisLableWith + 3, y, maxX - 8, y, paint);	
					}else {
						canvas.drawLine(yAxisLableWith + 8, y, maxX - 8, y, paint);
					}
				}
	        }
	        // 画Ｙ轴
	        if (showYLabels) {
	        	 paint.setStrokeWidth(3);
	 	        canvas.drawLine(yAxisLableWith + 2, 5, yAxisLableWith + 2, maxY, paint);
			}
	    }

		private void drawTitle(Canvas canvas,Paint paint){
			if(title != null){
				int x = (int)(rate*20);
				int y = (int)(rate*40);
				canvas.drawText(title, x, y, paint);
			}
		}
	    
	    private void chartViewPostType(Canvas canvas,Paint paint){
	        int size = dataArray.size();
//	        paint.setShadowLayer(2,2,0,Color.BLACK);
	        paint.setStrokeWidth(paintPostTypeWidth);
	        for (int i = 0; i < size; i++){
	            LineData lineData = dataArray.get(i);
	            
	            paint.setColor(lineData.colorValue);
	            ArrayList tempArray = lineData.dataList;
	            int lineDataSize = tempArray.size();
	            if (XAxisDataCount == 0) XAxisDataCount = lineDataSize;
	            int xAverage = (maxX - yAxisLableWith)/lineDataSize;
	            for (int j = 0; j < lineDataSize; j++){
	                int value = ((Integer)tempArray.get(j)).intValue();
	                int x = yAxisLableWith + xAverage*j + xAverage/2;
	                float y = (maxValue - value) * (maxY - 10) / (maxValue - minValue) + 12;
	                if (y >= maxY) {
	                	y = maxY;
					}
	                
	                canvas.drawLine(x,maxY - 2,x,y,paint);
	                String text = String.valueOf(value);
	                canvas.drawText(text,x,y - UpOrDownSpace/2,paint);
	                switch (lineData.textDrawPlace) {
	                    case TextDrawUp:
	                    	canvas.drawText(text,x,y - UpOrDownSpace/2,paint);
	                        break;
	                    default:
	                        break;
	                }
	            }
	        }
	    }

	    private void chartViewLineType(Canvas canvas,Paint paint){
	        int size = dataArray.size();
	        paint.setStrokeWidth(paintWidth);
//	        paint.setShadowLayer(4,2,2,Color.BLACK);
	        for (int i = 0 ; i < size; i++ ){
	            LineData lineData = dataArray.get(i);
	            paint.setColor(lineData.colorValue);
	            ArrayList tempArray = lineData.dataList;
	            int lineDataSize = tempArray.size();
	            int average = (maxX - yAxisLableWith)/lineDataSize;
	            float tempX = 0;
	            float tempY = 0;
	            ArrayList<HashMap<String, Integer>> tempDataArrayList = new ArrayList<HashMap<String,Integer>>();
	            for (int j = 0; j < lineDataSize; j++){
	                int value = ((Integer)tempArray.get(j)).intValue();
	                int x = yAxisLableWith + average*j + average/2;
	                int y = (maxValue - value) * (maxY - 10) / (maxValue - minValue) + (int)(rate*40);
	                if (y >= maxY) {
	                	y = maxY;
					}
	                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
	                hashMap.put("x", Integer.valueOf(x));
	                hashMap.put("y", Integer.valueOf(y));
	                tempDataArrayList.add(hashMap);
	                if (j == 0) {
	                    tempX = x;
	                    tempY = y;
	                }
	                else
	                {
	                    canvas.drawLine(tempX,tempY,x,y,paint);
	                    tempX = x;
	                    tempY = y;
	                }
	                String text = String.valueOf(value)+"°C";
	                switch (lineData.textDrawPlace) {
	                    case TextDrawUp:
	                    	canvas.drawText(text,x,y - UpOrDownSpace ,paint);
	                        break;
	                    case TextDrawDown:
	                    	 canvas.drawText(text,x,y + UpOrDownSpace * 3,paint);
	                        break;
	                    default:
	                        break;
	                }
	            }
	            for (HashMap<String, Integer> hashMap : tempDataArrayList) {
	            	int x = hashMap.get("x").intValue();
	            	int y = hashMap.get("y").intValue();
	            	 switch (chartViewValuePointType) {
	                    case ChartViewValuePoint:
	                        canvas.drawCircle(x,y, (int)(6*rate), paint);
	                        break;
	                    case ChartViewValueImage:
	                        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
	                        canvas.save();
	                        Matrix matrix = new Matrix();
	                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), 0);
	                        //matrix.setRotate(90,x,y);
	                        int width = bitmap.getWidth();
	                        int height = bitmap.getHeight();
	                        //bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	                        canvas.drawBitmap(bitmap, x - width/2, y - height/2, paint);
	                        canvas.restore();
	                        break;
	                    default:
	                        break;
	                }
				}
	        }
	    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int heightsize = MeasureSpec.getSize(heightMeasureSpec);
		Log.i("test", "heightsize: "+heightsize);
	}
}
