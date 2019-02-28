package com.computech.thenews.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.computech.thenews.R;
import com.computech.thenews.adapters.TabAdapter;
import com.computech.thenews.sub_sources_fragments.AljerzeeraFragment;
import com.computech.thenews.sub_sources_fragments.BBCFragment;
import com.computech.thenews.sub_sources_fragments.BloombergFragment;
import com.computech.thenews.sub_sources_fragments.CnnFragment;
import com.computech.thenews.sub_sources_fragments.FoxNewsFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SourcesFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;


    public SourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sources, container, false);
        Objects.requireNonNull(getActivity()).setTitle("News by Sources");

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new BBCFragment(),"BBC News");
        adapter.addFragment(new CnnFragment(),"CNN News");
        adapter.addFragment(new BloombergFragment(),"Bloomberg");
        adapter.addFragment(new AljerzeeraFragment(),"Aljazeera");
        adapter.addFragment(new FoxNewsFragment(),"Fox News");

        //set the adapter to the view pager
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

}
