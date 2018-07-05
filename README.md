普通样式
--------
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/waveLoadNormal.gif)<br>
		
		布局文件<br>
		<com.example.waveloaddemo.custom_view.WaveProgressView
				android:id="@+id/waveView"
				android:layout_width="match_parent"
				android:layout_height="match_parent"
				/>
				
		java代码<br>
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0.8f);
		valueAnimator.addUpdateListener((v) -> {
                    float value = (float) v.getAnimatedValue();
                    mWaveView.setPercent(value);//设置值
                }
        );
        valueAnimator.setDuration(5000);//3000秒
        valueAnimator.start();//开启动画

加上颜色过渡
------------
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/gradualChangeWaveLoad.gif)<br>

		java代码设置方法
		mWaveView.setFirstColorList(0x99FF2727, 0x99FF8C20, 0x99FBFF27, 0x998BE93A, 0x9932D132);
        mWaveView.setSecondColorList(0x66FF2727, 0x66FF8C20, 0x66FBFF27, 0x668BE93A, 0x6632D132);

加上TextView
------------
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/waveLoad90.gif)
![image](https://github.com/bouquet12138/pictureLibrary/blob/master/waveLoad100.gif)<br>
		
		布局文件
		<com.example.waveloaddemo.custom_view.WaveProgressView
			android:id="@+id/waveView"
			android:layout_width="match_parent"
			android:layout_height="match_parent"
			android:background="#fff"
			/>

		<TextView
			android:id="@+id/valueText"
			android:layout_width="match_parent"
			android:layout_height="match_parent"
			android:gravity="center"
			android:text="0.00%"
			android:textSize="14dp"
			android:textColor="#fff"/><br>
			
		java代码
		
		ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0.8f);
		valueAnimator.addUpdateListener((v) -> {
                    float value = (float) v.getAnimatedValue();
                    mWaveView.setPercent(value);//设置值

                    value *= 100;
                    if (value == 100 || value - mLastValue >= 0.8f) {//加上这句话是避免数据更新太快，不然闪瞎狗眼😂
                        NumberFormat nf = NumberFormat.getNumberInstance();
                        nf.setMaximumFractionDigits(2);//小数位最多两位
                        nf.setMinimumFractionDigits(2);//小数位最低两位 保证后面保留两位小数 亲们可以自行更改
                        String text = nf.format(value);
                        mValueText.setText(text + "%");
                        mLastValue = value;
                    }
                }
        );
		
			
为什么把TextView和waveProgressView分开而不是在waveProgressView里用画笔画呢，是为了更好的扩展性，高内聚低耦合了解一下，
其实是因为up懒，但这样确实扩展更好，嘿。

		

		