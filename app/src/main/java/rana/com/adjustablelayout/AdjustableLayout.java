package rana.com.adjustablelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AdjustableLayout extends LinearLayout {

    private List<LinearLayout> listHorizontalLayouts = new ArrayList<LinearLayout>();
    private List<View> listPresentChilds = new ArrayList<View>();
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
        addViewHorizontally(child);

    }

    /**
     * Function to add view horizontally
     * @param child View
     */
    private void addViewHorizontally(View child) {
        int layoutWidth = getWidth();
        int childCount = getChildCount();
        Log.i("", layoutWidth + "   layoutWidth    " + childCount + "   childCount");
        if (listHorizontalLayouts.size() == 0){
            addChildInNextLayout(child);
        }else {
            setViewWidth(child);
            LinearLayout linearLayout = listHorizontalLayouts.get(listHorizontalLayouts.size()-1);
            Log.i("", linearLayout.getWidth() + "   linearLayout.getWidth()    "+" child view width "+child.getWidth());
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
                Log.i("", " child view width "+child.getWidth());
            }
        });
    }
}
