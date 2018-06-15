package com.bakingapp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.R;
import com.bakingapp.data.model.Recipe;
import com.bakingapp.data.model.Step;
import com.bakingapp.ui.activity.FullScreenVideoActivity;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link RecipeStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeStepFragment extends Fragment implements ExoPlayer.EventListener{
    private static final String TAG = RecipeStepFragment.class.getSimpleName();
    private static final String ARG_RECIPE_JSON = "arg_recipe_json";
    private static final String ARG_RECIPE_STEP_POSITION = "arg_recipe_position";
    public static final String KEY_VIDEO_URL = "video_url" ;

    private Unbinder unbinder;
    private String mRecipeJson;
    private Recipe mRecipe;
    private Step step;
    private int mStepPosition;
    @BindView(R.id.tv_short_description)
    TextView tvShortDescription;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_thumbnail)
    ImageView ivThumbnail;
    @BindView(R.id.player_view)
    SimpleExoPlayerView mPlayerView;
    @BindView(R.id.exo_fullscreen)
    ImageButton button_fullscreen;
    @BindView(R.id.button_open_video)
    Button buttonOpenVideo;
    private SimpleExoPlayer mPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private boolean isPhone;

    public RecipeStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RecipeStepFragment.
     */
    public static RecipeStepFragment newInstance(String param1, int stepPosition) {
        RecipeStepFragment fragment = new RecipeStepFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPE_JSON, param1);
        args.putInt(ARG_RECIPE_STEP_POSITION, stepPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepPosition = getArguments().getInt(ARG_RECIPE_STEP_POSITION);
            mRecipeJson = getArguments().getString(ARG_RECIPE_JSON);
            Gson gson = new Gson();
            mRecipe = gson.fromJson(mRecipeJson, Recipe.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        isPhone = !getResources().getBoolean(R.bool.isTablet);
        unbinder = ButterKnife.bind(this, view);
        step = mRecipe.getSteps().get(mStepPosition);
        tvDescription.setText(step.getDescription());
        tvShortDescription.setText(step.getShortDescription());
        if (step.getThumbnailURL() != null && step.getThumbnailURL().length() > 0){
            Glide.with(this).load(step.getThumbnailURL()).into(ivThumbnail);
        }
        if (!isPhone) {
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_arrow_black_24dp));
            if (step.getVideoURL() != null && step.getVideoURL().length() > 0) {
                if (step.getVideoURL() != null && step.getVideoURL().length() > 0) {
                    initializeMediaSession();
                    initializePlayer(Uri.parse(step.getVideoURL()));
                }
                mPlayerView.setVisibility(View.VISIBLE);
            } else {
                mPlayerView.setVisibility(View.GONE);
            }
        } else {
            if (step.getVideoURL() != null && step.getVideoURL().length() > 0) {
                buttonOpenVideo.setVisibility(View.VISIBLE);
            }
            mPlayerView.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            if (isPhone){
               openFullScreenVideo();
            }
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

    }

    private void openFullScreenVideo() {
        Intent intent = new Intent(getContext(), FullScreenVideoActivity.class);
        intent.putExtra(KEY_VIDEO_URL, step.getVideoURL());
        startActivity(intent);
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        if (mMediaSession != null) {
            mMediaSession.setActive(false);
        }
        unbinder.unbind();
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mPlayer.prepare(mediaSource);
            if (!isPhone) {
                mPlayer.setPlayWhenReady(true);
            }
        }
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mPlayer.seekTo(0);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isPhone && mPlayer != null)
        mPlayer.setPlayWhenReady(false);
    }

    @OnClick(R.id.exo_fullscreen)
    public void fullScreen() {
        openFullScreenVideo();
    }

    @OnClick(R.id.button_open_video)
    public void fullScreenByButton() {
        openFullScreenVideo();
    }

}
