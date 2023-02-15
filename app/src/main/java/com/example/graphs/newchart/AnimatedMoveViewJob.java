package com.example.graphs.newchart;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;


@SuppressLint("NewApi")
public class AnimatedMoveViewJob extends AnimatedViewPortJob {
    public AnimatedMoveViewJob(ViewPortHandler viewPortHandler, float xValue, float yValue, Transformer trans, View v, float xOrigin, float yOrigin, long duration) {
        super(viewPortHandler, xValue, yValue, trans, v, xOrigin, yOrigin, duration);
    }

    @Override
    public void recycleSelf() {

    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return null;
    }

    @Override
    public void onAnimationStart(@NonNull Animator animation, boolean isReverse) {
        super.onAnimationStart(animation, isReverse);
    }

    @Override
    public void onAnimationEnd(@NonNull Animator animation, boolean isReverse) {
        super.onAnimationEnd(animation, isReverse);
    }

//    private static ObjectPool<AnimatedMoveViewJob> pool;
//
//    static {
//        pool = ObjectPool.create(4, new AnimatedMoveViewJob(null, 0, 0, null, null, 0, 0, 0));
//        pool.setReplenishPercentage(0.5f);
//    }
//
//    public AnimatedMoveViewJob(Object o, int i, int i1, Object o1, Object o2, int i2, int i3, int i4) {
////        super();
//    }
//
//    public static AnimatedMoveViewJob getInstance(ViewPortHandler viewPortHandler, float xValue, float yValue, Transformer trans, View v, float xOrigin, float yOrigin, long duration) {
//        AnimatedMoveViewJob result = pool.get();
//        result.mViewPortHandler = viewPortHandler;
//        result.xValue = xValue;
//        result.yValue = yValue;
//        result.mTrans = trans;
//        result.view = v;
//        result.xOrigin = xOrigin;
//        result.yOrigin = yOrigin;
//        //result.resetAnimator();
//        result.animator.setDuration(duration);
//        return result;
//    }
//
//    public static void recycleInstance(AnimatedMoveViewJob instance) {
//        pool.recycle(instance);
//    }
}