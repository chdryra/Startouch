package com.chdryra.android.reviewer.View.Configs.Interfaces;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ConfigDataUi {
    <T extends GvData> LaunchableConfigsHolder<T> getLaunchableConfigs(GvDataType<T> dataType);
}
