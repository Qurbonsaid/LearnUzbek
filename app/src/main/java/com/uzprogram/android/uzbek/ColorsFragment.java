package com.uzprogram.android.uzbek;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorsFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(getString(R.string.color_red), "qizil", R.drawable.color_red, R.raw.color_red));
        words.add(new Word(getString(R.string.color_blue), "ko'k", R.drawable.color_blue, R.raw.color_blue));
        words.add(new Word(getString(R.string.color_yellow), "sariq", R.drawable.color_yellow, R.raw.color_yellow));
        words.add(new Word(getString(R.string.color_green), "yashil", R.drawable.color_green, R.raw.color_green));
        words.add(new Word(getString(R.string.color_brown), "jigarrang", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word(getString(R.string.color_pink), "pushti", R.drawable.color_pink, R.raw.color_pink));
        words.add(new Word(getString(R.string.color_orange), "olovrang", R.drawable.color_orange, R.raw.color_orange));
        words.add(new Word(getString(R.string.color_gray), "kulrang", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word(getString(R.string.color_black), "qora", R.drawable.color_black, R.raw.color_black));
        words.add(new Word(getString(R.string.color_white), "oq", R.drawable.color_white, R.raw.color_white));

        WordAdapter wordAdapter = new WordAdapter(getContext(), words, R.color.category_colors);

        ListView listView = (ListView) inflater.inflate(R.layout.word_list, container, false);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();

                Word word = words.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getContext(), word.getAudioResourceId());

                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return listView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
