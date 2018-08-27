package com.liskovsoft.smartyoutubetv.bootstrap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import com.liskovsoft.smartyoutubetv.R;

import java.util.ArrayList;
import java.util.Map;

public class GenericSelectorDialog implements OnClickListener {
    private final Context mActivity;
    private AlertDialog alertDialog;
    private ArrayList<CheckedTextView> mDialogItems;
    private final DataSource mDataSource;

    public interface DataSource {
        /**
         * Your data
         * @return pairs that consist of text/tag
         */
        Map<String, String> getDialogItems();
        String getSelected();
        void setSelected(String tag);
        void apply();
        String getTitle();
    }

    public static void create(Context ctx, DataSource dataSource) {
        GenericSelectorDialog dialog = new GenericSelectorDialog(ctx, dataSource);
        dialog.run();
    }

    public GenericSelectorDialog(Context activity, DataSource dataSource) {
        mActivity = activity;
        mDataSource = dataSource;
    }

    public void run() {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        alertDialog = builder.setTitle(mDataSource.getTitle()).setView(buildView(builder.getContext())).create();
        alertDialog.show();
    }

    @SuppressLint("InflateParams")
    private View buildView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lang_selection_dialog, null);
        ViewGroup root = view.findViewById(R.id.root);

        TypedArray attributeArray = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
        int selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0);
        attributeArray.recycle();

        mDialogItems = new ArrayList<>();

        for (Map.Entry<String, String> entry : mDataSource.getDialogItems().entrySet()) {
            CheckedTextView dialogItem = (CheckedTextView) inflater.inflate(android.R.layout.simple_list_item_single_choice, root, false);
            dialogItem.setBackgroundResource(selectableItemBackgroundResourceId);
            dialogItem.setText(entry.getKey());

            dialogItem.setFocusable(true);
            dialogItem.setTag(entry.getValue());
            dialogItem.setTextSize(TypedValue.COMPLEX_UNIT_PX, mActivity.getResources().getDimension(R.dimen.text_size_dp));
            dialogItem.setOnClickListener(this);
            mDialogItems.add(dialogItem);
            root.addView(dialogItem);
        }

        updateViews();

        return view;
    }

    private void updateViews() {
        for (CheckedTextView view : mDialogItems) {
            if (view.getTag().equals(mDataSource.getSelected())) {
                view.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (!tag.equals(mDataSource.getSelected())) {
            mDataSource.setSelected(tag);

            // close dialog
            alertDialog.dismiss();
            alertDialog = null;

            mDataSource.apply();
        }
    }
}
