package com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data;

import android.os.Parcel;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.ViewHolders.VhAuthor;

/**
 * {@link } version of: {@link com.chdryra.android.reviewer.Model.Implementation.UserModel.Author}
 * {@link ViewHolder}: {@link VhAuthor}
 * <p/>
 * <p>
 * Ignores case when comparing authors.
 * </p>
 */
public class GvAuthor extends GvDataBasic<GvAuthor> implements DataAuthor {
    public static final GvDataType<GvAuthor> TYPE =
            new GvDataType<>(GvAuthor.class, "author");
    public static final Creator<GvAuthor> CREATOR = new Creator<GvAuthor>() {
        @Override
        public GvAuthor createFromParcel(Parcel in) {
            return new GvAuthor(in);
        }

        @Override
        public GvAuthor[] newArray(int size) {
            return new GvAuthor[size];
        }
    };

    private String mName;
    private String mUserId;

    //Constructors
    public GvAuthor() {
        this(null, null, null);
    }

    public GvAuthor(String name, String userId) {
        this(null, name, userId);
    }

    public GvAuthor(GvReviewId id, String name, String userId) {
        super(GvAuthor.TYPE, id);
        mName = name;
        mUserId = userId;
    }

    public GvAuthor(GvAuthor author) {
        this(author.getGvReviewId(), author.getName(), author.getUserId());
    }

    GvAuthor(Parcel in) {
        super(in);
        mName = in.readString();
        mUserId = in.readString();
    }

    //Overridden
    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getUserId() {
        return mUserId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mName);
        parcel.writeString(mUserId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GvAuthor)) return false;
        if (!super.equals(o)) return false;

        GvAuthor gvAuthor = (GvAuthor) o;

        if (mName != null ? !mName.equals(gvAuthor.mName) : gvAuthor.mName != null)
            return false;
        return !(mUserId != null ? !mUserId.equals(gvAuthor.mUserId) : gvAuthor.mUserId !=
                null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mUserId != null ? mUserId.hashCode() : 0);
        return result;
    }

    @Override
    public String getStringSummary() {
        return mName;
    }

    @Override
    public ViewHolder getViewHolder() {
        return new VhAuthor();
    }

    @Override
    public boolean isValidForDisplay() {
        return mName != null && mName.length() > 0;
    }

    @Override
    public boolean hasData(DataValidator dataValidator) {
        return dataValidator.validate(this);
    }
}