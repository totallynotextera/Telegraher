package org.telegram.ui;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.evildayz.code.telegraher.ThePenisMightierThanTheSword;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class ReadAllMentionsMenu {

    public final static int TYPE_REACTIONS = 0;
    public final static int TYPE_MENTIONS = 1;

    public static ActionBarPopupWindow show(int type, Activity activity, FrameLayout contentView, View mentionButton, Theme.ResourcesProvider resourcesProvider, Runnable onRead) {
        ActionBarPopupWindow.ActionBarPopupWindowLayout popupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(activity);
        popupWindowLayout.setMinimumWidth(AndroidUtilities.dp(200));

        ActionBarMenuSubItem cell = new ActionBarMenuSubItem(activity, true,true, resourcesProvider);
        cell.setMinimumWidth(AndroidUtilities.dp(200));
        cell.setTextAndIcon(type == TYPE_REACTIONS ? LocaleController.getString("ReadAllReactions", R.string.ReadAllReactions) : LocaleController.getString("ReadAllMentions", R.string.ReadAllMentions) , R.drawable.msg_seen);
        cell.setOnClickListener(view -> {
            if (onRead != null) {
                onRead.run();
            }
        });
        popupWindowLayout.addView(cell);

        ActionBarPopupWindow scrimPopupWindow = new ActionBarPopupWindow(popupWindowLayout, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT);
        scrimPopupWindow.setPauseNotifications(true);
        scrimPopupWindow.setDismissAnimationDuration(220);
        scrimPopupWindow.setOutsideTouchable(true);
        scrimPopupWindow.setClippingEnabled(true);
        scrimPopupWindow.setAnimationStyle(R.style.PopupContextAnimation);
        scrimPopupWindow.setFocusable(true);
        popupWindowLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000), View.MeasureSpec.AT_MOST));
        scrimPopupWindow.setInputMethodMode(ActionBarPopupWindow.INPUT_METHOD_NOT_NEEDED);
        scrimPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED);
        scrimPopupWindow.getContentView().setFocusableInTouchMode(true);

        float x = mentionButton.getX() + mentionButton.getWidth() - popupWindowLayout.getMeasuredWidth() + AndroidUtilities.dp(8);
        float y = mentionButton.getY() - popupWindowLayout.getMeasuredHeight();
        scrimPopupWindow.showAtLocation(contentView, Gravity.LEFT | Gravity.TOP, (int) x, (int) y);
        return scrimPopupWindow;
    }
}
