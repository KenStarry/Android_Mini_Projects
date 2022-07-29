package com.example.mvvmintro.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmintro.R;
import com.example.mvvmintro.entities.Note;
import com.example.mvvmintro.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.ViewHolder> {

    private List<Note> selectedNotes = new ArrayList<>();
    private boolean isSelected = false;

    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            //  if these two items have the same id, then they are the same
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority();
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);

        Note note_model = getItem(position);

        holder.noteTitle.setText(note_model.getTitle());
        holder.noteDesc.setText(note_model.getDescription());
        holder.notePriority.setText(String.valueOf(note_model.getPriority()));

        holder.itemView.startAnimation(animation);
    }

    public Note getNoteAt(int i) {
        return getItem(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView noteTitle;
        private TextView noteDesc;
        private TextView notePriority;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);
            noteDesc = itemView.findViewById(R.id.note_desc);
            notePriority = itemView.findViewById(R.id.note_priority);
            checkBox = itemView.findViewById(R.id.checkBox);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    isSelected = true;

                    checkBox.setVisibility(View.VISIBLE);

                    if (selectedNotes.contains(getItem(getAdapterPosition()))) {
                        itemView.setBackgroundColor(Color.TRANSPARENT);
                        selectedNotes.remove(getItem(getAdapterPosition()));

                    } else {
                        itemView.setBackgroundResource(R.color.teal_200);
                        selectedNotes.add(getItem(getAdapterPosition()));
                    }

                    if (selectedNotes.size() == 0) {
                        isSelected = false;
                        checkBox.setVisibility(View.GONE);
                    }

                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //  Pass the interface
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));

                    if (isSelected) {

                        checkBox.setVisibility(View.VISIBLE);

                        if (selectedNotes.contains(getItem(getAdapterPosition()))) {
                            itemView.setBackgroundColor(Color.TRANSPARENT);
                            selectedNotes.remove(getItem(getAdapterPosition()));

                            checkBox.setVisibility(View.GONE);

                        } else {
                            itemView.setBackgroundResource(R.color.teal_200);
                            selectedNotes.add(getItem(getAdapterPosition()));
                        }

                        if (selectedNotes.size() == 0) {
                            isSelected = false;
                            checkBox.setVisibility(View.GONE);
                        }
                    } else {

                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
