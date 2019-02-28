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
import com.computech.thenews.sub_fragments.BusinessFragment;
import com.computech.thenews.sub_fragments.EntertainmentFragment;
import com.computech.thenews.sub_fragments.GeneralFragment;
import com.computech.thenews.sub_fragments.HealthFragment;
import com.computech.thenews.sub_fragments.ScienceFragment;
import com.computech.thenews.sub_fragments.SportsFragment;
import com.computech.thenews.sub_fragments.TechFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Objects.requireNonNull(getActivity()).setTitle("Home");
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);

        //set the adapter for the viewpager and adapter
        adapter = new TabAdapter(getChildFragmentManager());

        adapter.addFragment(new GeneralFragment(),"General");
        adapter.addFragment(new BusinessFragment(),"Business");
        adapter.addFragment(new EntertainmentFragment(),"Entertainment");
        adapter.addFragment(new HealthFragment(),"Health");
        adapter.addFragment(new TechFragment(),"Tech");
        adapter.addFragment(new ScienceFragment(),"Science");
        adapter.addFragment(new SportsFragment(),"Sports");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.colorPrimary));

        return view;
    }

}
