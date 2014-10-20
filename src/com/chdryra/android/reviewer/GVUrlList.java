/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.chdryra.android.mygenerallibrary.ViewHolder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * GVReviewDataList: GVUrl
 *
 * @see com.chdryra.android.reviewer.FragmentReviewURLs
 */
class GVUrlList extends GVReviewDataList<GVUrlList.GVUrl> {

    GVUrlList() {
        super(GVType.URLS);
    }

    /**
     * GVData version of: RDUrl
     * ViewHolder: VHUrlView
     * <p>
     * Methods for getting full URL and shortened more readable version.
     * </p>
     *
     * @see com.chdryra.android.reviewer.RDUrl
     * @see VHUrl
     */
    static class GVUrl implements GVReviewDataList.GVReviewData {
        public static final Parcelable.Creator<GVUrl> CREATOR = new Parcelable
                .Creator<GVUrl>() {
            public GVUrl createFromParcel(Parcel in) {
                return new GVUrl(in);
            }

            public GVUrl[] newArray(int size) {
                return new GVUrl[size];
            }
        };
        URL mUrl;

        private GVUrl(URL url) {
            mUrl = url;
        }

        private GVUrl(Parcel in) {
            mUrl = (URL) in.readSerializable();
        }

        private GVUrl(String stringUrl) throws MalformedURLException, URISyntaxException {
            URL url = new URL(stringUrl);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                    url.getPath(), url.getQuery(), url.getRef());
            mUrl = uri.toURL();
        }

        public URL getUrl() {
            return mUrl;
        }

        public String toShortenedString() {
            String protocol = mUrl.getProtocol();
            String result = toString().replaceFirst(protocol + ":", "");
            if (result.startsWith("//")) {
                result = result.substring(2);
            }

            result = result.trim();
            if (result.endsWith("/")) {
                result = (String) result.subSequence(0, result.length() - 1);
            }

            return result;
        }

        @Override
        public ViewHolder getViewHolder() {
            return new VHUrl();
        }

        @Override
        public boolean isValidForDisplay() {
            return toShortenedString() != null && toShortenedString().length() > 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GVUrl)) return false;

            GVUrl gvUrl = (GVUrl) o;

            if (mUrl != null ? !mUrl.equals(gvUrl.mUrl) : gvUrl.mUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mUrl != null ? mUrl.hashCode() : 0;
        }

        @Override
        public String toString() {
            return mUrl != null ? mUrl.toExternalForm() : null;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeSerializable(mUrl);
        }
    }

    void add(String urlString) throws MalformedURLException, URISyntaxException {
        add(new GVUrl(urlString));
    }

    void add(URL url) {
        add(new GVUrl(url));
    }

    boolean contains(URL url) {
        return contains(new GVUrl(url));
    }

    void remove(URL url) {
        remove(new GVUrl(url));
    }
}
