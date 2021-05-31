package com.shacharnissan.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shacharnissan.minesweeper.logic.FileHelper;
import com.shacharnissan.minesweeper.logic.Score;
import com.shacharnissan.minesweeper.logic.Utils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class HighScoresFragment extends Fragment {

    private Button back_btn;
    private Context contextReference;
    private View viewReference;

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
        contextReference = getContext();
    }

    private void closeHighestScoreFragment() {
        getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragment", "onCreateView");
        // Inflate the layout for this fragment
        viewReference = inflater.inflate(R.layout.fragment_high_scores, container, false);
        back_btn  = viewReference.findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> closeHighestScoreFragment());

        loadHighestScoresFromFile();

        return viewReference;
    }

    private void loadHighestScoresFromFile() {
        Log.d("fragment", "loadHighestScoresFromFile");
        String filename = getResources().getString(R.string.Scores_Json_File);
        SharedPreferences sharedPref = contextReference.getSharedPreferences(filename,Context.MODE_PRIVATE);
        String jsonFileString = sharedPref.getString(getString(R.string.Scores_Json_String),
                                Utils.getJsonFromAssets(contextReference, "template_scores_json.json"));
        Gson gson = new Gson();
        Type listScoreType = new TypeToken<List<Score>>() {}.getType();
        scores = gson.fromJson(jsonFileString, listScoreType);
    }

    private void saveScoresToFragmentTable() {
        int viewId;
        String idName;
        TextView tv;
        for (int i = 0; i < scores.size(); i++)
        {
            idName = "row" + scores.get(i).getPlace();  //  the text "row"+index is by the id-names in the fragment layout
            viewId = getResources().getIdentifier(idName, "id", contextReference.getPackageName());
            tv = (TextView) viewReference.findViewById(viewId).findViewWithTag(scores.get(i).getStringDifficulty());
            tv.setText(scores.get(i).getTimeScore());
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