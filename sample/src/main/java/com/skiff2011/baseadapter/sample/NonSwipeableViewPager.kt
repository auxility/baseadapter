package com.skiff2011.baseadapter.sample

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller

class NonSwipeableViewPager @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

  init {
    setMyScroller()
  }

  override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
    // Never allow swiping to switch between pages
    return false
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    // Never allow swiping to switch between pages
    return false
  }

  //down one is added for smooth scrolling

  private fun setMyScroller() {
    try {
      val viewpager = ViewPager::class.java
      val scroller = viewpager.getDeclaredField("mScroller")
      scroller.isAccessible = true
      scroller.set(this, MyScroller(context))
    } catch (e: Exception) {
      e.printStackTrace()
    }

  }

  inner class MyScroller(context: Context) : Scroller(context, DecelerateInterpolator()) {

    override fun startScroll(
      startX: Int,
      startY: Int,
      dx: Int,
      dy: Int,
      duration: Int
    ) {
      super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/)
    }
  }

}