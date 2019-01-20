package com.example.lee.urgentstation;

import android.content.Context;
import android.util.AttributeSet;

public class AutoFirstTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    private Context mContext;

    public AutoFirstTextView(Context context) {
        super(context);
        mContext = context;
    }

    public AutoFirstTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public AutoFirstTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void performFiltering(CharSequence text,int keyCode) {
        //mFilter.filter(text, this);
    }

    @Override
    public void onFilterComplete(int count) {
    }
}