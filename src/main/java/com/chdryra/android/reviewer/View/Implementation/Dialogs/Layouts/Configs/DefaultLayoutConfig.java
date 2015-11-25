package com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Configs;

import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.AddEditComment;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.AddEditCriterion;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.AddEditFact;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.AddEditTag;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.LayoutEditImage;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.LayoutEditLocation;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutComment;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutCriterion;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutDate;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutFact;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutImage;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutSubject;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Layouts.Implementation.ViewLayoutTag;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DefaultLayoutConfig extends ConfigDialogLayouts{
    public DefaultLayoutConfig() {
        add(GvTag.TYPE, AddEditTag.class, ViewLayoutTag.class);
        add(GvCriterion.TYPE, AddEditCriterion.class, ViewLayoutCriterion.class);
        add(GvImage.TYPE, LayoutEditImage.class, ViewLayoutImage.class);
        add(GvComment.TYPE, AddEditComment.class, ViewLayoutComment.class);
        add(GvLocation.TYPE, LayoutEditLocation.class, null);
        add(GvFact.TYPE, AddEditFact.class, ViewLayoutFact.class);
        add(GvSubject.TYPE, null, ViewLayoutSubject.class);
        add(GvDate.TYPE, null, ViewLayoutDate.class);
    }
}
