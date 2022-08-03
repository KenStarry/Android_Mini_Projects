package com.example.mvvmintro.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.mvvmintro.data.NoteDao;
import com.example.mvvmintro.data.TrashDao;
import com.example.mvvmintro.entities.Note;
import com.example.mvvmintro.entities.Trash;

@Database(entities = {Note.class, Trash.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    //  Singleton class
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();
    public abstract TrashDao trashDao();

    //  synchronized - you can only create one instance at a time
    public static synchronized NoteDatabase getInstance(Context context) {

        //  Only instantiate the database if we dont have an instance
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NoteDatabase.class,
                            "note_database"
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;
        private TrashDao trashDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
            trashDao = db.trashDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("The Wild", "This is part of the pixel perfect wild", 2));
            noteDao.insert(new Note("The Jungle", "This is part of the pixel perfect wild", 10));
            noteDao.insert(new Note("The Farm", "This is part of the pixel perfect wild", 5));
            noteDao.insert(new Note("The Ox", "This is part of the pixel perfect wild", 3));
            return null;
        }
    }

}
























































