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
    private List<View> listChildViews = new ArrayList<View>();
    private LinearLayout tempLinearLayout;
    private int childViewWidth = 0;

    public AdjustableLayout(Context context) {
        super(context);
    }

    public AdjustableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AdjustableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        tempLinearLayout = createNewHorizontalLayout();
        super.addView(tempLinearLayout);
    }

    /**
     * Overriding add view.
     *
     * @param child Your custom view
     */
    @Override
    public void addView(View child) {
        //set orientation of parent layout vertical
        this.setOrientation(VERTICAL);

        //Give more margin to your custom view
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        child.setLayoutParams(layoutParams);

        //Now add your custom view horizontally in child layout
        addViewHorizontally(child);
    }

    /**
     * Function to add view horizontally
     *
     * @param child View
     */
    private void addViewHorizontally(View child) {
        if (listHorizontalLayouts.size() == 0) {
            addChildInNextLayout(child);
        } else {
            listChildViews.add(child);
            tempLinearLayout.addView(child);
            setViewObserver(child);
//            if (linearLayout.getWidth() + childViewWidth > getWidth()){
//                addChildInNextLayout(child);
//            }else {
//                linearLayout.addView(child);
//            }
        }
    }

    /**
     * Function to add child view in next layout.
     *
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
     *
     * @return LinearLayout
     */
    private LinearLayout createNewHorizontalLayout() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(layoutParams);
        return linearLayout;
    }

    /**
     * Function to set child view width.
     *
     * @param child        View
     */
    private void setViewObserver(final View child) {
        final LinearLayout linearLayout = listHorizontalLayouts.get(listHorizontalLayouts.size() - 1);
        child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                child.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                childViewWidth = child.getWidth();
                tempLinearLayout.removeView(child);
                listChildViews.remove(child);
                int presentLayoutWidth = linearLayout.getWidth();
                int parentLayoutWidth = getWidth();
                if (presentLayoutWidth + childViewWidth > parentLayoutWidth) {
                    addChildInNextLayout(child);
                } else {
                    linearLayout.addView(child);
                }
            }
        });
    }

    @Override
    public void removeView(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            linearLayout.removeView(view);
        }
    }
}
