/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gdgmallorcawear;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.ImageReference;

import java.util.ArrayList;

/**
 * Constructs fragments as requested by the GridViewPager. For each row a
 * different background is provided.
 */
public class SampleGridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private ArrayList<Event> mEvents = new ArrayList<Event>();

    public SampleGridPagerAdapter(Context ctx, FragmentManager fm, ArrayList<Event> events) {
        super(fm);
        mContext = ctx;
        mEvents = events;
    }

    static final int[] BG_IMAGES = new int[]{
            R.drawable.card_background,
            R.drawable.card_background,
            R.drawable.card_background,
            R.drawable.card_background,
            R.drawable.card_frame_pressed
    };


    @Override
    public Fragment getFragment(int row, int col) {
        String title = "xxxx";
        String text = "yyy";
        CardFragment fragment = CardFragment.create(title, text);

        return fragment;
    }

    @Override
    public ImageReference getBackground(int row, int column) {
        return ImageReference.forDrawable(BG_IMAGES[row % BG_IMAGES.length]);
    }

    @Override
    public int getRowCount() {
        return mEvents.size();
    }

    @Override
    public int getColumnCount(int rowNum) {
        return rowNum;
    }
}
