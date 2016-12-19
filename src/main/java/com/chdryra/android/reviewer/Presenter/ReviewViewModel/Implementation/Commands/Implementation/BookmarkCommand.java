/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.Playlist;
import com.chdryra.android.reviewer.Persistence.Interfaces.PlaylistCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 26/09/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BookmarkCommand extends Command implements PlaylistCallback {
    private static final String BOOKMARKS = Strings.Playlists.BOOKMARKS;
    private static final String PLACEHOLDER = Strings.Commands.DASHES;
    private static final String UNBOOKMARKING = Strings.Toasts.UNBOOKMARKING;
    private static final String BOOKMARKING = Strings.Toasts.BOOKMARKING;
    private static final String BOOKMARKED = Strings.Toasts.BOOKMARKED;
    private static final String UNBOOKMARKED = Strings.Toasts.UNBOOKMARKED;
    private static final String UNBOOKMARK = Strings.Commands.UNBOOKMARK;
    private static final String BOOKMARK = Strings.Commands.BOOKMARK;
    private static final String BOOKMARKS_UNAVAILABLE = Strings.Commands.BOOKMARKS_UNAVAILABLE;

    private final ReviewId mReviewId;
    private final Playlist mBookmarks;
    private final CurrentScreen mScreen;

    private boolean mIsBookmarked = false;
    private boolean mLocked = false;
    private boolean mErorChecking = false;
    private BookmarkCommandReadyCallback mCallback;

    public interface BookmarkCommandReadyCallback {
        void onBookmarkCommandReady();
    }

    public BookmarkCommand(ReviewId reviewId, MutableRepository repo, CurrentScreen screen) {
        super(PLACEHOLDER);
        mReviewId = reviewId;
        mScreen = screen;
        mBookmarks = repo.getPlaylist(BOOKMARKS);
    }

    public void initialise(BookmarkCommandReadyCallback callback) {
        mCallback = callback;
        lock();
        mBookmarks.hasEntry(mReviewId, this);
    }

    @Override
    public void execute() {
        if (mLocked || mErorChecking) {
            onExecutionComplete();
            return;
        }

        lock();
        if (mIsBookmarked) {
            showToast(UNBOOKMARKING);
            mBookmarks.removeEntry(mReviewId, this);
        } else {
            showToast(BOOKMARKING);
            mBookmarks.addEntry(mReviewId, this);
        }
    }

    @Override
    public void onAddedToPlaylistCallback(CallbackMessage message) {
        showToast(!message.isError() ? BOOKMARKED : message.getMessage());
        unlock();
    }

    @Override
    public void onRemovedFromPlaylistCallback(CallbackMessage message) {
        showToast(!message.isError() ? UNBOOKMARKED : message.getMessage());
        unlock();
    }

    @Override
    public void onPlaylistHasReviewCallback(boolean hasReview, CallbackMessage message) {
        if (!message.isError()) {
            mIsBookmarked = hasReview;
            mErorChecking = false;
            setName(mIsBookmarked ? UNBOOKMARK : BOOKMARK);
        } else {
            mErorChecking = true;
            setName(BOOKMARKS_UNAVAILABLE);
        }
        unlock();
        mCallback.onBookmarkCommandReady();
    }

    private void showToast(String toast) {
        mScreen.showToast(toast);
    }

    private void lock() {
        mLocked = true;
    }

    private void unlock() {
        mLocked = false;
    }
}
