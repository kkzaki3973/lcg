package top.easelink.lcg.ui.main.forumnav.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import org.greenrobot.eventbus.EventBus;
import top.easelink.framework.BR;
import top.easelink.framework.base.BaseFragment;
import top.easelink.lcg.R;
import top.easelink.lcg.databinding.FragmentForumsNavigationBinding;
import top.easelink.lcg.ui.ViewModelProviderFactory;
import top.easelink.lcg.ui.main.forumnav.viewmodel.ForumNavigationViewModel;
import top.easelink.lcg.ui.main.model.ForumNavigationModel;
import top.easelink.lcg.ui.main.model.OpenForumEvent;

import javax.inject.Inject;

public class ForumNavigationFragment extends BaseFragment<FragmentForumsNavigationBinding, ForumNavigationViewModel>
        implements ForumNavigationNavigator{

    @Inject
    ViewModelProviderFactory factory;

    public static ForumNavigationFragment newInstance() {
        return new ForumNavigationFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_forums_navigation;
    }

    @Override
    public ForumNavigationViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(ForumNavigationViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp();
    }

    private void setUp() {
        getViewDataBinding().navigationGrid.setAdapter(new CustomGridViewAdapter(getBaseActivity(), R.layout.item_forums_grid));
        getViewDataBinding().navigationGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ForumNavigationModel item = ((CustomGridViewAdapter) parent.getAdapter()).getItem(position);
                EventBus.getDefault().post(new OpenForumEvent(item.getTitle(), item.getUrl()));
            }
        });
        getViewModel().initOptions(getContext());
    }
}
