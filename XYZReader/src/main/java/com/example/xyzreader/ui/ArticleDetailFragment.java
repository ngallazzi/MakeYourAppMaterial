package com.example.xyzreader.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = "ArticleDetailFragment";

    public static final String ARG_ITEM_ID = "item_id";

    private Cursor mCursor;
    private long mItemId;
    private View mRootView;
    private ImageView mPhotoView;
    private Context mContext;
    private AppCompatActivity mActivity;
    private Toolbar tbDetailArticle;
    private NestedScrollView svArticleContainer;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public static ArticleDetailFragment newInstance(long itemId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ITEM_ID, itemId);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // In support library r8, calling initLoader for a fragment in a FragmentPagerAdapter in
        // the fragment's onCreate may cause the same LoaderManager to be dealt to multiple
        // fragments because their mIndex is -1 (haven't been added to the activity yet). Thus,
        // we do this in onActivityCreated.
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        mPhotoView = (ImageView) mRootView.findViewById(R.id.dhiwDetailArticlePhoto);
        mRootView.findViewById(R.id.fabShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText("Some sample text")
                        .getIntent(), getString(R.string.action_share)));
            }
        });
        svArticleContainer = (NestedScrollView) mRootView.findViewById(R.id.svArticleContainer);
        setUpToolbar();
        bindViews();
        return mRootView;
    }



    private void setUpToolbar(){
        tbDetailArticle = (Toolbar) mRootView.findViewById(R.id.tbDetailArticle);
        mActivity.setSupportActionBar(tbDetailArticle);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbDetailArticle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void bindViews() {
        if (mRootView == null) {
            return;
        }
        TextView tvDetailArticleBody = (TextView) mRootView.findViewById(R.id.tvDetailArticleBody);
        final TextView tvDetailArticleInfos = (TextView) mRootView.findViewById(R.id.tvDetailArticleInfos);
        TextView tvDetailArticleTitle = (TextView) mRootView.findViewById(R.id.tvDetailArticleTitle);

        CollapsingToolbarLayout ctlDetailArticle = (CollapsingToolbarLayout) mRootView.findViewById(R.id.ctlDetailArticle);
        tvDetailArticleBody.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));

        if (mCursor != null) {
            String itemTitle = mCursor.getString(ArticleLoader.Query.TITLE);
            ctlDetailArticle.setTitle(itemTitle);
            ctlDetailArticle.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            tvDetailArticleTitle.setText(itemTitle);
            tvDetailArticleBody.setText(Html.fromHtml(mCursor.getString(ArticleLoader.Query.BODY)));
            Uri imageUri = Uri.parse(mCursor.getString(ArticleLoader.Query.PHOTO_URL));
            //Picasso.with(mContext).load(imageUri).into(mPhotoView);
            Picasso.with(mContext)
                    .load(imageUri)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                            /* Save the bitmap or do something with it here */
                            //Set it in the ImageView
                            mPhotoView.setImageBitmap(bitmap);
                            setArticleInfosColor(bitmap,tvDetailArticleInfos);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
            mRootView.animate().alpha(1);
        }
    }



    public void setArticleInfosColor(Bitmap bitmap, TextView tvInfos) {
        Spanned publishingDateAndAuthor = Html.fromHtml(
                DateUtils.getRelativeTimeSpanString(
                        mCursor.getLong(ArticleLoader.Query.PUBLISHED_DATE),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL).toString()
                        + " by " + mCursor.getString(ArticleLoader.Query.AUTHOR));
        if (bitmap != null && !bitmap.isRecycled()) {
            Palette p = createPaletteSync(bitmap);
            Palette.Swatch vibrantSwatch = checkVibrantSwatch(p);
            if (vibrantSwatch!=null){
                tvInfos.setTextColor(vibrantSwatch.getRgb());
            }
        }
        tvInfos.setText(publishingDateAndAuthor);
    }

    public Palette createPaletteSync(Bitmap bitmap) {
        Palette p = Palette.from(bitmap).generate();
        return p;
    }

    private Palette.Swatch checkVibrantSwatch(Palette p) {
        Palette.Swatch vibrant = p.getVibrantSwatch();
        if (vibrant != null) {
            return vibrant;
        }
        return null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getActivity(), mItemId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }

        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            Log.e(TAG, "Error reading item detail cursor");
            mCursor.close();
            mCursor = null;
        }

        bindViews();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
        bindViews();
    }
}
