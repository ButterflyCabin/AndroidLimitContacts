package com.yehu.androidlimitcontacts;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.yehu.androidlimitcontacts.bean.ContactsPerson;
import com.yehu.androidlimtcontacts.R;
import com.yehu.androidlimitcontacts.adapter.ContactsAdapter;
import com.yehu.androidlimitcontacts.bean.Page;
import com.yehu.androidlimitcontacts.utils.ContactsUtils;

import static com.yehu.androidlimtcontacts.R.id.rv_view;
import static com.yehu.androidlimtcontacts.R.id.srl_refresh;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsLoadMore;
    private int mPage = 1;
    private int mPageCount = 20;
    private List<ContactsPerson> mContacts = new ArrayList<>();
    private ContactsUtils mContactsUtils;
    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    public void findViews() {
        mRecycleView = (RecyclerView) findViewById(rv_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(srl_refresh);
        initViews();
    }

    public void initViews() {
        mContactsUtils = new ContactsUtils(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactsAdapter(this, mContacts);
        mRecycleView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mSwipeRefreshLayout.isRefreshing()) {
                    MainActivity.this.onRefresh();
                }
            }
        });
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_IDLE == newState && isVisBottom(recyclerView) && mIsLoadMore && !mSwipeRefreshLayout.isRefreshing()) {
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        onRefresh();
    }

    public void onRefresh() {
        mPage = 1;
        mContacts.clear();
        getContactsData();
    }

    public void onLoadMore() {
        getContactsData();
    }

    public void getContactsData() {
        mSwipeRefreshLayout.setRefreshing(true);
        mIsLoadMore = false;
        final int[] tempValues = new int[4] ;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Page<List<ContactsPerson>> tkPage = mContactsUtils.getContactsByPage(mPageCount, mPage);
                if (null != tkPage) {
                    tempValues[0] = tkPage.count;
                    tempValues[1] = tkPage.pages;
                    tempValues[2] = tkPage.page;
                    List<ContactsPerson> list = tkPage.data;
                    if (null != list) {
                        tempValues[3] = list.size();
                        mContacts.addAll(list);
                        if (tkPage.pages > mPage && list.size() == mPageCount) {
                            mPage = tkPage.page + 1;
                            mIsLoadMore = true;
                        } else {
                            mIsLoadMore = false;
                        }
                    }
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.notifyDataSetChanged();
                        ((TextView) findViewById(R.id.tv_count)).setText(getString(R.string.count_tip, tempValues[0],tempValues[1],tempValues[2],tempValues[3]));
                    }
                });
            }
        }).start();
    }

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        return (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE);
    }
}
