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

public class PhrasesFragment extends Fragment {

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
        words.add(new Word(getString(R.string.phrase_where_are_you_going), "Qayerga ketayapsiz?", R.raw.phrase_where_are_you_going));
        words.add(new Word(getString(R.string.phrase_what_is_your_name), "Ismingiz nima?", R.raw.phrase_what_is_your_name));
        words.add(new Word(getString(R.string.phrase_my_name_is), "Mening ismim...", R.raw.phrase_my_name_is));
        words.add(new Word(getString(R.string.phrase_how_are_you_feeling), "O'zingizni qanday his qilayapsiz?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word(getString(R.string.phrase_im_feeling_good), "O'zimni yaxshi his qilayapman.", R.raw.phrase_im_feeling_good));
        words.add(new Word(getString(R.string.phrase_are_you_coming), "Kelayapsizmi?", R.raw.phrase_are_you_coming));
        words.add(new Word(getString(R.string.phrase_yes_im_coming), "Ha, kelayapman.", R.raw.phrase_yes_im_coming));
        words.add(new Word(getString(R.string.phrase_im_coming), "Men kelayapman.", R.raw.phrase_im_coming));
        words.add(new Word(getString(R.string.phrase_lets_go), "Qani ketdik.", R.raw.phrase_lets_go));
        words.add(new Word(getString(R.string.phrase_come_here), "Shu yerga keling.", R.raw.phrase_come_here));

        WordAdapter wordAdapter = new WordAdapter(getContext(), words, R.color.category_phrases);

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
