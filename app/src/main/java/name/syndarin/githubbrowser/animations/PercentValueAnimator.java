package name.syndarin.githubbrowser.animations;

import android.support.percent.PercentLayoutHelper;

/**
 * Created by vtiahotenkov on 23.03.17.
 */

public class PercentValueAnimator {

    private PercentLayoutHelper.PercentLayoutInfo percentLayoutInfo;

    public PercentValueAnimator(PercentLayoutHelper.PercentLayoutInfo percentLayoutInfo) {
        this.percentLayoutInfo = percentLayoutInfo;
    }

    public void setWidthPercent(float percent) {
        percentLayoutInfo.widthPercent = percent;
    }

}
