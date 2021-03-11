package com.example.classtest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private SearchAdapter mSearchAdapter = new SearchAdapter();

    private EditText mEditView;
    private TextView mSearchText;
    private OnSearchTextChangedListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_third, container, false);
        // 获取 RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_view);
        // 设置 layout 显示效果，线性布局、grid或者瀑布流
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        // 为 RecyelerView 设置 adapter
        mRecyclerView.setAdapter(mSearchAdapter);

        // 模拟数据
        List<String> items = new ArrayList<>();
        for (int i=0; i<=100; i++){
            if (i == 0) items.add(String.format("全军出击！"));
            else items.add(String.format("敌军还有%d秒到达战场！", i));
        }

        // SearchAdapter 函数
        mSearchAdapter.notifyItems(items);

        mEditView = view.findViewById(R.id.edit);
        mSearchText = view.findViewById(R.id.search);

        mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "beforeTextChanged: " + s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "OnTextChanged: " + s);
                mSearchText.setText("取消");
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListener != null){
                    mListener.afterChanged(s.toString());
                }
            }
        });

        setOnSearchTextChangedListener(new OnSearchTextChangedListener() {
            @Override
            public void afterChanged(String text) {
                Log.i(TAG, "afterChanged: " + text);
                List<String> filters = new ArrayList<>();
                for (String item : items){
                    if (item.contains(text)){
                        filters.add(item);
                    }
                }
                mSearchAdapter.notifyItems(filters);
            }
        });

        return view;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChangedListener listener){
        mListener = listener;
    }

    public interface OnSearchTextChangedListener{
        void afterChanged(String text);
    }

    public void closeSoftKeyboard(EditText mEditText, Context mContext){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

}