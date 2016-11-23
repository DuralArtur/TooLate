package com.example.android.toolate;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordSlideFragment extends Fragment {
    private String word;
    @BindView(R.id.word)
    TextView wordTV;

    public WordSlideFragment() {
        // Required empty public constructor
    }

    public static WordSlideFragment newInstance(String word){
        Bundle args = new Bundle();
        args.putString("WORD",word);
        WordSlideFragment wordSlideFragment = new WordSlideFragment();
        wordSlideFragment.setArguments(args);
        return wordSlideFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_word_slide, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            word = bundle.getString("WORD");
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wordTV.setText(word);
    }
}
