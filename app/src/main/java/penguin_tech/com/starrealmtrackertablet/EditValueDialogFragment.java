package penguin_tech.com.starrealmtrackertablet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import penguin_tech.com.starrealmtrackertablet.calc.*;

public class EditValueDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String IMAGE = "image";
    public static final String TITLE = "title";
    public static final String INITAL_VALUE = "initial value";
    public static final String FRAG_TAG = "frag tag";

    Calculator calculator;
    TextView result, expression;
    AlertDialog alertDialog;
    String fragTag;
    EditValueListener listener;

    public EditValueDialogFragment() {
        super();
        calculator = new Calculator();
        listener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EditValueListener) {
            listener = (EditValueListener)context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        fragTag = getArguments().getString(FRAG_TAG);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_edit_value, null);
        ImageView img = (ImageView)v.findViewById(R.id.imge_type);
        img.setImageResource(getArguments().getInt(IMAGE));
        TextView tv = (TextView)v.findViewById(R.id.text_title);
        tv.setText(getArguments().getString(TITLE));
        expression = (TextView)v.findViewById(R.id.edit_expression);
        expression.setText("" + getArguments().getInt(INITAL_VALUE));
        result = (TextView)v.findViewById(R.id.text_result);
        result.setText(calculator.partialCalculation("" + getArguments().getInt(INITAL_VALUE)));
        v.findViewById(R.id.btn_0).setOnClickListener(this);
        v.findViewById(R.id.btn_1).setOnClickListener(this);
        v.findViewById(R.id.btn_2).setOnClickListener(this);
        v.findViewById(R.id.btn_3).setOnClickListener(this);
        v.findViewById(R.id.btn_4).setOnClickListener(this);
        v.findViewById(R.id.btn_5).setOnClickListener(this);
        v.findViewById(R.id.btn_6).setOnClickListener(this);
        v.findViewById(R.id.btn_7).setOnClickListener(this);
        v.findViewById(R.id.btn_8).setOnClickListener(this);
        v.findViewById(R.id.btn_9).setOnClickListener(this);
        v.findViewById(R.id.btn_plus).setOnClickListener(this);
        v.findViewById(R.id.btn_minus).setOnClickListener(this);
        v.findViewById(R.id.btn_backspace).setOnClickListener(this);
        builder.setView(v)
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null);
        alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                doShow();
            }
        });
        return alertDialog;
    }

    private void doShow() {
        Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(this);
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_0:
                expression.append("0");
                break;
            case R.id.btn_1:
                expression.append("1");
                break;
            case R.id.btn_2:
                expression.append("2");
                break;
            case R.id.btn_3:
                expression.append("3");
                break;
            case R.id.btn_4:
                expression.append("4");
                break;
            case R.id.btn_5:
                expression.append("5");
                break;
            case R.id.btn_6:
                expression.append("6");
                break;
            case R.id.btn_7:
                expression.append("7");
                break;
            case R.id.btn_8:
                expression.append("8");
                break;
            case R.id.btn_9:
                expression.append("9");
                break;
            case R.id.btn_plus:
                expression.append("+");
                break;
            case R.id.btn_minus:
                expression.append("-");
                break;
            case R.id.btn_backspace:
                String s = expression.getText().toString();
                if(!s.isEmpty()) {
                    expression.setText(s.substring(0, s.length() - 1));
                }
                break;
            default:
                positiveButtonClick();
                return;
        }
        result.setText(calculator.partialCalculation(expression.getText().toString()));
    }

    private void positiveButtonClick() {
        if(calculator.hasResult()) {
            int value = (int)calculator.getResult();
            if(value == calculator.getResult()) {
                alertDialog.dismiss();
                if (listener != null) {
                    listener.valueChanged(fragTag, getTag(), value);
                }
            } else {
                MessageDialogFragment frag = new MessageDialogFragment();
                Bundle args = new Bundle();
                args.putString(MessageDialogFragment.TITLE, "Error");
                args.putString(MessageDialogFragment.MESSAGE, "Value must be an integer.");
                frag.setArguments(args);
                frag.show(getActivity().getSupportFragmentManager(), "EditValueDialogFragment.Message");
            }
        } else {
            try {
                calculator.finalCalculation();
                int value = (int)calculator.getResult();
                if(value == calculator.getResult()) {
                    alertDialog.dismiss();
                    if (listener != null) {
                        listener.valueChanged(fragTag, getTag(), value);
                    }
                } else {
                    MessageDialogFragment frag = new MessageDialogFragment();
                    Bundle args = new Bundle();
                    args.putString(MessageDialogFragment.TITLE, "Error");
                    args.putString(MessageDialogFragment.MESSAGE, "Value must be an integer.");
                    frag.setArguments(args);
                    frag.show(getActivity().getSupportFragmentManager(), "EditValueDialogFragment.Message");
                }
            } catch (CalculatorException eRef) {
                MessageDialogFragment frag = new MessageDialogFragment();
                Bundle args = new Bundle();
                args.putString(MessageDialogFragment.TITLE, "Error");
                args.putString(MessageDialogFragment.MESSAGE, eRef.getMessage());
                frag.setArguments(args);
                frag.show(getActivity().getSupportFragmentManager(), "EditValueDialogFragment.Message");
            }
        }
    }

    public interface EditValueListener {
        void valueChanged(String fragTag, String tag, int value);
    }

}
