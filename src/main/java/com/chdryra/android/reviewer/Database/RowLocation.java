/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.MdLocationList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowLocation implements ReviewerDbRow.TableRow {
    public static String LOCATION_ID = ReviewerDbContract.TableLocations
            .COLUMN_NAME_LOCATION_ID;
    public static String REVIEW_ID   = ReviewerDbContract.TableLocations.COLUMN_NAME_REVIEW_ID;
    public static String LAT         = ReviewerDbContract.TableLocations.COLUMN_NAME_LATITUDE;
    public static String LNG         = ReviewerDbContract.TableLocations.COLUMN_NAME_LONGITUDE;
    public static String NAME        = ReviewerDbContract.TableLocations.COLUMN_NAME_NAME;

    private String mLocationId;
    private String mReviewId;
    private double mLatitude;
    private double mLongitude;
    private String mName;

    public RowLocation() {
    }

    public RowLocation(MdLocationList.MdLocation location, int index) {
        mReviewId = location.getReviewId().toString();
        mLocationId = mReviewId + ReviewerDbRow.SEPARATOR + "l" + String.valueOf(index);
        mLatitude = location.getLatLng().latitude;
        mLongitude = location.getLatLng().longitude;
        mName = location.getName();
    }

    public RowLocation(Cursor cursor) {
        mLocationId = cursor.getString(cursor.getColumnIndexOrThrow(LOCATION_ID));
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
        mLatitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LAT));
        mLongitude = cursor.getDouble(cursor.getColumnIndexOrThrow(LNG));
        mName = cursor.getString(cursor.getColumnIndexOrThrow(NAME));
    }


    @Override
    public String getRowId() {
        return mLocationId;
    }

    @Override
    public String getRowIdColumnName() {
        return LOCATION_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(LOCATION_ID, mLocationId);
        values.put(REVIEW_ID, mReviewId);
        values.put(LAT, mLatitude);
        values.put(LNG, mLongitude);
        values.put(NAME, mName);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
    }
}