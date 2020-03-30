package com.sharkz.tool.kit;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-01-09  14:58
 * 描    述 这个是一个案例 实践证明还是有问题的  待优化
 * 修订历史：
 * ================================================
 */
public class BaseOnTouchListener2 implements View.OnTouchListener {

    private static String TAG = "BaseOnTouchListener2";

    /**
     * 长按事件的时间判断条件 600ms
     */
    private static final long LONG_CLICK_LIMIT = 800;

    /**
     * 单击事件判断条件
     */
    private static final long SHORT_CLICK_LIMIT = 400;

    /**
     * 双击事件 判断时间
     */
    private static final long DOUBLE_CLICK_LIMIT = 300;

    /**
     * 滑动事件 最小判断标示
     */
    private static final int MOVE_BOUNDARY = 80;


    /**
     * 点击事件
     */
    private boolean mIsClick;

    /**
     * 长按事件
     */
    private boolean mIsLongClick;

    /**
     * 双击事件 用于点击事件之间的判断
     */
    private boolean mIsDoubleClick;

    /**
     * 当前正在执行长按操作 用于和滑动事件之间的判断
     */
    private boolean onLongClick;

    /**
     * 第一次点击的时间戳 / 全部手指离开屏幕的时间戳
     */
    private long downCurrentTimeMillis = 0;
    private long upCurrentTimeMillis = 0;

    /**
     * 触摸的是坐标区域还是右边区域 --> 当前将屏幕分成 左右两个区域
     */
    private boolean mIsLeftArea;
    private boolean mIsRightArea;

    /**
     * 左边区域/右边区域
     */
    private static final int AREA_LEFT = 0;
    private static final int AREA_RIGHT = 1;

    /**
     * 滑动的距离
     */
    private int mMoveX;
    private int mMoveY;

    /**
     * 第一个触点坐标
     */
    private int mFirstX, mFirstY;

    /**
     * 实时坐标点
     */
    private int x, y;

    /**
     * 滑动方向
     */
    private boolean mIsXMove;
    private boolean mIsYMove;

    /**
     * 双指操作
     */
    private boolean doublePointMove = false;

    /**
     * 两个手指之间的距离
     */
    private double distance = 0;

    // =============================================================================================


    /**
     * 用于手势判断的 条件
     *
     * @param basePlayManager
     */
    private boolean basePlayManager;

    public void setBasePlayManager(boolean basePlayManager) {
        this.basePlayManager = basePlayManager;
    }


    /**
     * 事件处理
     */
    private Handler mHandler = new Handler(Looper.getMainLooper());


    // =============================================================================================


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 当前 触摸点坐标
        x = (int) event.getX();
        y = (int) event.getY();
        // 触摸设备时手指的数量
        int pCount = event.getPointerCount();
        // 热区在这里判断 触摸点在热区 直接返回
        if (isInHotspot(v, event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
            Log.i(TAG, "onTouch: 热区在这里判断 触摸点在热区 直接返回");
        }
        // 手势类型
        int action = event.getAction();
        switch (action) {
            // 用户第一次 触摸
            case MotionEvent.ACTION_DOWN:
                actionDown(v, event);
                break;
            // 滑动
            case MotionEvent.ACTION_MOVE:
                // 如果当前是长按事件 --> 取消 滑动操作
                if (onLongClick) {
                    break;
                }
                if (pCount == 1) {
                    actionMove();
                }
                if (pCount == 2) {
                    actionDoublePointMove(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;

            case MotionEvent.ACTION_CANCEL:
                actionCancel();
                break;
        }
        return true; // 默认处理当前事件
    }


    // =============================================================================================


    /**
     * 两指操作
     *
     * @param event
     */
    private void actionDoublePointMove(MotionEvent event) {
        doublePointMove = true;
        mIsClick = false; // 取消单击操作
        mIsLongClick = false;// 取消长按事件
        mIsDoubleClick = false; // 取消双击事件
        // 如果当前是 滑动操作 --> 取消长按事件
        mHandler.removeCallbacks(longClickRn);
        // Log.i(TAG, "onTouch: 两指操作");
        //Log.i(TAG, "actionDoublePointMove: distance==" + distance);
        if (distance == 0) {
            distance = distanceBetweenFingers(event);
        } else {
            if (distance > distanceBetweenFingers(event)) {
                onNarrow();
            } else {
                onEnLarge();
            }
        }
    }

    /**
     * ACTION MOVE 事件处理
     */
    private void actionMove() {
        if (doublePointMove) {
            return;
        }
        mMoveX = x - mFirstX;
        mMoveY = mFirstY - y;
        // 坐标点夹脚
        int an = -1;
        // 判断点击、长按/滑动 满足下面的条件就是滑动事件了
        if (Math.abs(mMoveY) > MOVE_BOUNDARY || Math.abs(mMoveX) > MOVE_BOUNDARY) {
            mIsClick = false; // 取消单击操作
            mIsLongClick = false;// 取消长按事件
            mIsDoubleClick = false; // 取消双击事件
            // 如果当前是 滑动操作 --> 取消长按事件
            mHandler.removeCallbacks(longClickRn);
            // 夹脚
            an = angle(mFirstX, mFirstY, x, y);
        }

        // 判断操作状态
        if (!mIsXMove && !mIsYMove) {
            if (Math.abs(mMoveY) > MOVE_BOUNDARY && an < 90 && an > 45) {
                mIsYMove = true;
                mIsXMove = false;
                mIsDoubleClick = false; // 取消双击事件
            }
            if (Math.abs(mMoveX) > MOVE_BOUNDARY && an < 45 && an > 0) {
                mIsXMove = true;
                mIsYMove = false;
                mIsDoubleClick = false; // 取消双击事件
            }
        }
        // X轴滑动 改变播放进度
        if (mIsXMove) {
            onXMove(mMoveX);
        }
        // Y轴滑动
        if (mIsYMove) {
            if (mIsLeftArea) {
                onYMove(AREA_LEFT, mMoveY);
            } else {
                onYMove(AREA_RIGHT, mMoveY);
            }
        }
    }

    /**
     * ACTION DOWN 事件处理
     *
     * @param v
     * @param event
     */
    private void actionDown(View v, MotionEvent event) {
        // 记录下当前的时间戳
        downCurrentTimeMillis = System.currentTimeMillis();
        // 重置单击和长按的定时器
        mHandler.removeCallbacks(onCLickRn);
        mHandler.removeCallbacks(longClickRn);
        // 初始化变量
        int controlAreaWidth = v.getWidth() / 2;
        mFirstX = (int) event.getX();
        mFirstY = (int) event.getY();
        // 判断触控区域是左还是右
        mIsLeftArea = mFirstX < controlAreaWidth;
        mIsRightArea = mFirstX > v.getWidth() - controlAreaWidth;
        // 点击事件 down 的时候 丧钟方式都开启了
        mIsClick = true;
        mIsLongClick = true;
        mIsDoubleClick = true;
        // 长按延时执行 --> up的时候取消
        mHandler.postDelayed(longClickRn, LONG_CLICK_LIMIT);
    }

    /**
     * ACTION UP 事件处理
     * 最后一根手指离开屏幕
     */
    private void actionUp() {
        // 双击事件处理
        if (downCurrentTimeMillis - upCurrentTimeMillis < DOUBLE_CLICK_LIMIT && mIsDoubleClick) {
            mIsClick = false; // 取消单击事件
            mIsLongClick = false; // 取消长按事件
            mHandler.removeCallbacks(longClickRn); // 长按定时器
            mHandler.removeCallbacks(onCLickRn); // 单击定时器
            onDoubleClick();
        }
        // 记录一下 最后一个手指离开屏幕的时间戳
        upCurrentTimeMillis = System.currentTimeMillis();
        // 处理长按
        cancelLongClick();
        // 处理单击
        mHandler.postDelayed(onCLickRn, SHORT_CLICK_LIMIT);

        // 下面是滑动 重置处理
        if (mIsXMove) {
            onXMoveEnd(mMoveX);
        }
        if (mIsYMove) {
            if (mIsLeftArea) {
                onYMoveEnd(AREA_LEFT, mMoveY);
            } else {
                onYMoveEnd(AREA_RIGHT, mMoveY);
            }
        }

        // 滑动事件重置
        mIsXMove = false;
        mIsYMove = false;
        // 双指操作
        doublePointMove = false;
        distance = 0;
    }

    /**
     * ACTION CANCEL 事件处理
     * 从表象来说明就是:当触摸事件从我们的控件开始,也就是ACTION_DOWN被我们返回true处理,
     * 然后手指移动到当前控件的外面,这时候就会触发ACTION_CANCLE事件,
     * 触发cancle事件就不会接收到up事件!!! 实际上当手指移动到了当前控件之外的时候,
     * 这个触摸事件被他的父控件拦截掉了,所以触发了cancle事件.之后的触摸事件就再也不会传递到当前控件的onTouchEvent里面.
     */
    private void actionCancel() {
        // 滑动事件重置
        mIsXMove = false;
        mIsYMove = false;
    }

    /**
     * 两个坐标点与 X轴的夹脚
     * 根据需求 夹脚45度以内 X轴
     * 夹脚45度以上 Y轴
     *
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     * @return 两个坐标点连线与X轴的夹脚
     */
    private int angle(int x1, int y1, int x2, int y2) {
        int x = Math.abs(x1 - x2);
        int y = Math.abs(y1 - y2);
        double z = Math.sqrt(x * x + y * y);
        return Math.round((float) (Math.asin(y / z) / Math.PI * 180));//最终角度
    }

    /**
     * 计算两个手指之间的距离。
     *
     * @param event
     * @return 两个手指之间的距离
     */
    private double distanceBetweenFingers(MotionEvent event) {
        float disX = Math.abs(event.getX(0) - event.getX(1));
        float disY = Math.abs(event.getY(0) - event.getY(1));
        return Math.sqrt(disX * disX + disY * disY);
    }

    // =============================================================================================


    /**
     * 单击计时器
     */
    private Runnable onCLickRn = new Runnable() {
        @Override
        public void run() {
            if (mIsClick && upCurrentTimeMillis - downCurrentTimeMillis < SHORT_CLICK_LIMIT) {
                mHandler.removeCallbacks(longClickRn);
                onClick();
            }
        }
    };


    /**
     * 长按事件计时器
     */
    private Runnable longClickRn = new Runnable() {
        @Override
        public void run() {
            if (mIsLongClick) {
                mIsDoubleClick = false;
                onLongClick = true;
                onLongClick();
            }
        }
    };


    /**
     * 取消长按事件
     */
    private void cancelLongClick() {
        // 判断事件戳 是否大于 600；
        if (mIsLongClick && upCurrentTimeMillis - downCurrentTimeMillis > 600) {
            mIsDoubleClick = false;
            mHandler.removeCallbacks(onCLickRn);
            onLongClick = false;
            onLongClickEnd();
        }
        // 重置判断标识条件
        mIsLongClick = false;
    }

    // =============================================================================================


    /**
     * 长按
     */
    public void onLongClick() {
        Log.i(TAG, "onLongClick: 触发长按事件");
    }

    /**
     * 长按结束
     */
    public void onLongClickEnd() {
        Log.i(TAG, "onLongClickEnd: 长按结束");
    }

    /**
     * 点击
     */
    public void onClick() {
        Log.i(TAG, "onClick: 单击事件");
    }

    /**
     * 双击
     */
    public void onDoubleClick() {
        Log.i(TAG, "onDoubleClick: 双击事件");
    }


    /**
     * X轴方向滑动
     *
     * @param size 滑动距离
     */
    public void onXMove(int size) {
        Log.i(TAG, "onXMove: X轴方向滑动 -->" + size);
    }

    /**
     * X轴方向滑动
     *
     * @param area 左边/右边
     * @param size 滑动距离
     */
    public void onYMove(int area, int size) {
        String ar = "左边区域";
        if (area == AREA_RIGHT) {
            ar = "右边区域";
        }
        Log.i(TAG, "onXMove: Y轴方向滑动 --> " + ar + size);
    }


    /**
     * X轴方向滑动结束
     *
     * @param size 滑动距离
     */
    public void onXMoveEnd(int size) {
        Log.i(TAG, "onXMoveEnd: X轴方向滑动结束 " + size);
    }


    /**
     * Y轴方向滑动结束
     *
     * @param area 左边/右边
     * @param size 滑动距离
     */
    public void onYMoveEnd(int area, int size) {
        Log.i(TAG, "onYMoveEnd: Y轴方向滑动结束" + size);
    }

    /**
     * 当前触点坐标是否在热区里面  --> 这个给子类去实现
     *
     * @param v
     * @param event
     * @return
     */
    public boolean isInHotspot(View v, MotionEvent event) {
        boolean isInHotspot = false;
        int y = (int) event.getY();

        if (y > 600) {
            isInHotspot = true;
        }
        return isInHotspot;
    }


    /**
     * 双指放大
     */
    public void onEnLarge() {
        Log.i(TAG, "onEnLarge: 双指放大");
    }

    /**
     * 双指缩小
     */
    public void onNarrow() {
        Log.i(TAG, "onNarrow: 双指缩小");
    }


}
