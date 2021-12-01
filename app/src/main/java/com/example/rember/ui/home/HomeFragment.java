package com.example.rember.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rember.NavigationActivity;
import com.example.rember.R;
import com.example.rember.RecyclerVAdapter;
import com.example.rember.Task;
import com.example.rember.ui.notifications.NotificationsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    Button btn_add;
    List<Task> tasks = new ArrayList<>();
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popTasks();
        rv = getView().findViewById(R.id.itemsRV);
        btn_add = view.findViewById(R.id.btn_add);

        rv.setHasFixedSize(true);
        manager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(manager);
        adapter = new RecyclerVAdapter(tasks, this.getContext());
        rv.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationActivity.fm.beginTransaction()
                        .hide(NavigationActivity.active)
                        .show(NavigationActivity.noti)
                        .commit();
                NavigationActivity.active = NavigationActivity.noti;
            }
        });
    }

    private void popTasks() {
        Task t0 = new Task(0, true, "0 redirect", "Task 1", "Desc 1");
        Task t1 = new Task(1, true, "1 redirect", "Task 2", "Desc 2");
        Task t2 = new Task(2, true, "2 redirect", "Task 3", "Desc 3");
        Task t3 = new Task(3, true, "3 redirect", "Task 5", "Desc 5");
        Task t4 = new Task(4, true, "4 redirect", "Task 4", "Desc 4");
        Task t5 = new Task(5, true, "5 redirect", "Task 6", "Desc 6");
        Task t6 = new Task(6, true, "6 redirect", "Task 7", "Desc 7");
        Task t7 = new Task(7, true, "7 redirect", "Task 8", "Desc 8");

        tasks.addAll(Arrays.asList(new Task[] {t0, t1, t2, t3, t4, t5, t6, t7}));
    }
}