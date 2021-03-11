package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView placeholder_lottie;
    private AnimatorSet animatorSet;
    private RecyclerView table_list;

    private TextViewAdapter TextViewAdapter = new TextViewAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        placeholder_lottie = view.findViewById(R.id.placeholder_lottie);
        table_list = view.findViewById(R.id.table_list);

        table_list.setLayoutManager(new LinearLayoutManager(getContext()));
        table_list.setAdapter(TextViewAdapter);

        List<String> items = new ArrayList<>();
        for(int i = 1; i < 100; i++){
            items.add(String.valueOf(i) + "个梦想");
        }
        TextViewAdapter.notifyItems(items);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator lottie_alpha = ObjectAnimator.ofFloat(placeholder_lottie, "alpha", 1f, 0f);
                lottie_alpha.setDuration(500);
//                lottie_alpha.start();

                ObjectAnimator table_list_alpha = ObjectAnimator.ofFloat(table_list, "alpha", 0f, 1f);
                table_list_alpha.setDuration(500);
                table_list.setVisibility(View.VISIBLE);

                animatorSet = new AnimatorSet();
                animatorSet.playTogether(lottie_alpha, table_list_alpha);
                animatorSet.start();

            }
        }, 5000);

    }
}
