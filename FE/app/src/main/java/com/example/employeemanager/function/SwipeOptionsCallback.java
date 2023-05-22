package com.example.employeemanager.function;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.employeemanager.R;
import com.example.employeemanager.ui.NhanVienFragment;

public class SwipeOptionsCallback extends ItemTouchHelper.Callback {
    private final Context context;
    private final Drawable deleteIcon;
    private final Drawable updateIcon;
    private final Paint textPaint;
    private final Drawable deleteBackground;
    private final Drawable updateBackground;
    private final String deleteText;
    private final String updateText;
    private final int iconMargin;
    private final int textMargin;
    NhanVienFragment nhanVienFragment;

    public SwipeOptionsCallback(NhanVienFragment nhanVienFragment, Context context) {
        this.nhanVienFragment = nhanVienFragment;
        this.context = context;

        // Initialize the delete icon and update icon
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_24);
        updateIcon = ContextCompat.getDrawable(context, R.drawable.baseline_edit_24);

        // Initialize the delete text and update text
        deleteText = context.getString(R.string.delete_text);
        updateText = context.getString(R.string.update_text);

        // Initialize the text paint
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.swipe_text_size));
        textPaint.setAntiAlias(true);

        // Initialize the delete and update background
        deleteBackground = new ColorDrawable(ContextCompat.getColor(context, R.color.delete_background_color));
        updateBackground = new ColorDrawable(ContextCompat.getColor(context, R.color.update_background_color));

        // Initialize the icon and text margins
        iconMargin = context.getResources().getDimensionPixelSize(R.dimen.icon_margin);
        textMargin = context.getResources().getDimensionPixelSize(R.dimen.text_margin);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // Set the swipe directions
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Perform the necessary action based on swipe direction
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            // Handle left swipe (delete)
            nhanVienFragment.onLeftSwipe(position);
        } else if (direction == ItemTouchHelper.RIGHT) {
            // Handle right swipe (update)
            nhanVienFragment.onRightSwipe(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();
        int itemWidth = itemView.getWidth();

        if (dX > 0) {
            // Swiping to the right (update)
            int updateIconTop = itemView.getTop() + (itemHeight - updateIcon.getIntrinsicHeight()) / 2;
            int updateIconBottom = updateIconTop + updateIcon.getIntrinsicHeight();
            int updateIconRight = itemView.getLeft() + iconMargin + updateIcon.getIntrinsicWidth();
            int updateIconLeft = itemView.getLeft() + iconMargin;

            // Calculate the text height
            float updateTextHeight = textPaint.descent() - textPaint.ascent();

            // Calculate the vertical position to align the text below the icon
            float updateTextTop = updateIconBottom + textMargin + updateTextHeight;

            // Calculate the horizontal position to center the text
            float updateTextLeft = updateIconLeft + ((updateIconRight - updateIconLeft) - textPaint.measureText(updateText)) / 2;

            // Draw the update background
            int updateBackgroundLeft = itemView.getLeft();
            int updateBackgroundRight = itemView.getLeft() + (int) dX;
            updateBackground.setBounds(updateBackgroundLeft, itemView.getTop(), updateBackgroundRight, itemView.getBottom());
            updateBackground.draw(c);

            // Draw the update icon
            Rect updateIconBounds = new Rect(
                    updateIconLeft,
                    updateIconTop,
                    updateIconRight,
                    updateIconBottom
            );
            updateIcon.setBounds(updateIconBounds);
            updateIcon.draw(c);

            // Draw the update text
            c.drawText(updateText, updateTextLeft, updateTextTop, textPaint);
        } else if (dX < 0) {
            // Swiping to the left (delete)
            int deleteIconTop = itemView.getTop() + (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
            int deleteIconBottom = deleteIconTop + deleteIcon.getIntrinsicHeight();
            int deleteIconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
            int deleteIconRight = itemView.getRight() - iconMargin;

            // Calculate the text height
            float deleteTextHeight = textPaint.descent() - textPaint.ascent();

            // Calculate the vertical position to align the text below the icon
            float deleteTextTop = deleteIconBottom + textMargin + deleteTextHeight;

            // Calculate the horizontal position to center the text
            float deleteTextLeft = deleteIconLeft + ((deleteIconRight - deleteIconLeft) - textPaint.measureText(deleteText)) / 2;

            // Draw the delete background
            int deleteBackgroundLeft = itemView.getRight() + (int) dX;
            int deleteBackgroundRight = itemView.getRight();
            deleteBackground.setBounds(deleteBackgroundLeft, itemView.getTop(), deleteBackgroundRight, itemView.getBottom());
            deleteBackground.draw(c);

            // Draw the delete icon
            Rect deleteIconBounds = new Rect(
                    deleteIconLeft,
                    deleteIconTop,
                    deleteIconRight,
                    deleteIconBottom
            );
            deleteIcon.setBounds(deleteIconBounds);
            deleteIcon.draw(c);

            // Draw the delete text
            c.drawText(deleteText, deleteTextLeft, deleteTextTop, textPaint);
        }
    }

}