package com.evg_ivanoff.rickmortywiki.adapters;

import android.widget.Button;

public class ButtonAdapter {

    private Button btnNext;
    private Button btnPrev;
    private OnButtonNextUpdate onButtonNextUpdate;
    private OnButtonPrevUpdate onButtonPrevUpdate;

    public void setBtnNext(Button btnNext) {
        this.btnNext = btnNext;
    }

    public void setBtnPrev(Button btnPrev) {
        this.btnPrev = btnPrev;
    }

    public void setOnButtonNextUpdate(OnButtonNextUpdate onButtonNextUpdate) {
        this.onButtonNextUpdate = onButtonNextUpdate;
    }

    public void setOnButtonPrevUpdate(OnButtonPrevUpdate onButtonPrevUpdate) {
        this.onButtonPrevUpdate = onButtonPrevUpdate;
    }

    interface OnButtonNextUpdate{
        void OnButtonNext();
    }

    interface OnButtonPrevUpdate{
        void OnButtonPrev();
    }


}
