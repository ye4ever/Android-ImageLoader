/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.imageloader.display;

import me.xiaopan.android.imageloader.task.display.DisplayRequest;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * 由小到大位图显示器
 */
public class ZoomInBitmapDisplayer implements BitmapDisplayer {
//	private static final String NAME= ZoomInBitmapDisplayer.class.getSimpleName();
	private int duration;

	public ZoomInBitmapDisplayer(int duration){
		this.duration = duration;
	}
	
	public ZoomInBitmapDisplayer(){
		this(400);
	}
	
	@Override
	public void display(ImageView imageView, BitmapDrawable bitmapDrawable, BitmapType bitmapType, DisplayRequest displayRequest) {
		switch(bitmapType){
			case FAILURE : 
				if(bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()){
					fadeIn(imageView, bitmapDrawable);
				}else{
					imageView.setImageDrawable(null);
				}
				break;
			case SUCCESS : 
				if(bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()){
					fadeIn(imageView, bitmapDrawable);
				}else{
					imageView.setImageDrawable(null);
				}
				break;
		}
	}
	
	/**
	 * 渐入
	 */
	private void fadeIn(ImageView imageView, BitmapDrawable bitmapDrawable){
		Drawable oldDrawable = imageView.getDrawable();
		Drawable firstDrawable  = oldDrawable != null?oldDrawable:new ColorDrawable(android.R.color.transparent);
		TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{firstDrawable, bitmapDrawable});
		imageView.setImageDrawable(transitionDrawable);
		transitionDrawable.setCrossFadeEnabled(true);
		transitionDrawable.startTransition(duration);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		scaleAnimation.setDuration(duration);
		imageView.startAnimation(scaleAnimation);
	}

	@Override
	public BitmapDisplayer copy() {
		return new ZoomInBitmapDisplayer(duration);
	}
}
