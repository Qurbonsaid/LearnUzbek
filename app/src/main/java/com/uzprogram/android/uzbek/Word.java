package com.uzprogram.android.uzbek;

public class Word {

    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Uzbek translation for the word */
    private String mUzbekTranslation;

    /** Audio resource ID for the word */
    private int mAudioResourceId;

    /** Image resource ID for the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param uzbekTranslation is the word in the Uzbek language
     * @param audioResourceId is the resource ID for the audio file associated with this word
     */
    public Word(String defaultTranslation, String uzbekTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mUzbekTranslation = uzbekTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param uzbekTranslation is the word in the Uzbek language
     * @param imageResourceId is the drawable resource ID for the image associated with the word
     * @param audioResourceId is the resource ID for the audio file associated with this word
     */
    public Word(String defaultTranslation, String uzbekTranslation, int imageResourceId,
                int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mUzbekTranslation = uzbekTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Uzbek translation of the word.
     */
    public String getUzbekTranslation() {
        return mUzbekTranslation;
    }

    /**
     * Return the image resource ID of the word.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    /**
     * Return the audio resource ID of the word.
     */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}