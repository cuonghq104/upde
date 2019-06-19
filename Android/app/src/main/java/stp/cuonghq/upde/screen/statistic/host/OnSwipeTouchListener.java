package stp.cuonghq.upde.screen.statistic.host;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cuong.hq1 on 6/7/2019.
 */

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public OnSwipeTouchListener (Context ctx){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 0;
        private static final int SWIPE_VELOCITY_THRESHOLD = 0;
        private boolean load = false;
        @Override
        public boolean onDown(MotionEvent e) {
            load = true;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if (load) {
                try {
                    if (e2.getX() < e1.getX()) {
                        onSwipeLeft();
                    } else if (e2.getX() > e1.getX()) {
                        onSwipeRight();
                    }
                    load = false;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            onSwipeSingleTap(e.getRawX(),e.getRawY());
            return true;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    public void onSwipeSingleTap(float x, float y) {
    }
}
