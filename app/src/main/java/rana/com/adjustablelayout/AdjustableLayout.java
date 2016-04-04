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

    private int childViewCount = 0;
    private List<LinearLayout> listHorizontalLayouts = new ArrayList<LinearLayout>();
    private List<View> listChildViews = new ArrayList<View>();
    private List<Integer> listChildViewWidth = new ArrayList<Integer>();
    private LinearLayout tempLinearLayout;
    private int childViewWidth;
    private boolean isCalledFirstTime = true;

    public AdjustableLayout(Context context) {
        super(context);
    }

    public AdjustableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //=====================================================================================
    //=====================================================================================
    //======================Code for adding ONE view at a time========================
    //=====================================================================================
    //=====================================================================================

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
            setViewWidth(child);
            LinearLayout linearLayout = listHorizontalLayouts.get(listHorizontalLayouts.size() - 1);
            if (linearLayout.getWidth() + childViewWidth > getWidth()) {
                addChildInNextLayout(child);
            } else {
                linearLayout.addView(child);
            }
        }
    }

    /**
     * Function to set child view width.
     *
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

    //=====================================================================================
    //=====================================================================================
    //======================Code for adding MULTIPLE view at a time========================
    //=====================================================================================
    //=====================================================================================

    public void addingMultipleView(View child) {
        //set orientation of parent layout vertical
        this.setOrientation(VERTICAL);
        listChildViews.add(child);
        //Give more margin to your custom view
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        child.setLayoutParams(layoutParams);
    }

    /**
     * Function to invalidate view or you can say to populate the
     * absolute layout with all the view you have added
     * <p/>
     * NOTE: THIS FUNCTION NEED TO BE CALLED ONCE. IN CASE YOU CALL TWICE (may generate un wanted result in case you do).
     */
    public void invalidateView() {
        //>>>>>>>>>>>>>>>>>>>>>> ISSUE : Clear views and add again>>>>>>>>>>>>>
        if (listChildViews.size() > 0 && isCalledFirstTime) {
            isCalledFirstTime = false;
            //>>>>>>>>>>>>>>>>>>>>>> ISSUE : Clear views and add again>>>>>>>>>>>>>
            tempLinearLayout = createNewHorizontalLayout();
            super.addView(tempLinearLayout);
            setViewObserver(listChildViews.get(childViewCount));
        }
    }

    /**
     * Function to set child view width.
     *
     * @param childView View
     */
    private void setViewObserver(final View childView) {
        tempLinearLayout.addView(childView);
        childView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                childView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int childWidth = childView.getMeasuredWidth() + 20;
                tempLinearLayout.removeView(childView);
                if (childViewCount > listChildViews.size() - 1) {
                    addViewInParent();
                    return;
                }
                listChildViewWidth.add(childWidth);
                setViewObserver(listChildViews.get(childViewCount++));
            }
        });
    }

    /**
     * Function to add view in parent layout
     */
    private void addViewInParent() {
        this.removeView(tempLinearLayout);
        for (int i = 0; i < listChildViews.size(); i++) {
            LinearLayout linearLayout;
            if (listHorizontalLayouts.size() > 0) {
                linearLayout = listHorizontalLayouts.get(listHorizontalLayouts.size() - 1);
            } else {
                linearLayout = createNewHorizontalLayout();
                listHorizontalLayouts.add(linearLayout);
                super.addView(linearLayout);
            }
            int presentLayoutWidth = getWidthOf(linearLayout, i);
            int parentLayoutWidth = getWidth() - 5;
            if (presentLayoutWidth + listChildViewWidth.get(i) + 10 > parentLayoutWidth) {
                addChildInNextLayout(listChildViews.get(i));
            } else {
                linearLayout.addView(listChildViews.get(i));
            }
        }
    }

    /**
     * Function to get layout width based on number of child it has and then width of individual chile
     *
     * @param linearLayout LinearLayout
     * @param position     int
     * @return int
     */
    private int getWidthOf(LinearLayout linearLayout, int position) {
        int childCount = linearLayout.getChildCount();
        int startPoint = position - 1;
        int endPoint = position - childCount;
        int layoutWidthSum = 0;
        for (int i = startPoint; i >= endPoint; i--) {
            layoutWidthSum += listChildViewWidth.get(i);
        }
        return layoutWidthSum;
    }

    //=====================================================================================
    //=====================================================================================
    //======================Common function ===============================================
    //=====================================================================================
    //=====================================================================================


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

    @Override
    public void removeView(View view) {
        for (int i = 0; i < getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            linearLayout.removeView(view);
        }
    }

    //>>>>>>>>>>>>>>>>>>>>>> ISSUE : Clear views and add again>>>>>>>>>>>>>
    @Override
    public void removeAllViews() {
        super.removeAllViews();
        if (listHorizontalLayouts != null) {
            listHorizontalLayouts.clear();
        }
        if (listChildViews != null) {
            listChildViews.clear();
        }
        if (listChildViewWidth != null) {
            listChildViewWidth.clear();
        }
        childViewCount = 0;
        isCalledFirstTime = true;
    }
    //>>>>>>>>>>>>>>>>>>>>>> ISSUE : Clear views and add again>>>>>>>>>>>>>
}
