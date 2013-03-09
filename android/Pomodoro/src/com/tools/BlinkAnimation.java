package com.tools;

import android.util.FloatMath;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class BlinkAnimation extends Animation {
	private int totalBlinks;
	private boolean finishOff;
	
	public BlinkAnimation(int totalBlinks, boolean finishOff) {
		this.totalBlinks = totalBlinks;
		this.finishOff = finishOff;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		float period = interpolatedTime * totalBlinks * 3.14f + (finishOff ? 3.14f / 2 : 0);
		t.setAlpha(Math.abs(FloatMath.cos(period)));
	}

	@Override
	public boolean willChangeBounds() {
		return false;
	}

	@Override
	public boolean willChangeTransformationMatrix() {
		return false;
	}
}