/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.View.GvDataModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryGvData {
    private static final String NO_CTOR_ERR        = "Constructor not found: ";
    private static final String INSTANTIATION_ERR  = "Constructor not found: ";
    private static final String INVOCATION_ERR     = "Exception thrown by constructor: ";
    private static final String ILLEGAL_ACCESS_ERR = "Access not allowed to this constructor: ";

    private static FactoryGvData sFactory;
    private final Map<GvDataType, GvTypeList> mClasses = new HashMap<>();

    private FactoryGvData() {
        mClasses.put(gvTypeFromList(GvTagList.class), new GvTypeList<>(GvTagList.class));
        mClasses.put(gvTypeFromList(GvChildList.class), new GvTypeList<>(GvChildList.class));
        mClasses.put(gvTypeFromList(GvCommentList.class), new GvTypeList<>(GvCommentList.class));
        mClasses.put(gvTypeFromList(GvFactList.class), new GvTypeList<>(GvFactList.class));
        mClasses.put(gvTypeFromList(GvImageList.class), new GvTypeList<>(GvImageList.class));
        mClasses.put(gvTypeFromList(GvLocationList.class), new GvTypeList<>(GvLocationList.class));
        mClasses.put(gvTypeFromList(GvUrlList.class), new GvTypeList<>(GvUrlList.class));
    }

    private static FactoryGvData get() {
        if (sFactory == null) sFactory = new FactoryGvData();
        return sFactory;
    }

    public static <T1 extends GvData, T2 extends GvDataList<T1>> GvDataType gvTypeFromList
            (Class<T2> dataClass) {
        return newList(dataClass).getGvDataType();
    }

    public static <T extends GvData> GvDataType gvType(Class<T> dataClass) {
        return newNull(dataClass).getGvDataType();
    }

    //TODO make type safe
    public static <T extends GvData> GvDataList<T> newList(GvDataType dataType) {
        return newList(get().mClasses.get(dataType).mList);
    }

    public static <T1 extends GvData, T2 extends GvDataList<T1>> T2 newList(Class<T2> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        }
    }

    public static <T1 extends GvData, T2 extends GvDataList<T1>> T2 newList(Class<T2> dataClass,
            GvReviewId id) {
        if (id == null) return newList(dataClass);

        try {
            Constructor<T2> ctor = dataClass.getConstructor(GvReviewId.class);
            return ctor.newInstance(id);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(NO_CTOR_ERR + dataClass.getName(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName(), e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOCATION_ERR + dataClass.getName());
        }
    }

    public static <T extends GvData> T newNull(Class<T> dataClass) {
        try {
            return dataClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        }
    }

    public static <T extends GvData> T copy(T datum, Class<T> dataClass) {
        try {
            Constructor<T> ctor = dataClass.getConstructor(dataClass);
            return ctor.newInstance(datum);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(NO_CTOR_ERR + dataClass.getName());
        } catch (InstantiationException e) {
            throw new RuntimeException(INSTANTIATION_ERR + dataClass.getName());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(ILLEGAL_ACCESS_ERR + dataClass.getName());
        } catch (InvocationTargetException e) {
            throw new RuntimeException(INVOCATION_ERR + dataClass.getName());
        }
    }

    private class GvTypeList<L extends GvDataList<T>, T extends GvData> {
        private final Class<L> mList;

        private GvTypeList(Class<L> list) {
            mList = list;
        }
    }
}