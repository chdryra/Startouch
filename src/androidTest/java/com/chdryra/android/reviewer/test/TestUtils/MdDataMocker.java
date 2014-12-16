/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdDataList;
import com.chdryra.android.reviewer.MdFactList;
import com.chdryra.android.reviewer.MdImageList;
import com.chdryra.android.reviewer.MdLocationList;
import com.chdryra.android.reviewer.MdUrlList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.testutils.BitmapMocker;
import com.chdryra.android.testutils.LatLngMocker;
import com.chdryra.android.testutils.RandomStringGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataMocker<T extends Review> {
    private static final RandomStringGenerator STRING_GENERATOR = new RandomStringGenerator();
    private static final String                URL              = "http://www.google.co.uk";
    private static final Random                RAND             = new Random();
    private static URL sUrl;

    private T mReview;

    public MdDataMocker(T review) {
        mReview = review;
    }

    //Just a convenient method even if it uses GvType.....
    public MdDataList getData(GvDataList.GvType dataType, int size) {
        if (dataType == GvDataList.GvType.COMMENTS) {
            return newCommentList(size);
        } else if (dataType == GvDataList.GvType.FACTS) {
            return newFactList(size);
        } else if (dataType == GvDataList.GvType.IMAGES) {
            return newImageList(size);
        } else if (dataType == GvDataList.GvType.LOCATIONS) {
            return newLocationList(size);
        } else if (dataType == GvDataList.GvType.URLS) {
            return newUrlList(size);
        } else {
            return null;
        }
    }

    public MdCommentList newCommentList(int size) {
        MdCommentList list = new MdCommentList(mReview);
        for (int i = 0; i < size; ++i) {
            list.add(newComment());
        }

        return list;
    }

    public MdImageList newImageList(int size) {
        MdImageList list = new MdImageList(mReview);
        for (int i = 0; i < size; ++i) {
            list.add(newImage());
        }

        return list;
    }

    public MdLocationList newLocationList(int size) {
        MdLocationList list = new MdLocationList(mReview);
        for (int i = 0; i < size; ++i) {
            list.add(newLocation());
        }

        return list;
    }

    public MdFactList newFactList(int size) {
        MdFactList list = new MdFactList(mReview);
        for (int i = 0; i < size; ++i) {
            list.add(newFact());
        }

        return list;
    }

    public MdUrlList newUrlList(int size) {
        MdUrlList list = new MdUrlList(mReview);
        for (int i = 0; i < size; ++i) {
            list.add(newUrl());
        }

        return list;
    }

    public MdCommentList.MdComment newComment() {
        return new MdCommentList.MdComment(STRING_GENERATOR.nextParagraph(), mReview);
    }

    public MdImageList.MdImage newImage() {
        return new MdImageList.MdImage(BitmapMocker.nextBitmap(RAND.nextBoolean()),
                LatLngMocker.newLatLng(), RandomStringGenerator.nextSentence(),
                RAND.nextBoolean(), mReview);
    }

    public MdLocationList.MdLocation newLocation() {
        return new MdLocationList.MdLocation(LatLngMocker.newLatLng(),
                RandomStringGenerator.nextWord(), mReview);
    }

    public MdFactList.MdFact newFact() {
        return new MdFactList.MdFact(RandomStringGenerator.nextWord(),
                RandomStringGenerator.nextWord(),
                mReview);
    }

    public MdUrlList.MdUrl newUrl() {
        if (sUrl == null) {
            try {
                sUrl = new URL(URL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new MdUrlList.MdUrl(sUrl, mReview);
    }
}
