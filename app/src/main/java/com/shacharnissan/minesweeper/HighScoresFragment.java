package com.shacharnissan.minesweeper;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shacharnissan.minesweeper.logic.DifficultyEnum;
import com.shacharnissan.minesweeper.logic.Score;
import com.shacharnissan.minesweeper.logic.Utils;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;


public class HighScoresFragment extends Fragment {

    private Button back_btn;
    private Context contextRefference;
    private View viewRefference;

    private List<Score> scores;

    public HighScoresFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HighScoresFragment newInstance() {
        HighScoresFragment fragment = new HighScoresFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("fragment", "onCreate");
        super.onCreate(savedInstanceState);
        contextRefference = getContext();
    }

    private void closeHighestScoreFragment() {
        // TODO
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment", "onCreateView");
        // Inflate the layout for this fragment
        viewRefference = inflater.inflate(R.layout.fragment_high_scores, container, false);
        back_btn  = viewRefference.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> closeHighestScoreFragment());

        loadHighestScoresFromFile();

        return viewRefference;
    }

    private void loadHighestScoresFromFile() {
        Log.d("fragment", "loadHighestScoresFromFile");
        String jsonFileString = Utils.getJsonFromAssets(contextRefference, "highest_scores_json.json");
//        Log.i("data", jsonFileString);
        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<Score>>() {}.getType();
        scores = gson.fromJson(jsonFileString, listUserType);
        /*
        for (int i = 0; i < scores.size(); i++) {
            Log.i("data", "> Item " + i + "\n" + scores.get(i));
        }
        */
    }

    private void saveScoresToFragmentTable() {
        int viewId;
        String idName;
        TextView tv;

        for (int i = 0; i < scores.size(); i++)
        {
            idName = "row" + scores.get(i).getPlace();
            Log.d("idName", ""+ idName);

            viewId = getResources().getIdentifier(idName, "id", contextRefference.getPackageName());

            Log.d("viewId ",viewId + "");
            Log.d("viewTag","" + scores.get(i).getDifficulty());
            Log.d("newText", "" + scores.get(i).getTimeScore());

            tv = (TextView)viewRefference.findViewById(viewId).findViewWithTag(scores.get(i).getDifficulty());

            // TODO : ...

           // tv.setText(scores.get(i).getTimeScore());
            //((TextView)viewRefference.findViewById(viewId).findViewWithTag(scores.get(i).getDifficulty())).setText(scores.get(i).getTimeScore());
        }
    }

    @Override
    public void onStart() {
        Log.d("fragment", "onStart");
        super.onStart();
        saveScoresToFragmentTable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("fragment", "onDestroy");
    }


    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public Button getBack_btn() {
        return back_btn;
    }

    public void setBack_btn(Button back_btn) {
        this.back_btn = back_btn;
    }
}
