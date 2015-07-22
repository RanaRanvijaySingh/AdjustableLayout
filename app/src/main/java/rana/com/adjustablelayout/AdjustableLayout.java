package rana.com.adjustablelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AdjustableLayout extends LinearLayout {

    private List<LinearLayout> listHorizontalLayouts = new ArrayList<LinearLayout>();
    private int childViewWidth = 0;

    public AdjustableLayout(Context context) {
        super(context);
    }

    public AdjustableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        this.setOrientation(VERTICAL);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,5,5,5);
        child.setLayoutParams(layoutParams);
        addViewHorizontally(child);
    }

    /**
     * Function to add view horizontally
     * @param child View
     */
    private void addViewHorizontally(View child) {
        if (listHorizontalLayouts.size() == 0){
            addChildInNextLayout(child);
        }else {
            setViewWidth(child);
            LinearLayout linearLayout = listHorizontalLayouts.get(listHorizontalLayouts.size()-1);
            if (linearLayout.getWidth() + childViewWidth > getWidth()){
                addChildInNextLayout(child);
            }else {
                linearLayout.addView(child);
            }
        }
    }

    /**
     * Function to add child view in next layout.
     * @param child View
     */
    private void addChildInNextLayout(View child) {
        LinearLayout linearLayout = createNewHorizontalLayout();
        linearLayout.addView(child);
        listHorizontalLayouts.add(linearLayout);
        super.addView(linearLayout);
    }

    /**
     * Function to create new linear layout with horizontal orientation.
     * @return LinearLayout
     */
    private LinearLayout createNewHorizontalLayout() {
        LinearLayout  linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    /**
     * Function to set child view width.
     * @param child View
     */
    private void setViewWidth(final View child) {
        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                child.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                childViewWidth = child.getWidth();
            }
        });
    }

    @Override
    public void removeView(View view) {
        for (int i=0;i<getChildCount();i++){
            LinearLayout linearLayout = (LinearLayout)getChildAt(i);
            linearLayout.removeView(view);
        }
    }
}
