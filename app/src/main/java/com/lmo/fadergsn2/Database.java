package com.lmo.fadergsn2;

import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    FirebaseFirestore instance;

    public Database(FirebaseFirestore instance) {
        this.instance = FirebaseFirestore.getInstance();
    }

}
