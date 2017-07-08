package com.team4.yamblz.timetogo;

import android.app.Application;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.team4.yamblz.timetogo.MainActivity.activity;

/**
 * Created by Grechka on 08.07.2017.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private int position;

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        this.position = position;
        //TODO получаем объект по позиции в списке

        String lectureName = "";
        String date = "";
        String time = "";
        String place = "";
        String lecturer = "";
        boolean toGo = true;

        holder.setLectureName(lectureName);
        holder.setTimeAndPlace(date, time, place);
        holder.setLecturer(lecturer);
        holder.setCheckBox(toGo);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lecture_row, parent, false);
        return new ScheduleViewHolder(v);
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final TextView lectureName;
        private final TextView timeAndPlace;
        private final TextView lecturer;
        private final CheckBox checkBox;

        public ScheduleViewHolder(View v) {
            super(v);
            lectureName = (TextView) v.findViewById(R.id.lecture_name);
            timeAndPlace = (TextView) v.findViewById(R.id.time_place);
            lecturer = (TextView) v.findViewById(R.id.lecturer);
            checkBox = (CheckBox) v.findViewById(R.id.checkbox);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, DetailsFragment.newInstance(position))
                            .addToBackStack(null)
                            .commitAllowingStateLoss();
                }
            });

            checkBox.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO изменить объект
                }
            });
        }

        public void setLectureName(String requestValue) {
            lectureName.setText(requestValue);
        }

        public void setTimeAndPlace(String date, String time, String place) {
            String resultValue = date + ", " + time + ", " + place;
            timeAndPlace.setText(resultValue);
        }

        public void setLecturer(String langsValue) {
            lecturer.setText(langsValue);
        }

        public void setCheckBox(boolean toGo) {
            checkBox.setChecked(toGo);
        }
    }
}
