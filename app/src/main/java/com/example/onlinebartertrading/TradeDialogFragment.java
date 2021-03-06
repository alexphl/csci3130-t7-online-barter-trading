package com.example.onlinebartertrading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.onlinebartertrading.entities.Post;

import java.util.UUID;

public class TradeDialogFragment extends DialogFragment {

    private String email;
    private String title;
    private String postId;
    private int value;

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public int getValue() {
        return value;
    }

    public String getPostId() {
        return postId;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_exchange, null))
                .setPositiveButton(R.string.submit_trade, (dialog, id) -> {
                    // Send the positive button event back to the host activity
                    listener.onDialogPositiveClick(TradeDialogFragment.this);
                })
                .setNegativeButton(R.string.cancel_trade, (dialog, id) -> {
                    // Send the negative button event back to the host activity
                    listener.onDialogNegativeClick(TradeDialogFragment.this);
                });

        this.email = getArguments().getString("email");
        this.title = getArguments().getString("title");
        this.value = getArguments().getInt("value");
        this.postId = getArguments().getString("id");

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                String item = ((EditText)dialog.findViewById(R.id.receiver_item)).getText().toString();
                String value1 = ((EditText)dialog.findViewById(R.id.receiver_value)).getText().toString();
                System.out.println(item + value);
                if(item.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), "Item cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(value1.isEmpty()) {
                    Toast.makeText(getActivity().getBaseContext(), "Value cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = this.getEmail();
                String title = this.getTitle();
                int value = this.getValue();
                String postId = this.getPostId();

                //Details entered by the receiver
                String receiver_item =  ((EditText)this.getDialog().findViewById(R.id.receiver_item)).getText().toString();
                String receiver_value =  ((EditText)this.getDialog().findViewById(R.id.receiver_value)).getText().toString();

                //Generate the exchange key

                ((PostListActivity)getActivity()).createHistoryProvider(email, postId, receiver_item, receiver_value);
                ((PostListActivity)getActivity()).createHistoryReceiver(title, postId, value);

                Toast.makeText(getActivity().getBaseContext(), "Successfully initialised trade.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        });

        return dialog;
    }

}
