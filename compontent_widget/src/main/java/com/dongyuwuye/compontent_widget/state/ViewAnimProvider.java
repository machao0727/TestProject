package com.dongyuwuye.compontent_widget.state;

import android.view.animation.Animation;

/**
 * ViewAnimProvider
 *
 * @author mc
 * create at 2017/9/19 10:18
 */
public interface ViewAnimProvider {
    Animation showAnimation();

    Animation hideAnimation();
}
