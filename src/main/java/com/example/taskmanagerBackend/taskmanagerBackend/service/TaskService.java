package com.example.taskmanagerBackend.taskmanagerBackend.service;

import com.example.taskmanagerBackend.taskmanagerBackend.entity.Task;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TaskService {

    private static final String COLLECTION_NAME = "tasks";

    public String saveTask(Task task){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture =  dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(task.getId())).set(task);
        //ApiFuture<WriteResult> collectionApiFuture =  dbFirestore.collection(COLLECTION_NAME).document().set(task);

        try {
            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Task getTaskDetailsById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference =  dbFirestore.collection(COLLECTION_NAME).document(id);

        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();

        Task task= null;
        if(document.exists()){
            task = document.toObject(Task.class);
            return task;
        }else{
            return null;
        }
    }

    public List<Task> getTaskDetails() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference =  dbFirestore.collection(COLLECTION_NAME).listDocuments();
        Iterator<DocumentReference> iterator= documentReference.iterator();

        List<Task> taskList = new ArrayList<>();
        Task task = null;
        while(iterator.hasNext()){
            DocumentReference documentReference1 = iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();

            task = document.toObject(Task.class);
            taskList.add(task);
        }
        return taskList;
    }

    public String updateTask(Task task){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture =  dbFirestore.collection(COLLECTION_NAME).document(String.valueOf(task.getId())).set(task);
        //ApiFuture<WriteResult> collectionApiFuture =  dbFirestore.collection(COLLECTION_NAME).document().set(task);

        try {
            return collectionApiFuture.get().getUpdateTime().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteTask(String id){
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture =  dbFirestore.collection(COLLECTION_NAME).document(id).delete();
        //ApiFuture<WriteResult> collectionApiFuture =  dbFirestore.collection(COLLECTION_NAME).document().set(task);
        return "task deleted";
    }
}


