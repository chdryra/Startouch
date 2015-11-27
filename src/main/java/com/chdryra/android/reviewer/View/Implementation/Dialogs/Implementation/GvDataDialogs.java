package com.chdryra.android.reviewer.View.Implementation.Dialogs.Implementation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataDialogs {
    private GvDataDialogs() {}

    //Adders
    //Need these subclasses as can't programmatically instantiate classes that utilise generics.
    //Tag
    public static class AddTag extends DialogGvDataAdd<GvTag> {
        //Constructors
        public AddTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class AddCriterion extends
            DialogGvDataAdd<GvCriterion> {
        //Constructors
        public AddCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    //Comment
    public static class AddComment extends DialogGvDataAdd<GvComment> {
        //Constructors
        public AddComment() {
            super(GvComment.TYPE);
        }
    }

    //Fact
    public static class AddFact extends DialogGvDataAdd<GvFact> {
        //Constructors
        public AddFact() {
            super(GvFact.TYPE);
        }
    }

    //Location
    public static class AddLocation extends DialogGvDataAdd<GvLocation> {
        //Constructors
        public AddLocation() {
            super(GvLocation.TYPE);
        }
    }

    //Editors
    //Tag
    public static class EditTag extends DialogGvDataEdit<GvTag> {
        //Constructors
        public EditTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class EditCriterion extends DialogGvDataEdit<GvCriterion> {
        //Constructors
        public EditCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    //Comment
    public static class EditComment extends DialogGvDataEdit<GvComment> {
        //Constructors
        public EditComment() {
            super(GvComment.TYPE);
        }
    }

    //Image
    public static class EditImage extends DialogGvDataEdit<GvImage> {
        //Constructors
        public EditImage() {
            super(GvImage.TYPE);
        }
    }

    //Fact
    public static class EditFact extends DialogGvDataEdit<GvFact> {
        //Constructors
        public EditFact() {
            super(GvFact.TYPE);
        }
    }

    //Location
    public static class EditLocation extends DialogGvDataEdit<GvLocation> {
        //Constructors
        public EditLocation() {
            super(GvLocation.TYPE);
        }
    }

    //Viewers
    //Tag
    public static class ViewTag extends DialogGvDataView<GvTag> {
        //Constructors
        public ViewTag() {
            super(GvTag.TYPE);
        }
    }

    //Child
    public static class ViewCriterion extends DialogGvDataView<GvCriterion> {
        //Constructors
        public ViewCriterion() {
            super(GvCriterion.TYPE);
        }
    }

    //Comment
    public static class ViewComment extends DialogGvDataView<GvComment> {
        //Constructors
        public ViewComment() {
            super(GvComment.TYPE);
        }
    }

    //Image
    public static class ViewImage extends DialogGvDataView<GvImage> {
        //Constructors
        public ViewImage() {
            super(GvImage.TYPE);
        }
    }

    //Fact
    public static class ViewFact extends DialogGvDataView<GvFact> {
        //Constructors
        public ViewFact() {
            super(GvFact.TYPE);
        }
    }

    //Subject
    public static class ViewSubject extends DialogGvDataView<GvSubject> {
        //Constructors
        public ViewSubject() {
            super(GvSubject.TYPE);
        }
    }

    //Date
    public static class ViewDate extends DialogGvDataView<GvDate> {
        //Constructors
        public ViewDate() {
            super(GvDate.TYPE);
        }
    }
}
