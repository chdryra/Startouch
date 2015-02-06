/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import java.util.HashMap;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Defines the adder, editor and display UIs to use with each data type.
 */
public final class ConfigGvDataAddEdit {
    private static ConfigGvDataAddEdit                    sConfig;
    private final  HashMap<GvDataList.GvType, AddEditUis> mMap;

    private ConfigGvDataAddEdit() {
        mMap = new HashMap<>();

        mMap.put(GvDataList.GvType.TAGS,
                new AddEditUis(
                        AddTag.class,
                        EditTag.class));

        mMap.put(GvDataList.GvType.CHILDREN,
                new AddEditUis(
                        AddChild.class,
                        EditChild.class));

        mMap.put(GvDataList.GvType.COMMENTS,
                new AddEditUis(
                        AddComment.class,
                        EditComment.class));

        mMap.put(GvDataList.GvType.IMAGES,
                new AddEditUis(
                        null,
                        EditImage.class));

        mMap.put(GvDataList.GvType.FACTS,
                new AddEditUis(
                        AddFact.class,
                        EditFact.class));

        mMap.put(GvDataList.GvType.LOCATIONS,
                new AddEditUis(
                        ActivityEditLocationMap.class,
                        ActivityEditLocationMap.class));

        mMap.put(GvDataList.GvType.URLS,
                new AddEditUis(
                        ActivityEditUrlBrowser.class,
                        ActivityEditUrlBrowser.class));

        mMap.put(GvDataList.GvType.FEED,
                new AddEditUis(null, null));

        mMap.put(GvDataList.GvType.SOCIAL,
                new AddEditUis(null, null));
    }

    public static Class<? extends LaunchableUi> getAddClass(GvDataList.GvType dataType) {
        return get().mMap.get(dataType).getAddClass();
    }

    public static Class<? extends LaunchableUi> getEditClass(GvDataList.GvType dataType) {
        return get().mMap.get(dataType).getEditClass();
    }

    private static ConfigGvDataAddEdit get() {
        if (sConfig == null) sConfig = new ConfigGvDataAddEdit();

        return sConfig;
    }

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.

    //Tag
    public static class AddTag extends DialogFragmentGvDataAdd<GvTagList.GvTag> {
        public AddTag() {
            super(GvTagList.class);
        }
    }

    //Child
    public static class AddChild extends
            DialogFragmentGvDataAdd<GvChildrenList.GvChildReview> {
        public AddChild() {
            super(GvChildrenList.class);
        }
    }

    //Comment
    public static class AddComment extends DialogFragmentGvDataAdd<GvCommentList.GvComment> {
        public AddComment() {
            super(GvCommentList.class);
        }
    }

    //Fact
    public static class AddFact extends DialogFragmentGvDataAdd<GvFactList.GvFact> {
        public AddFact() {
            super(GvFactList.class);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogFragmentGvDataEdit<GvTagList.GvTag> {
        public EditTag() {
            super(GvTagList.class);
        }
    }

    //Child
    public static class EditChild extends DialogFragmentGvDataEdit<GvChildrenList.GvChildReview> {
        public EditChild() {
            super(GvChildrenList.class);
        }
    }

    //Comment
    public static class EditComment extends DialogFragmentGvDataEdit<GvCommentList.GvComment> {
        public EditComment() {
            super(GvCommentList.class);
        }
    }

    //Image
    public static class EditImage extends DialogFragmentGvDataEdit<GvImageList.GvImage> {
        public EditImage() {
            super(GvImageList.class);
        }
    }

    //Fact
    public static class EditFact extends DialogFragmentGvDataEdit<GvFactList.GvFact> {
        public EditFact() {
            super(GvFactList.class);
        }
    }

    /**
     * Packages together an add and edit UI.
     */
    class AddEditUis {
        private final Class<? extends LaunchableUi> mAdd;
        private final Class<? extends LaunchableUi> mEdit;

        private AddEditUis(Class<? extends LaunchableUi> add,
                Class<? extends LaunchableUi> edit) {
            mAdd = add;
            mEdit = edit;
        }

        Class<? extends LaunchableUi> getAddClass() {
            return mAdd;
        }

        Class<? extends LaunchableUi> getEditClass() {
            return mEdit;
        }
    }
}