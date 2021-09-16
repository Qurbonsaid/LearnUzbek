package com.uzprogram.android.uzbek;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersFragment extends Fragment {

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
        words.add(new Word(getString(R.string.number_one), "bir", R.drawable.number_one, R.raw.number_one));
        words.add(new Word(getString(R.string.number_two), "ikki", R.drawable.number_two, R.raw.number_two));
        words.add(new Word(getString(R.string.number_three), "uch", R.drawable.number_three, R.raw.number_three));
        words.add(new Word(getString(R.string.number_four), "to'rt", R.drawable.number_four, R.raw.number_four));
        words.add(new Word(getString(R.string.number_five), "besh", R.drawable.number_five, R.raw.number_five));
        words.add(new Word(getString(R.string.number_six), "olti", R.drawable.number_six, R.raw.number_six));
        words.add(new Word(getString(R.string.number_seven), "yetti", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(getString(R.string.number_eight), "sakkiz", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(getString(R.string.number_nine), "to'qqiz", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(getString(R.string.number_ten), "o'n", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter wordAdapter = new WordAdapter(getContext(), words, R.color.category_numbers);

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
