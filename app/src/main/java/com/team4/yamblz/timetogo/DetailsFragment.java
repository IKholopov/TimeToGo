package com.team4.yamblz.timetogo;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private String lectureName, date, time, duration, place, lecturer, school;
    private TextView tvLectureName, tvDate, tvTime, tvDuration, tvPlace, tvLecturer, tvSchool;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(int pos) {
        Bundle args = new Bundle();
        DetailsFragment fragment = new DetailsFragment();
        args.putInt("position", pos);
        //TODO достаем инфо из объекта
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.bar_back);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        tvLectureName = (TextView) v.findViewById(R.id.lecture_name);
        tvDate = (TextView) v.findViewById(R.id.date);
        tvTime = (TextView) v.findViewById(R.id.time);
        tvDuration = (TextView) v.findViewById(R.id.duration);
        tvPlace = (TextView) v.findViewById(R.id.place);
        tvLecturer = (TextView) v.findViewById(R.id.lecturer);
        tvSchool = (TextView) v.findViewById(R.id.school);
        tvLectureName.setText(lectureName);
        tvDate.setText(date);
        tvTime.setText(time);
        tvDuration.setText(duration);
        tvPlace.setText(place);
        tvLecturer.setText(lecturer);
        tvSchool.setText(school);
        return v;
    }
}
