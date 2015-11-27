package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.View.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.GridItemAction;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.BannerButtonAddLocation;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GridItemDataEditLocation;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation.GvDataPacker;


/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryEditActionsLocations extends FactoryEditActionsDefault<GvLocation> {
    private static final GvDataType<GvLocation> TYPE = GvLocation.TYPE;

    public FactoryEditActionsLocations(Context context, ConfigDataUi config,
                                       LaunchableUiLauncher launchableFactory,
                                       FactoryGvData dataFactory,
                                       GvDataPacker<GvLocation> packer) {
        super(context, TYPE, config, launchableFactory, dataFactory, packer);
    }

    @Override
    protected BannerButtonAction<GvLocation> newBannerButtonAdd() {
        return new BannerButtonAddLocation(getAdderConfig(), getConfig().getMapEditorConfig(),
                getBannerButtonTitle(), getDataFactory(), getPacker(), getLaunchableFactory());
    }

    @Override
    protected GridItemAction<GvLocation> newGridItemEdit() {
        return new GridItemDataEditLocation(getEditorConfig(), getConfig().getMapEditorConfig(),
                getLaunchableFactory(), getPacker());
    }

}
