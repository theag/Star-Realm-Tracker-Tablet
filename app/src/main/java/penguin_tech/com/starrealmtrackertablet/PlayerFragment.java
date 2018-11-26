package penguin_tech.com.starrealmtrackertablet;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PlayerFragment extends Fragment implements View.OnLayoutChangeListener {

    private static final String ARG_PLAYERNAME = "PlayerFragment.PlayerName";
    private static final String ARG_AUTHORITY = "PlayerFragment.Authority";
    private static final String ARG_TRADE = "PlayerFragment.Trade";
    private static final String ARG_COMBAT = "PlayerFragment.Combat";

    private static final String EDIT_AUTHORITY_DIALOG = "edit authority dialog";
    private static final String EDIT_TRADE_DIALOG = "edit trade dialog";
    private static final String EDIT_COMBAT_DIALOG = "edit combat dialog";

    private String playerName;
    private Tracker tracker;

    public PlayerFragment() {
        tracker = new Tracker();
    }

    public static PlayerFragment newInstance(String playerName) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYERNAME, playerName);
        fragment.setArguments(args);
        return fragment;
    }

    public static PlayerFragment newInstance(String playerName, int authority, int trade, int combat) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PLAYERNAME, playerName);
        args.putInt(ARG_AUTHORITY, authority);
        args.putInt(ARG_TRADE, trade);
        args.putInt(ARG_COMBAT, combat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            playerName = getArguments().getString(ARG_PLAYERNAME);
            if(getArguments().containsKey(ARG_AUTHORITY)) {
                tracker.setAuthority(getArguments().getInt(ARG_AUTHORITY));
                tracker.setTrade(getArguments().getInt(ARG_TRADE));
                tracker.setCombat(getArguments().getInt(ARG_COMBAT));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("Creating");
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        TextView tv = v.findViewById(R.id.txt_playerName);
        tv.setText(playerName);
        View iv;
        v.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        //authority
        v.findViewById(R.id.btn_authority_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        v.findViewById(R.id.btn_authority_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        iv = v.findViewById(R.id.image_authority);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        iv.addOnLayoutChangeListener(this);
        //trade
        v.findViewById(R.id.btn_trade_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        v.findViewById(R.id.btn_trade_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        iv = v.findViewById(R.id.image_trade);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        iv.addOnLayoutChangeListener(this);
        //combat
        v.findViewById(R.id.btn_combat_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        v.findViewById(R.id.btn_combat_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        iv = v.findViewById(R.id.image_combat);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPress(view);
            }
        });
        iv.addOnLayoutChangeListener(this);
        return v;
    }

    public void btnPress(View view) {
        switch(view.getId()) {
            case R.id.btn_authority_up:
                tracker.changeAuthority(1);
                ((TextView)getView().findViewById(R.id.text_authority)).setText("" + tracker.getAuthority());
                break;
            case R.id.btn_authority_down:
                tracker.changeAuthority(-1);
                ((TextView)getView().findViewById(R.id.text_authority)).setText("" + tracker.getAuthority());
                break;
            case R.id.btn_trade_up:
                tracker.changeTrade(1);
                ((TextView)getView().findViewById(R.id.text_trade)).setText("" + tracker.getTrade());
                break;
            case R.id.btn_trade_down:
                tracker.changeTrade(-1);
                ((TextView)getView().findViewById(R.id.text_trade)).setText("" + tracker.getTrade());
                break;
            case R.id.btn_combat_up:
                tracker.changeCombat(1);
                ((TextView)getView().findViewById(R.id.text_combat)).setText("" + tracker.getCombat());
                break;
            case R.id.btn_combat_down:
                tracker.changeCombat(-1);
                ((TextView)getView().findViewById(R.id.text_combat)).setText("" + tracker.getCombat());
                break;
            case R.id.btn_reset:
                tracker.reset();
                ((TextView)getView().findViewById(R.id.text_trade)).setText("" + tracker.getTrade());
                ((TextView)getView().findViewById(R.id.text_combat)).setText("" + tracker.getCombat());
                break;
            case R.id.image_authority:
                DialogFragment frag = new EditValueDialogFragment();
                Bundle args = new Bundle();
                args.putInt(EditValueDialogFragment.IMAGE, R.drawable.authority);
                args.putString(EditValueDialogFragment.TITLE, "Edit Authority");
                args.putInt(EditValueDialogFragment.INITAL_VALUE, tracker.getAuthority());
                args.putString(EditValueDialogFragment.FRAG_TAG, getTag());
                frag.setArguments(args);
                frag.show(getActivity().getSupportFragmentManager(), EDIT_AUTHORITY_DIALOG);
                break;
            case R.id.image_trade:
                frag = new EditValueDialogFragment();
                args = new Bundle();
                args.putInt(EditValueDialogFragment.IMAGE, R.drawable.trade);
                args.putString(EditValueDialogFragment.TITLE, "Edit Trade");
                args.putInt(EditValueDialogFragment.INITAL_VALUE, tracker.getTrade());
                args.putString(EditValueDialogFragment.FRAG_TAG, getTag());
                frag.setArguments(args);
                frag.show(getActivity().getSupportFragmentManager(), EDIT_TRADE_DIALOG);
                break;
            case R.id.image_combat:
                frag = new EditValueDialogFragment();
                args = new Bundle();
                args.putInt(EditValueDialogFragment.IMAGE, R.drawable.combat);
                args.putString(EditValueDialogFragment.TITLE, "Edit Combat");
                args.putInt(EditValueDialogFragment.INITAL_VALUE, tracker.getCombat());
                args.putString(EditValueDialogFragment.FRAG_TAG, getTag());
                frag.setArguments(args);
                frag.show(getActivity().getSupportFragmentManager(), EDIT_COMBAT_DIALOG);
                break;
        }
    }

    public void valueChanged(String tag, int value) {
        switch(tag) {
            case EDIT_AUTHORITY_DIALOG:
                tracker.setAuthority(value);
                ((TextView)getView().findViewById(R.id.text_authority)).setText("" + value);
                break;
            case EDIT_TRADE_DIALOG:
                tracker.setTrade(value);
                ((TextView)getView().findViewById(R.id.text_trade)).setText("" + value);
                break;
            case EDIT_COMBAT_DIALOG:
                tracker.setCombat(value);
                ((TextView)getView().findViewById(R.id.text_combat)).setText("" + value);
                break;
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        TextView tv = getView().findViewById(R.id.txt_playerName);
        tv.setText(playerName);
    }

    public int getAuthority() {
        return tracker.getAuthority();
    }

    public int getTrade() {
        return tracker.getTrade();
    }

    public int getCombat() {
        return tracker.getCombat();
    }

    public void newGame() {
        tracker = new Tracker();
        TextView tv = (TextView)getView().findViewById(R.id.text_authority);
        tv.setText(""+tracker.getAuthority());
        tv = (TextView)getView().findViewById(R.id.text_trade);
        tv.setText(""+tracker.getTrade());
        tv = (TextView)getView().findViewById(R.id.text_combat);
        tv.setText(""+tracker.getCombat());
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if(left != oldLeft || right != oldRight || top != oldTop || bottom != oldBottom) {
            String test, text;
            TextView txt;
            int width, height;
            switch (v.getId()) {
                case R.id.image_authority:
                    test = "00";
                    width = v.getWidth() / 2;
                    height = v.getHeight() / 2;
                    txt = (TextView) getView().findViewById(R.id.text_authority);
                    text = ""+tracker.getAuthority();
                    break;
                case R.id.image_trade:
                    test = "00";
                    width = 2 * v.getWidth() / 3;
                    height = 2 * v.getHeight() / 3;
                    txt = (TextView) getView().findViewById(R.id.text_trade);
                    text = ""+tracker.getTrade();
                    break;
                case R.id.image_combat:
                    test = "00";
                    width = 2 * v.getWidth() / 3;
                    height = 2 * v.getHeight() / 3;
                    txt = (TextView) getView().findViewById(R.id.text_combat);
                    text = ""+tracker.getCombat();
                    break;
                default:
                    return;
            }
            Paint p = txt.getPaint();
            float size = p.getTextSize();
            Rect bounds = new Rect();
            p.getTextBounds(test, 0, test.length(), bounds);
            while (bounds.width() < width && bounds.height() < height) {
                size++;
                p.setTextSize(size);
                p.getTextBounds(test, 0, test.length(), bounds);
            }
            size--;
            p.setTextSize(size);
            txt.setText(text);
        }
    }

}
