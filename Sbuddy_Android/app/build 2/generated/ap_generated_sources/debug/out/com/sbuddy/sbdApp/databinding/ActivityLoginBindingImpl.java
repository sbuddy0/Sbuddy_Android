package com.sbuddy.sbdApp.databinding;
import com.sbuddy.sbdApp.R;
import com.sbuddy.sbdApp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityLoginBindingImpl extends ActivityLoginBinding implements com.sbuddy.sbdApp.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.top_layout, 5);
        sViewsWithIds.put(R.id.before_btn, 6);
        sViewsWithIds.put(R.id.middle_layout, 7);
        sViewsWithIds.put(R.id.mail, 8);
        sViewsWithIds.put(R.id.pwd, 9);
        sViewsWithIds.put(R.id.bottom_layout, 10);
        sViewsWithIds.put(R.id.sign_btn, 11);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.EditText mboundView1;
    @NonNull
    private final android.widget.EditText mboundView2;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    @Nullable
    private final android.view.View.OnClickListener mCallback2;
    // values
    // listeners
    // Inverse Binding Event Handlers
    private androidx.databinding.InverseBindingListener mboundView1androidTextAttrChanged = new androidx.databinding.InverseBindingListener() {
        @Override
        public void onChange() {
            // Inverse of viewModel.id.getValue()
            //         is viewModel.id.setValue((java.lang.String) callbackArg_0)
            java.lang.String callbackArg_0 = androidx.databinding.adapters.TextViewBindingAdapter.getTextString(mboundView1);
            // localize variables for thread safety
            // viewModel.id != null
            boolean viewModelIdJavaLangObjectNull = false;
            // viewModel.id
            androidx.lifecycle.MutableLiveData<java.lang.String> viewModelId = null;
            // viewModel.id.getValue()
            java.lang.String viewModelIdGetValue = null;
            // viewModel
            com.sbuddy.sbdApp.login.LoginViewModel viewModel = mViewModel;
            // viewModel != null
            boolean viewModelJavaLangObjectNull = false;



            viewModelJavaLangObjectNull = (viewModel) != (null);
            if (viewModelJavaLangObjectNull) {


                viewModelId = viewModel.getId();

                viewModelIdJavaLangObjectNull = (viewModelId) != (null);
                if (viewModelIdJavaLangObjectNull) {




                    viewModelId.setValue(((java.lang.String) (callbackArg_0)));
                }
            }
        }
    };

    public ActivityLoginBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivityLoginBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 2
            , (android.widget.ImageView) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[10]
            , (androidx.appcompat.widget.AppCompatButton) bindings[3]
            , (android.widget.LinearLayout) bindings[8]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (androidx.appcompat.widget.AppCompatButton) bindings[11]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[5]
            );
        this.loginBtn.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.EditText) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.EditText) bindings[2];
        this.mboundView2.setTag(null);
        this.pwdFindOut.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.sbuddy.sbdApp.generated.callback.OnClickListener(this, 1);
        mCallback2 = new com.sbuddy.sbdApp.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.activity == variableId) {
            setActivity((com.sbuddy.sbdApp.login.LoginActivity) variable);
        }
        else if (BR.viewModel == variableId) {
            setViewModel((com.sbuddy.sbdApp.login.LoginViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setActivity(@Nullable com.sbuddy.sbdApp.login.LoginActivity Activity) {
        this.mActivity = Activity;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.activity);
        super.requestRebind();
    }
    public void setViewModel(@Nullable com.sbuddy.sbdApp.login.LoginViewModel ViewModel) {
        this.mViewModel = ViewModel;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.viewModel);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeViewModelId((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
            case 1 :
                return onChangeViewModelPassword((androidx.lifecycle.MutableLiveData<java.lang.String>) object, fieldId);
        }
        return false;
    }
    private boolean onChangeViewModelId(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelId, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeViewModelPassword(androidx.lifecycle.MutableLiveData<java.lang.String> ViewModelPassword, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelId = null;
        java.lang.String viewModelPasswordGetValue = null;
        java.lang.String viewModelIdGetValue = null;
        com.sbuddy.sbdApp.login.LoginActivity activity = mActivity;
        com.sbuddy.sbdApp.login.LoginViewModel viewModel = mViewModel;
        androidx.lifecycle.MutableLiveData<java.lang.String> viewModelPassword = null;

        if ((dirtyFlags & 0x1bL) != 0) {


            if ((dirtyFlags & 0x19L) != 0) {

                    if (viewModel != null) {
                        // read viewModel.id
                        viewModelId = viewModel.getId();
                    }
                    updateLiveDataRegistration(0, viewModelId);


                    if (viewModelId != null) {
                        // read viewModel.id.getValue()
                        viewModelIdGetValue = viewModelId.getValue();
                    }
            }
            if ((dirtyFlags & 0x1aL) != 0) {

                    if (viewModel != null) {
                        // read viewModel.password
                        viewModelPassword = viewModel.getPassword();
                    }
                    updateLiveDataRegistration(1, viewModelPassword);


                    if (viewModelPassword != null) {
                        // read viewModel.password.getValue()
                        viewModelPasswordGetValue = viewModelPassword.getValue();
                    }
            }
        }
        // batch finished
        if ((dirtyFlags & 0x10L) != 0) {
            // api target 1

            this.loginBtn.setOnClickListener(mCallback1);
            androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher(this.mboundView1, (androidx.databinding.adapters.TextViewBindingAdapter.BeforeTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged)null, (androidx.databinding.adapters.TextViewBindingAdapter.AfterTextChanged)null, mboundView1androidTextAttrChanged);
            this.pwdFindOut.setOnClickListener(mCallback2);
        }
        if ((dirtyFlags & 0x19L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, viewModelIdGetValue);
        }
        if ((dirtyFlags & 0x1aL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, viewModelPasswordGetValue);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.sbuddy.sbdApp.login.LoginActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {


                    activity.requestLogin();
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // activity != null
                boolean activityJavaLangObjectNull = false;
                // activity
                com.sbuddy.sbdApp.login.LoginActivity activity = mActivity;



                activityJavaLangObjectNull = (activity) != (null);
                if (activityJavaLangObjectNull) {


                    activity.findPwd();
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): viewModel.id
        flag 1 (0x2L): viewModel.password
        flag 2 (0x3L): activity
        flag 3 (0x4L): viewModel
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}