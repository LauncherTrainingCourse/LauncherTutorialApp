package com.example.richo_han.tutorialapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

/**
 * Created by Richo_Han on 2016/11/18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private Context mContext;
    private String tabTitles[] = new String[] { "Contact", "Message", "Group" };
    private int[] imageResId = new int[]{
            R.drawable.ic_call_black_48px,
            R.drawable.ic_message_black_48px,
            R.drawable.ic_people_black_48px
    };

    public ViewPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() { return PAGE_COUNT; }

    /***
     * Used to return page fragment based on the current tab.
     * @param position
     * @return the corresponding page fragment instance.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ContactPageFragment();
            case 1:
                return new MessagePageFragment();
            case 2:
                return new GroupPageFragment();
            default:
                return null;
        }
    }

    /***
     * Used to generate tab with icons and titles.
     * @param position
     * @return String with icon and title.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = ContextCompat.getDrawable(this.mContext, imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
