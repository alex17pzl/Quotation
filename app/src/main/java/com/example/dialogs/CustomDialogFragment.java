package com.example.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.quotation.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CustomDialogFragment extends DialogFragment {

    public CustomDialogFragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setMessage(getString(R.string.deleteItem));
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getParentFragmentManager().setFragmentResult("remove_all", new Bundle());
            }
        });
        builder.setNegativeButton("No", null);

        return builder.create();
    }
}
